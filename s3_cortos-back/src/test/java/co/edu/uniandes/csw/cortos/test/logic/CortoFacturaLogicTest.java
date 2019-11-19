/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoFacturaLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
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
public class CortoFacturaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CortoLogic cl;
    
    @Inject
    private CortoFacturaLogic cfl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CortoEntity> data = new ArrayList<>();
    
    private List<FacturaEntity> facturas = new ArrayList<>();
    
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
        em.createQuery("delete from FacturaEntity").executeUpdate();
        em.createQuery("delete from CortoEntity").executeUpdate();
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
            FacturaEntity f = factory.manufacturePojo(FacturaEntity.class);
            em.persist(f);
            facturas.add(f);
            if(i==0)
                data.get(i).setFactura(f);
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
     * Test para reemplazar la factura
     */
    @Test
    public void replaceFacturaTest(){
        CortoEntity c = data.get(0);
       
        cfl.replaceFactura(c.getId(), facturas.get(1).getId());
        c = cl.getCorto(c.getId());
        
        Assert.assertEquals(c.getFactura().getFecha(), facturas.get(1).getFecha());
        Assert.assertEquals(c.getFactura().getCostoTotal(), facturas.get(1).getCostoTotal());
        Assert.assertEquals(c.getFactura().getNumeroFactura(), facturas.get(1).getNumeroFactura());  
        Assert.assertEquals(c.getFactura().getId(), facturas.get(1).getId());
    }
    /**
     * Quitar factura de un corto
     */
    @Test
    public void removeFacturaTest(){
        cfl.removeFactura(data.get(0).getId());
        CortoEntity c = cl.getCorto(data.get(0).getId());
        Assert.assertNull(c.getFactura());
    }
}
