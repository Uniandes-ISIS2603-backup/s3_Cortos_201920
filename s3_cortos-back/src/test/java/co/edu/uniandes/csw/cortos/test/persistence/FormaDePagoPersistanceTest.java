/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import co.edu.uniandes.csw.cortos.persistence.FormaDePagoPersistance;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class FormaDePagoPersistanceTest {
    
    @PersistenceContext
    EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(FormaDePagoEntity.class)
                .addClass(FormaDePagoPersistance.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    FormaDePagoPersistance ep;
    
    @Inject
    UserTransaction utx;
    
    private List<FormaDePagoEntity> data = new ArrayList<FormaDePagoEntity>();
    
    @Before
    public void configTest()
    {
        try
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
            try{
                utx.rollback();
            } catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData()
    {
        em.createQuery("delete from FormaDePagoEntity").executeUpdate();
    }
    
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<3; i++)
        {
            FormaDePagoEntity entity = factory.manufacturePojo(FormaDePagoEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        FormaDePagoEntity formaDePago = factory.manufacturePojo(FormaDePagoEntity.class);
        FormaDePagoEntity result = ep.create(formaDePago);
        Assert.assertNotNull(result);
        
        
        FormaDePagoEntity entity = em.find(FormaDePagoEntity.class, result.getId());
        
        Assert.assertEquals(formaDePago.getNumero(), entity.getNumero());
        Assert.assertEquals(formaDePago.getFechaDeVencimiento(), entity.getFechaDeVencimiento());
        Assert.assertEquals(formaDePago.getCcv(), entity.getCcv());
    }

   @Test
   public void getFormasTest(){
        List <FormaDePagoEntity> lista = ep.findAll();
        Assert.assertEquals(data.size(), lista.size());
        for (FormaDePagoEntity ent : lista) {
            boolean fin = false;
            for (FormaDePagoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    fin = true;
                }
            }
            Assert.assertTrue(fin);
        }
   }
   
    @Test
    public void getFormaTest() {
        FormaDePagoEntity entity = data.get(0);
        FormaDePagoEntity newEntity = ep.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
        Assert.assertEquals(entity.getCcv(), newEntity.getCcv());
        Assert.assertEquals(entity.getFechaDeVencimiento(), newEntity.getFechaDeVencimiento());
    }
   
     @Test
    public void deleteFormaTest() {
        FormaDePagoEntity entity = data.get(0);
        ep.delete(entity.getId());
        FormaDePagoEntity deleted = em.find(FormaDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
     @Test
    public void updateFormaTest() {
        FormaDePagoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FormaDePagoEntity newEntity = factory.manufacturePojo(FormaDePagoEntity.class);

        newEntity.setId(entity.getId());

       ep.update(newEntity);

        FormaDePagoEntity resp = em.find(FormaDePagoEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getNumero(), resp.getNumero());
       
    }
   
}
