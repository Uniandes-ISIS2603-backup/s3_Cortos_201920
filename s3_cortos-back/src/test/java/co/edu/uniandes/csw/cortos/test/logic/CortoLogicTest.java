/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
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
public class CortoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    /**
     * Referencia a la clase CortoLogic
     */
    @Inject
    private CortoLogic cl;
    @PersistenceContext
    EntityManager em;
    @Inject
    UserTransaction utx;
    private List<CortoEntity> data = new ArrayList<>();
    /**
     * 
     * @return jar de arquillian para desplegar en Payara embedido. 
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
    /**
     * Prueba la creacion de un Corto
     * @throws BusinessLogicException 
     */
    @Test
    public void createCorto() throws BusinessLogicException{
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        CortoEntity result = cl.createCorto(corto);
        Assert.assertNotNull(result);
        CortoEntity c = em.find(CortoEntity.class, result.getId());
        Assert.assertEquals(c.getNombre(), corto.getNombre());
        Assert.assertEquals(c.getPrecio(), corto.getPrecio());
        Assert.assertEquals(c.getCalificacionPromedio(), corto.getCalificacionPromedio());
        Assert.assertEquals(c.getFechaDePublicacion(), corto.getFechaDePublicacion());
        Assert.assertEquals(c.getDescripcion(), corto.getDescripcion());
    }
    /**
     * Prueba que si un corto es creado con nombre nulo debe lanzar excepcion
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void createCortoNombreNull() throws BusinessLogicException{
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        corto.setNombre(null);
        CortoEntity result = cl.createCorto(corto);
    }
    /**
     * Prueba que en la creacion de un corto con precio nulo debe lanzar excepcion
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void createCortoprecioNull() throws BusinessLogicException{
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        corto.setPrecio(null);
        CortoEntity result = cl.createCorto(corto);
    }
    /**
     * Prueba que en la creacion de un corto con descripcion nula lanza excepcion
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void createCortoDescripcionNull() throws BusinessLogicException{
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        corto.setDescripcion(null);
        CortoEntity result = cl.createCorto(corto);
    }
    /**
     * Prueba que muestra la correctitud del metodo de obtener todos los cortos
     */
    @Test
    public void getCortosTest(){
        List<CortoEntity> list = cl.getCortos();
        Assert.assertEquals(list.size(), data.size());
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
     * Prueba que muestra la correctitud de obtener un solo corto por su id
     */
    @Test
    public void getCortoTest(){
        CortoEntity c = data.get(0);
        CortoEntity corto = cl.getCorto(c.getId());
        Assert.assertNotNull(corto);
        Assert.assertEquals(c.getNombre(), corto.getNombre());
        Assert.assertEquals(c.getPrecio(), corto.getPrecio());
        Assert.assertEquals(c.getCalificacionPromedio(), corto.getCalificacionPromedio());
        Assert.assertEquals(c.getFechaDePublicacion(), corto.getFechaDePublicacion());
        Assert.assertEquals(c.getDescripcion(), corto.getDescripcion());
    }
    /**
     * Prueba de actualizar la informacion de un corto
     * @throws BusinessLogicException 
     */
    @Test
    public void updateCortoTest() throws BusinessLogicException{
        CortoEntity c = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);

        corto.setId(c.getId());

        cl.updateCorto(corto.getId(),corto);

        CortoEntity resp = em.find(CortoEntity.class, c.getId());
        Assert.assertEquals(resp.getNombre(), corto.getNombre());
        Assert.assertEquals(resp.getPrecio(), corto.getPrecio());
        Assert.assertEquals(resp.getCalificacionPromedio(), corto.getCalificacionPromedio());
        Assert.assertEquals(resp.getFechaDePublicacion(), corto.getFechaDePublicacion());
        Assert.assertEquals(resp.getDescripcion(), corto.getDescripcion());
    }
    /**
     * Prueba que si se actualiza un corto con nombre nulo se lanza excepcion
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateNombreNull() throws BusinessLogicException{
        CortoEntity c = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);

        corto.setId(c.getId());
        corto.setNombre(null);
        cl.updateCorto(corto.getId(),corto);
    }
     /**
     * Prueba que si se actualiza un corto con precio nulo se lanza excepcion
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePrecioNull() throws BusinessLogicException{
        CortoEntity c = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);

        corto.setId(c.getId());
        corto.setPrecio(null);
        cl.updateCorto(corto.getId(),corto);
    }
     /**
     * Prueba que si se actualiza un corto con descripcion nula se lanza excepcion
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateDescripcionNull() throws BusinessLogicException{
        CortoEntity c = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);

        corto.setId(c.getId());
        corto.setDescripcion(null);
        cl.updateCorto(corto.getId(),corto);
    }
    /**
     * Prueba del metodo para eliminar un corto.
     */
    @Test
    public void deleteCortoTest(){
        CortoEntity a = data.get(0);
        cl.deleteCorto(a.getId());
        CortoEntity c = em.find(CortoEntity.class, a.getId());
        Assert.assertNull(c);
    }
}
