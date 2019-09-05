/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;


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
public class CortosPersistenceTest {
    @Inject
    CortoPersistence cp;
    @Inject
    UserTransaction utx;
    @PersistenceContext
    EntityManager em;
    private List<CortoEntity> data = new ArrayList<>();
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CortoEntity.class.getPackage())
                .addPackage(CortoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
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
        em.createQuery("delete from CortoEntity").executeUpdate();
    }
        /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CortoEntity entity = factory.manufacturePojo(CortoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createCortoTest(){
        PodamFactory factory = new PodamFactoryImpl();
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        CortoEntity result = cp.create(corto);
        Assert.assertNotNull(result);
        CortoEntity c = em.find(CortoEntity.class, result.getId());
        Assert.assertEquals(c.getNombre(), corto.getNombre());
        Assert.assertEquals(c.getPrecio(), corto.getPrecio());
        Assert.assertEquals(c.getCalificacionPromedio(), corto.getCalificacionPromedio());
        Assert.assertEquals(c.getFechaDePublicacion(), corto.getFechaDePublicacion());
        Assert.assertEquals(c.getDescripcion(), corto.getDescripcion());
    }
     /**
     * Prueba para consultar la lista de cortos.
     */
    @Test
    public void getCortosTest() {
        List<CortoEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CortoEntity ent : list) {
            boolean found = false;
            for (CortoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     /**
     * Prueba para consultar un Corto.
     */
    @Test
    public void getCortoTest() {
        CortoEntity c = data.get(0);
        CortoEntity corto = cp.find(c.getId());
        Assert.assertNotNull(corto);
        Assert.assertEquals(c.getNombre(), corto.getNombre());
        Assert.assertEquals(c.getPrecio(), corto.getPrecio());
        Assert.assertEquals(c.getCalificacionPromedio(), corto.getCalificacionPromedio());
        Assert.assertEquals(c.getFechaDePublicacion(), corto.getFechaDePublicacion());
        Assert.assertEquals(c.getDescripcion(), corto.getDescripcion());
    }
       /**
     * Prueba para eliminar un Book.
     */
    @Test
    public void deleteCortoTest() {
        CortoEntity entity = data.get(0);
        cp.delete(entity.getId());
        CortoEntity deleted = em.find(CortoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
        @Test
    public void updateBookTest() {
        CortoEntity c = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);

        corto.setId(c.getId());

        cp.update(corto);

        CortoEntity resp = em.find(CortoEntity.class, c.getId());
        Assert.assertEquals(resp.getNombre(), corto.getNombre());
        Assert.assertEquals(resp.getPrecio(), corto.getPrecio());
        Assert.assertEquals(resp.getCalificacionPromedio(), corto.getCalificacionPromedio());
        Assert.assertEquals(resp.getFechaDePublicacion(), corto.getFechaDePublicacion());
        Assert.assertEquals(resp.getDescripcion(), corto.getDescripcion());
        
    }
}
