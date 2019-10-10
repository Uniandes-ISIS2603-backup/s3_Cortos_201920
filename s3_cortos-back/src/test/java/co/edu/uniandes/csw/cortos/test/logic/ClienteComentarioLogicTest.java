/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteCalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class ClienteComentarioLogicTest 
{
    private PodamFactory fabrica = new PodamFactoryImpl();
    
    @Inject
    private ComentarioLogic cl;
    
    @Inject
    private ClienteComentarioLogic ccl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ClienteEntity> data = new ArrayList<>();
    
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    /**
     * Limpia las tablas que estan en la prueba
     */
    private void clearData(){
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }
    
    /**
     * Inserta datos iniciales para las pruebas
     */
    private void insertData(){
        for(int i = 0; i < 3; ++i){
            ComentarioEntity c = fabrica.manufacturePojo(ComentarioEntity.class);
            c.setComentario("comnet" + i);
            em.persist(c);
            comentarios.add(c);
        }
        for(int i = 0 ; i < 3 ; ++ i){
            ClienteEntity c = fabrica.manufacturePojo(ClienteEntity.class);
            em.persist(c);
            data.add(c);
            if(i == 0)
                comentarios.get(i).setCliente(c);
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
    
    /**
     * Prueba asociar Comentario con Corto
     */
    @Test
    public void addComentarioTest(){
        ClienteEntity c = data.get(0);
        ComentarioEntity coment = comentarios.get(1);
        coment.setCliente(c);
        ComentarioEntity resp = ccl.addComentario(coment.getId(), coment.getCliente().getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getCliente(), coment.getCliente());
    }
    
    /**
     * Prueba para obtener toda la lista de comentarios de un corto
     */
    @Test
    public void getComentariosTest(){
        List<ComentarioEntity> lista = ccl.getComentarios(data.get(0).getId());
        
        Assert.assertEquals(1,lista.size());
    }
    /**
     * Prueba para obtener comentario existente
     * @throws BusinessLogicException 
     */
    @Test
    public void getComentarioTest() throws BusinessLogicException{
        ClienteEntity c = data.get(0);
        ComentarioEntity coment = comentarios.get(0);
        ComentarioEntity resp = ccl.getComentario(c.getId(),coment.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getCliente(), coment.getCliente());
        Assert.assertEquals(resp.getId(), coment.getId());
    }
    
    /**
     * Prueba para recibir la excepcion por no existir comentario en la lista
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getComentarioNoExisteTest()throws BusinessLogicException{
        ClienteEntity c = data.get(0);
        ComentarioEntity coment = comentarios.get(1);
        ComentarioEntity resp = ccl.getComentario(c.getId(),coment.getId());
    }
}
