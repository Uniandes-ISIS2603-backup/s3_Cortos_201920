/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
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
 * @author Santiago Vargas 
 */
@RunWith(Arquillian.class)
public class ComentarioTest {
   
    @PersistenceContext
    EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ComentarioEntity.class)
                .addClass(ComentarioPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Inject
    ComentarioPersistence coment;
    
    @Test
    public void createTest()
    {
        PodamFactory factory  = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        ComentarioEntity result = coment.create(comentario);
        Assert.assertNotNull(result);
        
       ComentarioEntity entity= 
                em.find(ComentarioEntity.class,result.getId());
        
        Assert.assertEquals(comentario.getComentario(),entity.getComentario());
    }

    
}
