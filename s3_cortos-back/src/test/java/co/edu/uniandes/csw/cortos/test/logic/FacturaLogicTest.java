/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.FacturaLogic;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
import java.util.ArrayList;
import java.util.Date;
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
public class FacturaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private FacturaLogic  facturaLogic;
    
    @PersistenceContext
    private EntityManager em;
    
     @Inject
    private UserTransaction utx;
     
    private List<FacturaEntity> data = new ArrayList<FacturaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
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
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }
    
    private void insertData(){
        for(int i =0;i<3;i++)
        {
            FacturaEntity factura= factory.manufacturePojo(FacturaEntity.class);
            em.persist(factura);
            data.add(factura);
        }
    }
    @Test
    public void createFacturaTest() throws BusinessLogicException
    {
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
        Assert.assertNotNull(result);
        FacturaEntity entity= em.find(FacturaEntity.class,result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNumeroFactura(), newEntity.getNumeroFactura());
        Assert.assertEquals(entity.getFecha(), newEntity.getFecha());
        Assert.assertEquals(entity.getCostoTotal(), newEntity.getCostoTotal());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createFacturaFechaNull()throws BusinessLogicException
    {
        FacturaEntity newEntity = data.get(0);
        newEntity.setFecha(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createFacturaNumeroRep()throws BusinessLogicException
    {
        FacturaEntity newEntity = data.get(0);
        FacturaEntity newEntity2 = data.get(1);
        FacturaEntity result = facturaLogic.createFactura(newEntity2);
        newEntity.setNumeroFactura(newEntity2.getNumeroFactura());
        FacturaEntity result2 = facturaLogic.createFactura(newEntity);
    }
    
    
    
    @Test(expected = BusinessLogicException.class)
    public void createFacturaNumeroNeg()throws BusinessLogicException
    {
        FacturaEntity newEntity = data.get(0);
        newEntity.setNumeroFactura(-500);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }
   
    @Test(expected = BusinessLogicException.class)
    public void createFacturaCostoNeg()throws BusinessLogicException
    {
        FacturaEntity newEntity = data.get(0);
        newEntity.setCostoTotal(-2000.6);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }
    @Test(expected = BusinessLogicException.class)
    public void createFacturaCostoNull()throws BusinessLogicException
    {
        FacturaEntity newEntity = data.get(0);
        newEntity.setCostoTotal(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }
    @Test(expected = BusinessLogicException.class)
    public void createFacturaNumeroNull()throws BusinessLogicException
    {
        FacturaEntity newEntity = data.get(0);
        newEntity.setNumeroFactura(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }
     @Test(expected = BusinessLogicException.class)
    public void updateFacturaFechaNull()throws BusinessLogicException
    {
        FacturaEntity factura= data.get(0);
        factura.setFecha(null);
        FacturaEntity result =facturaLogic.updateFactura(factura.getId(),factura);
    }
    @Test(expected = BusinessLogicException.class)
    public void updateFacturaNumeroRep()throws BusinessLogicException
    {
        FacturaEntity factura1=data.get(0);
        FacturaEntity factura2=data.get(1);
        factura1.setNumeroFactura(factura2.getNumeroFactura());
        FacturaEntity result =facturaLogic.updateFactura(factura1.getId(), factura1);
    }
    @Test(expected = BusinessLogicException.class)
    public void updateFacturaNumeroNeg()throws BusinessLogicException
    {
        FacturaEntity factura=data.get(0);
        factura.setNumeroFactura(-500);
        FacturaEntity result =facturaLogic.updateFactura(factura.getId(), factura);
    }
    @Test(expected = BusinessLogicException.class)
    public void updateFacturaCostoNeg()throws BusinessLogicException
    {
        FacturaEntity factura= data.get(0);
        factura.setCostoTotal(-350.99);
        FacturaEntity result = facturaLogic.updateFactura(factura.getId(), factura);
    }
     @Test(expected = BusinessLogicException.class)
    public void updateFacturaCostoNull()throws BusinessLogicException
    {
        FacturaEntity factura = data.get(0);
        factura.setCostoTotal(null);
        FacturaEntity result =facturaLogic.updateFactura(factura.getId(), factura);
    }
    @Test(expected = BusinessLogicException.class)
    public void updateFacturaNumeroNull()throws BusinessLogicException
    {
        FacturaEntity factura = data.get(0);
        factura.setNumeroFactura(null);
        FacturaEntity result =facturaLogic.updateFactura(factura.getId(), factura);
    }
    
}
