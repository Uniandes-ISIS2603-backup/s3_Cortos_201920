/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.TemaCineastaLogic;
import co.edu.uniandes.csw.cortos.ejb.TemaCortoLogic;
import co.edu.uniandes.csw.cortos.ejb.TemaLogic;
import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.persistence.TemaPersistence;
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
 * @author Santiago Vargas Vega 
 */

@RunWith(Arquillian.class)
public class TemaCineastaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private TemaCineastaLogic temaCineastaLogic; 
    
    @Inject
    private TemaLogic temaLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<TemaEntity> data = new ArrayList<>();
    
    private List<CineastaEntity> cineastas = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TemaEntity.class.getPackage())
                .addPackage(TemaLogic.class.getPackage())
                .addPackage(TemaPersistence.class.getPackage())
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
    
    private void clearData() {
        em.createQuery("delete from TemaEntity").executeUpdate();
        em.createQuery("delete from CineastaEntity").executeUpdate();
     
       
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            TemaEntity c= factory.manufacturePojoWithFullData(TemaEntity.class);
            em.persist(c);
            data.add(c);
        }
         for (int i = 0; i < 3; i++) {
            CineastaEntity c= factory.manufacturePojoWithFullData(CineastaEntity.class);
            c.setTemas(new ArrayList<>());
            c.getTemas().add(data.get(0));
            em.persist(c);
            cineastas.add(c);
        }
    }
    
    @Test
    public void addTemaCineastaLogicTest()
    {
        TemaEntity tema = data.get(0);
        CineastaEntity cineasta = cineastas.get(1);
        CineastaEntity resp = temaCineastaLogic.addCineasta(tema.getId(), cineasta.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(),cineasta.getId());
        Assert.assertEquals(resp.getNombre(),cineasta.getNombre());
        Assert.assertEquals(resp.getDireccion(),cineasta.getDireccion());
        Assert.assertEquals(resp.getFechaNacimiento(),cineasta.getFechaNacimiento());
        Assert.assertEquals(resp.getCorreo(),cineasta.getCorreo());
        Assert.assertEquals(resp.getContrasenia(),cineasta.getContrasenia());
    }
    
    @Test
    public void getCineastaTest()
    {
        
        TemaEntity tema = data.get(0);
        CineastaEntity cineasta = cineastas.get(1);
        CineastaEntity resp = temaCineastaLogic.addCineasta(tema.getId(), cineasta.getId());
        List<CineastaEntity> prueba= temaCineastaLogic.getCineasta(data.get(0).getId());
        Assert.assertNotNull(prueba);
    }
    
}
