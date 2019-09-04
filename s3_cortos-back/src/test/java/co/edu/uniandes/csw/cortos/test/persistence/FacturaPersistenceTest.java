/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Ingrith Barbosa
 */
@RunWith(Arquillian.class)
public class FacturaPersistenceTest 
{
    @Deployment
    public JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
        .addClass(FacturaEntity.class)
        .addClass(FacturaPersistence.class)
        .addAsManifestResource("METAINF/persistence.xml", "persistence.xml")
        .addAsManifestResource("METAINF/beans.xml", "beans.xml" );
    }
    @Inject
    FacturaPersistence fp;
    
    @Test
    public void createTest()
    {
        FacturaEntity result= fp.create(factura);
        Assert.assertNotNull(result);
    }
}
