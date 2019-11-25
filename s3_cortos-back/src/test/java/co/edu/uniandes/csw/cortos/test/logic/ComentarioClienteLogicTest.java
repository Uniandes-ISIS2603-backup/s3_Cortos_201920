/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CalificacionClienteLogic;
import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ComentarioClienteLogic;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Vargas Vega
 */
@RunWith(Arquillian.class)
public class ComentarioClienteLogicTest {
     private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ComentarioLogic comentarioLogic;
    
    @Inject
    private ComentarioClienteLogic comentarioClienteLogic;

    @PersistenceContext
    EntityManager em;
    
    @Inject
    
    private UserTransaction utx;
    
    private List<ComentarioEntity > data = new ArrayList<>();
    
    private List<ClienteEntity> clientes = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
      private void clearData(){
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
      
    }
      
       private void insertData()
    {
       
        for (int i = 0; i < 3; i++) {
            ComentarioEntity c= factory.manufacturePojoWithFullData(ComentarioEntity.class);
            em.persist(c);
            data.add(c);
        }
        
         for (int i = 0; i < 3; i++) {
            ClienteEntity c= factory.manufacturePojoWithFullData(ClienteEntity.class);
            c.setCalificaciones(new ArrayList<>());
            c.getComentarios().add(data.get(0));
            em.persist(c);
            clientes.add(c);
        }
    }
            /**
     * Configuracion inicial de la prueba
     */
    @Before
    public void configTest(){
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        }catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
    
   @Test
   public void addComentarioClienteTest()
   {
       ComentarioEntity comentario = data.get(0);
       ClienteEntity cliente = clientes.get(1);
       ClienteEntity resp =  comentarioClienteLogic.addCliente(cliente.getId(), comentario.getId());
       Assert.assertNotNull(resp);
       Assert.assertEquals(resp.getId(),cliente.getId());
       Assert.assertEquals(resp.getContrasenia(),cliente.getContrasenia());
       Assert.assertEquals(resp.getNombre(),cliente.getNombre());
       Assert.assertEquals(resp.getCorreo(),cliente.getCorreo());
       
   }
   
   @Test
   public void getClienteTest()
   {
       ComentarioEntity comentario = data.get(0);
       ClienteEntity cliente = clientes.get(1);
       ClienteEntity resp =  comentarioClienteLogic.addCliente(cliente.getId(), comentario.getId());
       ClienteEntity prueba = comentarioClienteLogic.getCliente(data.get(0).getId());
       Assert.assertNotNull(prueba);
   }
}
