/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.ClienteLogic;
import co.edu.uniandes.csw.cortos.ejb.FormaDePagoLogic;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author Juan Felipe Mejia Parra
 */
@RunWith(Arquillian.class)
public class FormaDePagoLogicTest {
    
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    protected EntityManager em;
    
    @Inject
    private FormaDePagoLogic formaDePagoLogic;
    
    @Inject
    UserTransaction utx;
    
    @Before
    public void configTest() 
    {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
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
    
    private void clearData() 
    {
        em.createQuery("delete from FormaDePagoEntity").executeUpdate();
    }
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Test
    public void createFormaDePago()throws BusinessLogicException
     {
         FormaDePagoEntity newEntity = factory.manufacturePojo(FormaDePagoEntity.class);
         FormaDePagoEntity result = formaDePagoLogic.createFormaDePago(newEntity);
         Assert.assertNotNull(result); 
         
         FormaDePagoEntity entity = em.find(FormaDePagoEntity.class, result.getId());
         Assert.assertEquals(entity.getNumero(),result.getNumero());
     }
    
    @Test(expected = BusinessLogicException.class)
    public void createFormaDePagoNumeroInvalidoMayor() throws BusinessLogicException
    {
        FormaDePagoEntity newEntity = factory.manufacturePojo(FormaDePagoEntity.class);
        newEntity.setNumero(100000000000000000L);
        FormaDePagoEntity result = formaDePagoLogic.createFormaDePago(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createFormaDePagoNumeroInvalidoMenor() throws BusinessLogicException
    {
        FormaDePagoEntity newEntity = factory.manufacturePojo(FormaDePagoEntity.class);
        newEntity.setNumero(1000000L);
        FormaDePagoEntity result = formaDePagoLogic.createFormaDePago(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createFormaDePagoCCVInvalidoMenor() throws BusinessLogicException
    {
        FormaDePagoEntity newEntity = factory.manufacturePojo(FormaDePagoEntity.class);
        newEntity.setCcv(25);
        FormaDePagoEntity result = formaDePagoLogic.createFormaDePago(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createFormaDePagoCCVInvalidoMayor() throws BusinessLogicException
    {
        FormaDePagoEntity newEntity = factory.manufacturePojo(FormaDePagoEntity.class);
        newEntity.setCcv(1000);
        FormaDePagoEntity result = formaDePagoLogic.createFormaDePago(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createFormaDePagoFechaInvalida() throws BusinessLogicException, ParseException
    {
        FormaDePagoEntity newEntity = factory.manufacturePojo(FormaDePagoEntity.class);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        Date d = sdf.parse("12/15");
        newEntity.setFechaDeVencimiento(d);
        FormaDePagoEntity result = formaDePagoLogic.createFormaDePago(newEntity);
    }
}
