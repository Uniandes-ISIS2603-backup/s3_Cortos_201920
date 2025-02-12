/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.ejb.TemaCortoLogic;
import co.edu.uniandes.csw.cortos.ejb.TemaLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
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
public class TemaCortoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private TemaCortoLogic temaCortoLogic; 
    
    @Inject
    private TemaLogic temaLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<TemaEntity> data = new ArrayList<>();
    
    private List<CortoEntity> cortos = new ArrayList<>();
    
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
    
    private void clearData() {
        em.createQuery("delete from TemaEntity").executeUpdate();
        em.createQuery("delete from CortoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            TemaEntity c= factory.manufacturePojoWithFullData(TemaEntity.class);
            em.persist(c);
            data.add(c);
        }
         for (int i = 0; i < 3; i++) {
            CortoEntity c= factory.manufacturePojoWithFullData(CortoEntity.class);
            c.setTemas(new ArrayList<>());
            c.getTemas().add(data.get(0));
            em.persist(c);
            cortos.add(c);
        }
    }
    
   @Test
   public void addTemaCortoLogicTest()
   {
       TemaEntity tema = data.get(0);
       CortoEntity corto = cortos.get(1);
       CortoEntity resp = temaCortoLogic.addCorto(tema.getId(),corto.getId());
       Assert.assertNotNull(resp);
       Assert.assertEquals(resp.getDescripcion(),corto.getDescripcion());
       Assert.assertEquals(resp.getId(),corto.getId()); 
       Assert.assertEquals(resp.getNombre(),corto.getNombre()); 
       Assert.assertEquals(resp.getPrecio(),corto.getPrecio());
   }
   
   @Test
   public void getCortoTest()
   {
       TemaEntity tema = data.get(0);
       CortoEntity corto = cortos.get(1);
       CortoEntity resp = temaCortoLogic.addCorto(tema.getId(),corto.getId());
       List<CortoEntity> prueba= temaCortoLogic.getCorto(data.get(0).getId());
       Assert.assertNotNull(prueba);
   }
}
