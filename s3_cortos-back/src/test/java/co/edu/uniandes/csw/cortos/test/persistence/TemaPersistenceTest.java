/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;


import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.persistence.TemaPersistence;
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

public class TemaPersistenceTest {
    @PersistenceContext 
    EntityManager em;
    @Inject
    TemaPersistence temaPersistence;
    @Inject
    UserTransaction utx;
      
    private List<TemaEntity> data = new ArrayList<TemaEntity>();
    @Deployment
      public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(TemaEntity.class)
                .addClass(TemaPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
     
    @Before
    public void configTest()
    {
        try{
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
                
            }catch( Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }
    
    
     private void clearData()
    {
        em.createQuery("delete from TemaEntity").executeUpdate();
    }
     
      private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i=0; i<3 ; i++){
            TemaEntity entity = factory.manufacturePojo(TemaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
     
    @Test
    public void createTest()
    {
        PodamFactory factory  = new PodamFactoryImpl();
        TemaEntity tema = factory.manufacturePojo(TemaEntity.class);
        TemaEntity result = temaPersistence.create(tema);
        Assert.assertNotNull(result);
        
       TemaEntity entity= 
                em.find(TemaEntity.class,result.getId());
        
        Assert.assertEquals(entity.getNombre(), tema.getNombre());
    }
    @Test
    public void createTemaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        TemaEntity newEntity = factory.manufacturePojo(TemaEntity.class);
        TemaEntity result = temaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        TemaEntity entity = em.find(TemaEntity.class, result.getId());

      Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
     @Test
    public void getTemasTest() {
       List<TemaEntity> list = temaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (TemaEntity ent : list) {
            boolean found = false;
            for (TemaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void deleteTemaTest() {
        TemaEntity entity = data.get(0);
        temaPersistence.delete(entity.getId());
        TemaEntity deleted = em.find(TemaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
     @Test
    public void updateTemaTest() {
       TemaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TemaEntity newEntity = factory.manufacturePojo(TemaEntity.class);

        newEntity.setId(entity.getId());

        temaPersistence.update(newEntity);

        TemaEntity resp = em.find(TemaEntity.class, entity.getId());
       Assert.assertEquals(newEntity.getId(), resp.getId());
       
    }
}
