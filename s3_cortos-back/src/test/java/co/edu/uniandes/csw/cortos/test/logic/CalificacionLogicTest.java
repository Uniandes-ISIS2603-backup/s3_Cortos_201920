/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
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
 * @author Santiago Vargas Vega
 */

@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CalificacionLogic  calificacionLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
     @Before
    public void configTest() {
        try {
            utx.begin();
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
    
    private void clearData(){
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }
    
    private void insertData(){
        for(int i =0;i<3;i++)
        {
            CalificacionEntity comentario= factory.manufacturePojo(CalificacionEntity.class);
            em.persist(comentario);
            data.add(comentario);
        }
    }
    
    @Test
    public void createCalificacionTest() throws BusinessLogicException
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);
        Assert.assertNotNull(result);
        CalificacionEntity entity= em.find(CalificacionEntity.class,result.getId());
        Assert.assertEquals(entity.getId(),newEntity.getId());
        Assert.assertEquals(entity.getPuntaje(),newEntity.getPuntaje());
        
    }
    
    @Test(expected = BusinessLogicException.class)
    public void calificacionInvalidaporPuntaje() throws BusinessLogicException
    {
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setPuntaje(-11);
        calificacionLogic.createCalificacion(newEntity);
    }
        @Test(expected = BusinessLogicException.class)
    public void calificacionInvalidaporPuntajeMayor5() throws BusinessLogicException
    {
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setPuntaje(7);
        calificacionLogic.createCalificacion(newEntity);
    }
        @Test(expected = BusinessLogicException.class)
    public void calificacionInvalidaPorId() throws BusinessLogicException
    {
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setId(null);
        calificacionLogic.createCalificacion(newEntity);
    }
     @Test
    public void getCalificacionesTest() {
       List<CalificacionEntity> list = calificacionLogic.getCalificaciones();
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
    public void deleteCalificacionTest() throws BusinessLogicException{
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacion(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    @Test
    public void updateCalificacionTest() throws BusinessLogicException{
        CalificacionEntity entity = data.get(0);
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());

        calificacionLogic.updateCalificacion(entity.getId(),newEntity);

        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
       Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionCalificacionIdNula()throws BusinessLogicException{
        CalificacionEntity entity = data.get(0);
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setId(null);
        calificacionLogic.updateCalificacion(entity.getId(),newEntity);

        
    }
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionCalificacionMenor()throws BusinessLogicException{
        CalificacionEntity entity = data.get(0);
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setPuntaje(-1);
        calificacionLogic.updateCalificacion(entity.getId(),newEntity);

        
    }
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionCalificacionMayor()throws BusinessLogicException{
        CalificacionEntity entity = data.get(0);
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setPuntaje(6);
        calificacionLogic.updateCalificacion(entity.getId(),newEntity);

        
    }
    @Test
    public void findCalificacionTest(){
        CalificacionEntity c = data.get(0);
        CalificacionEntity entity = calificacionLogic.getCalificacion(c.getId());
        Assert.assertEquals(c.getPuntaje(), entity.getPuntaje());
    }
}
