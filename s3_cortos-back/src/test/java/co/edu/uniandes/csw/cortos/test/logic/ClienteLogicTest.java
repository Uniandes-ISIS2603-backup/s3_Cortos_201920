/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.ClienteLogic;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Arturo Rubio
 */
@RunWith(Arquillian.class)
public class ClienteLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private ClienteLogic clienteLogic;
    
    @Inject
    UserTransaction utx;
    
    private List<ClienteEntity> data = new ArrayList<>();
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    
    private void insertData(){
        for(int i =0;i<3;i++)
        {
            ClienteEntity comentario= factory.manufacturePojo(ClienteEntity.class);
            em.persist(comentario);
            data.add(comentario);
        }
    }
    
    @Deployment
     public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    } 
     @Test
     public void createCliente()throws BusinessLogicException
     {
         ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
         ClienteEntity result = clienteLogic.createCliente(newEntity);
         Assert.assertNotNull(result); 
         
         ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
         Assert.assertEquals(entity.getNombre(),result.getNombre());
     }
     @Test(expected = BusinessLogicException.class)
     public void createClienteNombreNull()throws BusinessLogicException
     {
         ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
         newEntity.setNombre(null);
         ClienteEntity result = clienteLogic.createCliente(newEntity);
     }
     @Test(expected = BusinessLogicException.class)
     public void createClientContraseniaNull()throws BusinessLogicException
     {
         ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
         newEntity.setContrasenia(null);
         ClienteEntity result = clienteLogic.createCliente(newEntity);
     }
     
     @Test(expected = BusinessLogicException.class)
     public void createCorreoNombreNull()throws BusinessLogicException
     {
         ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
         newEntity.setCorreo(null);
         ClienteEntity result = clienteLogic.createCliente(newEntity);
     }
     
     @Test(expected = BusinessLogicException.class)
     public void createClienteNombreRep()throws BusinessLogicException
     {
         ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
         ClienteEntity newEntity2 = factory.manufacturePojo(ClienteEntity.class);
         ClienteEntity result = clienteLogic.createCliente(newEntity2);
         newEntity.setNombre(result.getNombre());
         ClienteEntity result2 = clienteLogic.createCliente(newEntity);
     }
     
     @Test(expected = BusinessLogicException.class)
     public void createClienteCorreoRep()throws BusinessLogicException
     {
         ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
         ClienteEntity newEntity2 = factory.manufacturePojo(ClienteEntity.class);
         ClienteEntity result = clienteLogic.createCliente(newEntity2);
         newEntity.setCorreo(newEntity2.getCorreo());
         ClienteEntity result2 = clienteLogic.createCliente(newEntity);
     }
     
     @Test 
    public void deleteTest()throws BusinessLogicException{
        ClienteEntity c = data.get(0);
        clienteLogic.deleteCliente(c.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, c.getId());
        org.junit.Assert.assertNull(deleted);
    }
    @Test
    public void getClientesTest() {
       List<ClienteEntity> list = clienteLogic.getClientes();
        org.junit.Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity ent : list) {
            boolean found = false;
            for (ClienteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clienteLogic.getCliente(entity.getId());
        org.junit.Assert.assertNotNull(newEntity);
        org.junit.Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        org.junit.Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    @Test
    public void updateClienteTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setId(entity.getId());

        clienteLogic.updateCliente(entity.getId(),newEntity);

        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        org.junit.Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        org.junit.Assert.assertEquals(newEntity.getId(), resp.getId());
       
    }
}
