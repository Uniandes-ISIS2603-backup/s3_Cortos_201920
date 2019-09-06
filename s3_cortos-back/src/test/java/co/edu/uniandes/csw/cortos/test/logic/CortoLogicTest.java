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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
    @Inject
    private CortoLogic cl;
    @PersistenceContext
    EntityManager em;
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CortoEntity.class.getPackage())
                .addPackage(CortoLogic.class.getPackage())
                .addPackage(CortoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
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
    @Test (expected = BusinessLogicException.class)
    public void createCortoNombreNull() throws BusinessLogicException{
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        corto.setNombre(null);
        CortoEntity result = cl.createCorto(corto);
    }
    @Test (expected = BusinessLogicException.class)
    public void createCortoprecioNull() throws BusinessLogicException{
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        corto.setPrecio(null);
        CortoEntity result = cl.createCorto(corto);
    }
    @Test (expected = BusinessLogicException.class)
    public void createCortoDescripcionNull() throws BusinessLogicException{
        CortoEntity corto = factory.manufacturePojo(CortoEntity.class);
        corto.setDescripcion(null);
        CortoEntity result = cl.createCorto(corto);
    }
}
