/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
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
 * @author Santiago  Vargas Vega
 */
@RunWith(Arquillian.class)
public class CalificacionPersistenceTest {
    @PersistenceContext
    EntityManager em;
    @Inject
    CalificacionPersistence calificacionPersistence;
     @Inject
    UserTransaction utx;
     
     private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
     
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }
     
     private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i=0; i<3 ; i++){
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createTest()
    {
        PodamFactory factory  = new PodamFactoryImpl();
        CalificacionEntity calificacion = factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity result = calificacionPersistence.create(calificacion);
        Assert.assertNotNull(result);
        
       CalificacionEntity entity= 
                em.find(CalificacionEntity.class,result.getId());
        
        Assert.assertEquals(calificacion.getPuntaje(),entity.getPuntaje());
    }
    
      @Test
    public void createCalificacionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity result = calificacionPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());

      Assert.assertEquals(entity.getId(), newEntity.getId());
      Assert.assertEquals(entity.toString(), newEntity.toString());
    }
     @Test
    public void getCalificacionesTest() {
       List<CalificacionEntity> list = calificacionPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity ent : list) {
            boolean found = false;
            for (CalificacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     @Test
    public void deleteCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        calificacionPersistence.delete(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
     @Test
    public void updateCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());

        calificacionPersistence.update(newEntity);

        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
       Assert.assertEquals(newEntity.getId(), resp.getId());
       
    }
    
    @Test
    public void findCalificacionTest(){
        CalificacionEntity c = data.get(0);
        CalificacionEntity entity = calificacionPersistence.find(c.getId());
        Assert.assertEquals(c.getPuntaje(), entity.getPuntaje());
    }
}
