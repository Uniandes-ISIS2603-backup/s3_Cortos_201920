/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoCineastaProductorLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
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
 * @author Juan Sebastian Gomez
 */
@RunWith(Arquillian.class)
public class CortoCineastaProductorTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CortoLogic cl;
    
    @Inject
    private CortoCineastaProductorLogic ccl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CortoEntity> data = new ArrayList<>();
    
    private List<CineastaEntity> productores = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
     @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CortoEntity.class.getPackage())
                .addPackage(CortoLogic.class.getPackage())
                .addPackage(CortoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    /**
     * Limpia datos antes de un test
     */
    private void clearData(){
        em.createQuery("delete from CortoEntity").executeUpdate();
        em.createQuery("delete from CineastaEntity").executeUpdate();
        
    }
    /**
     * aniade datos antes de un test
     */
    private void insertData(){
        for(int i = 0; i < 3; i++){
            CortoEntity c = factory.manufacturePojo(CortoEntity.class);
            em.persist(c);
            data.add(c);
        }
        for(int i = 0; i < 3; i++){
            CineastaEntity f = factory.manufacturePojo(CineastaEntity.class);
            em.persist(f);
            productores.add(f);
            if(i==0)
                data.get(i).setProductor(f);
        }
    }
    /**
     * configuracion de test
     */
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
    /**
     * Test para reemplazar productor
     */
    @Test
    public void replaceProductorTest(){
        CortoEntity c = data.get(0);
        ccl.replaceProductor(c.getId(), productores.get(1).getId());
        c = cl.getCorto(c.getId());
        Assert.assertEquals(c.getProductor(), productores.get(1));
    }
    /**
     * Quitar productor de un corto
     */
    @Test
    public void removeProductorTest(){
        
        ccl.removeProductor(data.get(0).getId());
        CortoEntity c = cl.getCorto(data.get(0).getId());
        Assert.assertNull(c.getFactura());
    }
}
