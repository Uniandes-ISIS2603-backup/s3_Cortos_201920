/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CineastaLogic;
import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import java.text.DateFormat;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class CineastaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private CineastaLogic cineastaLogic;

    @Inject
    UserTransaction utx;

    @Before
    public void configTest() {
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

    private void clearData() {
        em.createQuery("delete from CineastaEntity").executeUpdate();
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CineastaEntity.class.getPackage())
                .addPackage(CineastaLogic.class.getPackage())
                .addPackage(CineastaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createCineasta() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setCorreo("pepito@correcto.com");
        newEntity.setFechaNacimiento(new Date(98,2,21));
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
        Assert.assertNotNull(result);
        
        CineastaEntity entity = em.find(CineastaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
    }
    @Test
    public void getCineasta() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setCorreo("pepito@correcto.com");
        newEntity.setFechaNacimiento(new Date(98,2,21));
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
        Assert.assertNotNull(result);
        
        result = cineastaLogic.getCineasta(newEntity.getId());
        CineastaEntity entity = em.find(CineastaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
    }
    
    @Test
    public void updateCineasta() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setCorreo("pepito@correcto.com");
        newEntity.setFechaNacimiento(new Date(98,2,21));
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
        CineastaEntity entity1 = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setCorreo("pepito@corrfecto.com");
        newEntity.setFechaNacimiento(new Date(98,2,21));
        entity1.setId(newEntity.getId());
        
        
        Assert.assertNotNull(result);
        
        result= cineastaLogic.updateCineasta (newEntity.getId(),entity1);
        
        CineastaEntity entity = em.find(CineastaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
    }




    @Test(expected = BusinessLogicException.class)
    public void createCineastaNombreVacio() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setNombre("");
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createCineastaCorreoNoValido()throws BusinessLogicException{
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setCorreo("asdfasdfasefd");
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createCineastaContraseniaVacio() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setContrasenia("");
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }



    @Test(expected = BusinessLogicException.class)
    public void createCineastaCorreoVacio() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setCorreo("");
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }




    @Test(expected = BusinessLogicException.class)
    public void createCineastaTelefonoVacio() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setTelefono("");
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }



    @Test(expected = BusinessLogicException.class)
    public void createCineastaDireccionVacio() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setDireccion("");
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createCineastaRolNull() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setRol(null);
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }

      @Test(expected = BusinessLogicException.class)
     public void createCineastaGeneroNull()throws BusinessLogicException
     {
         CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
         newEntity.setGenero(null);
         CineastaEntity result = cineastaLogic.createCineasta(newEntity);
     }
     
    @Test(expected = BusinessLogicException.class)
    public void createCineastaCorreoRepetido() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        CineastaEntity newEntity2 = factory.manufacturePojo(CineastaEntity.class);
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
        newEntity.setCorreo(result.getCorreo());
        CineastaEntity result2 = cineastaLogic.createCineasta(newEntity2);
    }

    @Test(expected = BusinessLogicException.class)
    public void createCineastaFechaNacimientoNull() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setFechaNacimiento(null);
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCineastaFechaNacimientoMenorA13() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        newEntity.setFechaNacimiento(null);
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCineastaFechaNacimientoMayorA120() throws BusinessLogicException {
        CineastaEntity newEntity = factory.manufacturePojo(CineastaEntity.class);
        Date anios = new Date(0, 0, 1);//Aun no esta lafecha mirar el API de date
        newEntity.setFechaNacimiento(null);
        CineastaEntity result = cineastaLogic.createCineasta(newEntity);
    }
}
