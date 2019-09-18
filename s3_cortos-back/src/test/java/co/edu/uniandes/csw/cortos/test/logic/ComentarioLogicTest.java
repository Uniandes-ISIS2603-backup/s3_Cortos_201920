/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
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
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Vargas Vega
 */
@RunWith(Arquillian.class)
public class ComentarioLogicTest {
   
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject 
    private ComentarioLogic comentarioLogic;
    
    @PersistenceContext
    private EntityManager em;
    
     @Inject
    private UserTransaction utx;
    
    private List<ComentarioEntity> data = new ArrayList<ComentarioEntity>();
    
    
     @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
    }
    
    private void insertData(){
        for(int i =0;i<3;i++)
        {
            ComentarioEntity comentario= factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comentario);
            data.add(comentario);
        }
    }
    
    @Test
    public void createComentarioTest() throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojoWithFullData(ComentarioEntity.class);
        ComentarioEntity result = comentarioLogic.createComentario(newEntity);
        Assert.assertNotNull(result);
        ComentarioEntity entity= em.find(ComentarioEntity.class,result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
        Assert.assertEquals(entity.getFecha(), newEntity.getFecha());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void comentarioInvalidoPorContenido() throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setComentario("Hola marica");
        comentarioLogic.createComentario(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void comentarioVacio()throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setComentario("");
        comentarioLogic.createComentario(newEntity);
    }
    @Test(expected = BusinessLogicException.class)
    public void comentarioNulo()throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setComentario(null);
        comentarioLogic.createComentario(newEntity);
    }
    @Test(expected = BusinessLogicException.class)
    public void comentarioIdNulo()throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setId(null);
        comentarioLogic.createComentario(newEntity);
    }
    @Test 
    public void deleteTest()throws BusinessLogicException{
        ComentarioEntity c = data.get(0);
        comentarioLogic.deleteComentario(c.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, c.getId());
        Assert.assertNull(deleted);
    }
    @Test
    public void getComentariosTest() {
       List<ComentarioEntity> list = comentarioLogic.getComentarios();
        Assert.assertEquals(data.size(), list.size());
        for (ComentarioEntity ent : list) {
            boolean found = false;
            for (ComentarioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void getComentarioTest() {
        ComentarioEntity entity = data.get(0);
        ComentarioEntity newEntity = comentarioLogic.getComentario(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
        Assert.assertEquals(entity.getFecha(), newEntity.getFecha());
    }
    @Test
    public void updateComentarioTest() throws BusinessLogicException {
        ComentarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);

        newEntity.setId(entity.getId());

        comentarioLogic.updateComentario(entity.getId(),newEntity);

        ComentarioEntity resp = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
       
    }    
}
