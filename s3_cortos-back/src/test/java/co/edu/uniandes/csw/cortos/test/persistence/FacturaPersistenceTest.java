/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
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
public class FacturaPersistenceTest 
{ 
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<FacturaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    @Inject
    FacturaPersistence fp;
    @Test
    public void createTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity factura = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity result = fp.create(factura);
        Assert.assertNotNull(result);
        
        FacturaEntity nueva= em.find(FacturaEntity.class, result.getId());
        Assert.assertEquals(result.getNumeroFactura(), nueva.getNumeroFactura());
        Assert.assertEquals(result.getFecha(), nueva.getFecha());
        Assert.assertEquals(result.getCostoTotal(), nueva.getCostoTotal());
    }
    /**
     * Prueba para crear una nueva factura
     */
    @Test
    public void constructorFacturaTest()
    {
        Date fecha= new Date();
        FacturaEntity factura= new FacturaEntity(123456, 350.99, fecha);
        Assert.assertEquals(123456, factura.getNumeroFactura(), 0 );
        Assert.assertEquals(350.99, factura.getCostoTotal(), 0);
        Assert.assertEquals(fecha, factura.getFecha());
    }
    /**
     * Prueba para consultar una factura.
     */
    @Test
    public void findTest()
    {
        FacturaEntity factura= data.get(0);
        FacturaEntity newFactura= fp.find(factura.getId());
        Assert.assertNotNull(newFactura);
        Assert.assertEquals(factura.getFecha(), newFactura.getFecha());
        Assert.assertEquals(factura.getCostoTotal(), newFactura.getCostoTotal());
        Assert.assertEquals(factura.getNumeroFactura(), newFactura.getNumeroFactura());   
    }
    /**
     * Prueba para consultar la lista de facturas.
     */
    @Test
    public void findAllTest()
    {
        List<FacturaEntity> lista= fp.findAll();
        Assert.assertEquals(lista.size(), data.size());
        for (FacturaEntity ent : lista) {
            boolean found = false;
            for (FacturaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
        
    }
    /**
     * Prueba para actualizar una factura.
     */
    @Test
    public void updateTest() {
        FacturaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);

        newEntity.setId(entity.getId());

        fp.update(newEntity);

        FacturaEntity resp = em.find(FacturaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getCostoTotal(), resp.getCostoTotal());
        Assert.assertEquals(newEntity.getNumeroFactura(), resp.getNumeroFactura());
    }

}
