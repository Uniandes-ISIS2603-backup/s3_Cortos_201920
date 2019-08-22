/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
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
public class CortosPersistenceTest {

    @PersistenceContext
    EntityManager em;
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CortoEntity.class)
                .addClass(CortoPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    @Inject
    CortoPersistence cp;
    @Test
    public void createTest(){
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
}
