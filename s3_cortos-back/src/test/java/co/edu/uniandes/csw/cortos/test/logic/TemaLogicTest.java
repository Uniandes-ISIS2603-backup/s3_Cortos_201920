/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.TemaLogic;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
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
public class TemaLogicTest {
    private PodamFactory factory  = new PodamFactoryImpl();
    
    @Inject 
    private TemaLogic temaLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx; 
    
    private List<TemaEntity> data = new ArrayList<TemaEntity>();
    
      @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TemaEntity.class.getPackage())
                .addPackage(TemaLogic.class.getPackage())
                .addPackage(TemaPersistence.class.getPackage())
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
        em.createQuery ("delete from TemaEntity").executeUpdate();
    }
    
    private void insertData()
    {
        for(int i = 0; i < 3 ; i++)
        {
            TemaEntity tema= factory.manufacturePojo(TemaEntity.class);
            em.persist(tema);
            data.add(tema);
        }
    }
    
    @Test
    public void createTemaTest() throws BusinessLogicException
    {
        TemaEntity newEntity = factory.manufacturePojoWithFullData(TemaEntity.class);
        TemaEntity result = temaLogic.createTema(newEntity);
        Assert.assertNotNull(result);
        TemaEntity entity= em.find(TemaEntity.class,result.getId());
        Assert.assertEquals(entity.getId(),newEntity.getId());
         Assert.assertEquals(entity.getNombre(),newEntity.getNombre());
    }
    
    @Test(expected = BusinessLogicException.class )
    public void temaInvalidoPoroNombreVacio() throws BusinessLogicException
    {
        TemaEntity newEntity= factory.manufacturePojo(TemaEntity.class);
        newEntity.setNombre("");
        temaLogic.createTema(newEntity);
    }   
    @Test 
    public void getTemasTest()
    {
        List<TemaEntity> list  = temaLogic.getTemas();
        Assert.assertEquals(data.size(),list.size());
        for (TemaEntity ent  :list)
        {
            boolean found = false;
            for (TemaEntity entity : data){
                if (ent.getId().equals(entity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
  
    @Test
    public void getTemaTest()
    {
        TemaEntity entity = data.get(0);
        TemaEntity newEntity = temaLogic.getTema(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(),newEntity.getId());
        Assert.assertEquals(entity.getNombre(),newEntity.getNombre());
    }
    
    @Test
    public void updateTemaTest() throws BusinessLogicException {
        TemaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TemaEntity newEntity = factory.manufacturePojo(TemaEntity.class);
        
        newEntity.setId(entity.getId ());
        
        temaLogic.updateTema(entity.getId(), newEntity);
        
        TemaEntity resp = em.find (TemaEntity.class,entity.getId());
        
        Assert.assertEquals(resp.getId(),newEntity.getId());
        Assert.assertEquals(resp.getNombre(),newEntity.getNombre());
    }
}
