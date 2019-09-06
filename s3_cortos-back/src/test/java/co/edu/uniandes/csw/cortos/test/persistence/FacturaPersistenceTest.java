/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
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
 * @author Ingrith Barbosa
 */
@RunWith(Arquillian.class)
public class FacturaPersistenceTest 
{
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
        .addClass(FacturaEntity.class)
        .addClass(FacturaPersistence.class)
        .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
        .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
    }
    @Inject
    FacturaPersistence fp;
    @PersistenceContext
    EntityManager em;
    @Test
    public void createTest()
    {
<<<<<<< HEAD
      
=======
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity factura = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity result = fp.create(factura);
        Assert.assertNotNull(result);
>>>>>>> 423ea5983a7aa3450b1a7cf59dab741f4afe5539
    }
}
