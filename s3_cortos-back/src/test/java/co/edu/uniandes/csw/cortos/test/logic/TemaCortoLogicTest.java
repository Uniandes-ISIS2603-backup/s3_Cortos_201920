/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.ejb.TemaCortoLogic;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
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
public class TemaCortoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private TemaCortoLogic temaCortoLogic; 
    
    @Inject 
    private CortoLogic cortoLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<TemaEntity> data = new ArrayList<>();
    
    private CortoEntity corto = new CortoEntity();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TemaEntity.class.getPackage())
                .addPackage(CortoEntity.class.getPackage())
                .addPackage(TemaCortoLogic.class.getPackage())
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
        em.createQuery("delete from CorttoEntity").executeUpdate();
        em.createQuery("delete from TemaEntity").executeUpdate();
    }

    private void insertData() {
        corto = factory.manufacturePojo(CortoEntity.class);
        corto.setId(1L);
        corto.setTemas(new ArrayList<>());
        em.persist(corto);

        for (int i = 0; i < 3; i++) {
            TemaEntity entity = factory.manufacturePojo(TemaEntity.class);
            entity.setCortos(new ArrayList<>());
            entity.getCortos().add(corto);
            em.persist(entity);
            data.add(entity);
            corto.getTemas().add(entity);
        }
    }
    
    @Test
    public void addCortoTest() throws BusinessLogicException
    {
        //CortoEntity newCorto = factory.manufacturePojo(CortoEntity.class);
        //cortoLogic.createCorto(corto);
        
        //CortoEntity cortoEntity = temaCortoLogic.addCorto(, corto.getId())
    }
}
