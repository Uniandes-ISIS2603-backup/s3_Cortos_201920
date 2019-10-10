/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CalificacionClienteLogic;
import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
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
public class CalificacionClienteLogicTest {
   
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
    @Inject
    private CalificacionClienteLogic calificacionClienteLogic;

    @PersistenceContext
    EntityManager em;
    
    @Inject
    
    private UserTransaction utx;
    
    private List<CalificacionEntity > data = new ArrayList<>();
    
    private List<ClienteEntity> clientes = new ArrayList<>();
    
     @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
     /**
     * Limpia las tablas que estan en la prueba
     */
    private void clearData(){
        em.createQuery("delete from ClienteEntity").executeUpdate();
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }
    
    private void insertData()
    {
       
        for (int i = 0; i < 3; i++) {
            CalificacionEntity c= factory.manufacturePojoWithFullData(CalificacionEntity.class);
            em.persist(c);
            data.add(c);
        }
        
         for (int i = 0; i < 3; i++) {
            ClienteEntity c= factory.manufacturePojoWithFullData(ClienteEntity.class);
            c.setCalificaciones(new ArrayList<>());
            c.getCalificaciones().add(data.get(0));
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
    public void addCalificacionClienteTest()
    {
        CalificacionEntity calificacion = data.get(0);
        ClienteEntity cliente = clientes.get(1);
        ClienteEntity  resp = calificacionClienteLogic.addCliente(calificacion.getId(), cliente.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(),cliente.getId());
        Assert.assertEquals(resp.getCorreo(),cliente.getCorreo());
        Assert.assertEquals(resp.getContrasenia(),cliente.getContrasenia());
        Assert.assertEquals(resp.getNombre(),cliente.getNombre());
    }
    
    @Test
    public void getClienteTest()
    {
        ClienteEntity lista = calificacionClienteLogic.getCliente(data.get(0).getId());
        Assert.assertNull(lista);
    }
    
}
