/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.FacturaCortoLogic;
import co.edu.uniandes.csw.cortos.ejb.FacturaLogic;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
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
 * @author Ingrith Barbosa
 */
@RunWith(Arquillian.class)
public class FacturaCortoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private FacturaLogic cl;
    
    @Inject
    private FacturaCortoLogic fcl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<FacturaEntity> data = new ArrayList<>();
    
    private List<CortoEntity> cortos = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
     @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    /**
     * Limpia datos antes de un test
     */
    private void clearData(){
        em.createQuery("delete from CortoEntity").executeUpdate();
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }
    /**
     * aniade datos antes de un test
     */
    private void insertData(){
        for(int i = 0; i < 3; i++){
            FacturaEntity f = factory.manufacturePojo(FacturaEntity.class);
            em.persist(f);
            data.add(f);
        }
        for(int i = 0; i < 3; i++){
            CortoEntity c = factory.manufacturePojo(CortoEntity.class);
            em.persist(c);
            cortos.add(c);
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
    @Test
    public void addCortoTest()
    {
        FacturaEntity factura = data.get(0);
        CortoEntity corto = cortos.get(1);
        CortoEntity  resp =fcl.addCorto(factura.getId(),corto.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(), corto.getId());
        Assert.assertEquals(resp.getDescripcion(),corto.getDescripcion());
        Assert.assertEquals(resp.getFechaDePublicacion(),corto.getFechaDePublicacion());
        Assert.assertEquals(resp.getNombre(),corto.getNombre());
        Assert.assertEquals(resp.getCalificacionPromedio(),corto.getCalificacionPromedio());
        Assert.assertEquals(resp.getPrecio(),corto.getPrecio());
    }
    
}
