/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoCineastasLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import java.util.ArrayList;
import java.util.Date;
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
 * @author Juan Sebastian Gomez
 */
@RunWith(Arquillian.class)
public class CortoCineastasLogicTest {
        private PodamFactory fabrica = new PodamFactoryImpl();
    
    @Inject
    private CortoLogic cl;
    
    @Inject
    private CortoCineastasLogic ccl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CortoEntity> data = new ArrayList<>();
    
    private List<CineastaEntity> cineastas = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CortoEntity.class.getPackage())
                .addPackage(CortoLogic.class.getPackage())
                .addPackage(CortoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    /**
     * Limpia las tablas que estan en la prueba
     */
    private void clearData(){
        em.createQuery("delete from CineastaEntity").executeUpdate();
        em.createQuery("delete from CortoEntity").executeUpdate();
    }
    
    /**
     * Inserta datos iniciales para las pruebas
     */
    private void insertData(){
        for(int i = 0; i < 3; ++i){
            CineastaEntity c = fabrica.manufacturePojo(CineastaEntity.class);
            c.setCorreo("pepito@correcto.com");
            c.setFechaNacimiento(new Date(98,2,21));
            em.persist(c);
            cineastas.add(c);
        }
        for(int i = 0 ; i < 3 ; ++ i){
            CortoEntity c = fabrica.manufacturePojo(CortoEntity.class);
            em.persist(c);
            data.add(c);
            if(i == 0)
                cineastas.get(i).setCortoCineastas(c);
        }
    }
    
    /**
     * Configuracion inicial de la prueba
     */
    @Before
    public void configTest(){
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        }catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Prueba asociar Cineasta con Corto
     */
    @Test
    public void addCineastaTest(){
        CortoEntity corto = data.get(0);
        CineastaEntity cineasta = cineastas.get(1);
        CineastaEntity resp = ccl.addCineasta(corto.getId(),cineasta.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(cineasta.getContrasenia(),resp.getContrasenia());
        Assert.assertEquals(cineasta.getNombre(),resp.getNombre());
        Assert.assertEquals(cineasta.getCorreo(),resp.getCorreo());
        Assert.assertEquals(cineasta.getGenero(),resp.getGenero());
        Assert.assertEquals(cineasta.getFechaNacimiento(),resp.getFechaNacimiento());
        Assert.assertEquals(cineasta.getTelefono(),resp.getTelefono());
        Assert.assertEquals(cineasta.getDireccion(),resp.getDireccion());
    }
    
    /**
     * Prueba para obtener toda la lista de cineastas de un corto
     */
    @Test
    public void getCineastasTest(){
        List<CineastaEntity> lista = ccl.getCineastas(data.get(0).getId());
        
        Assert.assertEquals(1,lista.size());
    }
    /**
     * Prueba para obtener cineasta existente
     * @throws BusinessLogicException 
     */
    @Test
    public void getCineastaTest() throws BusinessLogicException{
        CortoEntity c = data.get(0);
        CineastaEntity cineasta = cineastas.get(0);
        CineastaEntity resp = ccl.getCineasta(c.getId(),cineasta.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(cineasta.getContrasenia(),resp.getContrasenia());
        Assert.assertEquals(cineasta.getNombre(),resp.getNombre());
        Assert.assertEquals(cineasta.getCorreo(),resp.getCorreo());
        Assert.assertEquals(cineasta.getGenero(),resp.getGenero());
        Assert.assertEquals(cineasta.getFechaNacimiento(),resp.getFechaNacimiento());
        Assert.assertEquals(cineasta.getTelefono(),resp.getTelefono());
        Assert.assertEquals(cineasta.getDireccion(),resp.getDireccion());
        
    }
    
    /**
     * Prueba para recibir la excepcion por no existir cineasta en la lista
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getCineastaNoExisteTest()throws BusinessLogicException{
        CortoEntity c = data.get(0);
        CineastaEntity cineasta = cineastas.get(1);
        CineastaEntity resp = ccl.getCineasta(c.getId(), cineasta.getId());
    }
}
