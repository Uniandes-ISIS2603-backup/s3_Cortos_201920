/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
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
 * @author Arturo Rubio
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest 
{
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    ClientePersistence cp;
    
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

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
   
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    
    @Test
    public void createTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = cp.create(cliente);
        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        
        Assert.assertEquals(cliente.getNombre(), entity.getNombre());
        
    }
    
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity ent : list) {
            boolean found = false;
            for (ClienteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), entity.getContrasenia());
        Assert.assertEquals(entity.getCorreo(), entity.getCorreo());    
    }
    
    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }
    
    @Test
    public void deleteClienteTest() {
        ClienteEntity entity = data.get(0);
        cp.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void getClienteNombreTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = cp.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), entity.getContrasenia());
        Assert.assertEquals(entity.getCorreo(), entity.getCorreo());    
    }
    
    @Test
    public void getClienteNombreLikeTest() {
        
         try {
            utx.begin();
            em.joinTransaction();                
            ClienteEntity c1 = data.get(0);
            c1.setNombre("Carlos Gonzal");
            em.persist(c1);
            ClienteEntity c2 = data.get(1);
            c2.setNombre("Carlos Andres");
            em.persist(c2);
            ClienteEntity c3 = data.get(2);
            c3.setNombre("Carlos Marado");
            em.persist(c3);
            List<ClienteEntity> entity = new ArrayList<>();
            entity = cp.findByNameLike("Car");
            Assert.assertNotNull(entity);
            Assert.assertEquals(entity.size(), 3);
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
    
    @Test
    public void getClienteCorreoTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = cp.findByCorreo(entity.getCorreo());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), entity.getContrasenia());
        Assert.assertEquals(entity.getCorreo(), entity.getCorreo());    
    }
    
}
