/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.persistence;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
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
 * @author Pedro Luis Callejas Pinzon
 */
@RunWith(Arquillian.class)
public class CineastaPersistenceTest {

    @Inject
    UserTransaction utx;

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private CineastaPersistence cp;

    private List<CineastaEntity> data = new ArrayList<CineastaEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CineastaEntity.class.getPackage())
                .addPackage(CineastaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Before
    public void setUp() {
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

    private void clearData() {
        em.createQuery("delete from CineastaEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CineastaEntity entity = factory.manufacturePojo(CineastaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createCineastaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);

        CineastaEntity cineasta = cp.create(newEntity);

        Assert.assertNotNull(cineasta);

        CineastaEntity entity = em.find(CineastaEntity.class, cineasta.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    @Test
    public void getCineastasTest() {
        List<CineastaEntity> list = cp.findAll();

        Assert.assertEquals(data.size(), list.size());

        for (CineastaEntity ent : list) {
            boolean found = false;
            for (CineastaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }

            Assert.assertTrue(found);
        }
    }

    @Test
    public void getCineastaTest() {
        CineastaEntity entity = data.get(0);
        CineastaEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    @Test
    public void updateCineastaTest() {
        CineastaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);

        newEntity.setId(entity.getId());

        cp.update(newEntity);

        CineastaEntity resp = em.find(CineastaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    @Test
    public void deleteCineastaTest() {
        CineastaEntity entity = data.get(0);
        cp.delete(entity.getId());
        CineastaEntity deleted = em.find(CineastaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
