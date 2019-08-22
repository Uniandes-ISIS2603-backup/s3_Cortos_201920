/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest 
{
    @PersistenceContext
    protected EntityManager em;
   
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class).addClass(ClienteEntity.class)
                .addClass(ClientePersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    @Inject
    ClientePersistence cp;
    
    @Test
    public void createTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = cp.create(cliente);
        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        
        Assert.assertEquals(cliente.getNombre(), entity.getNombre());
        
    }
}
