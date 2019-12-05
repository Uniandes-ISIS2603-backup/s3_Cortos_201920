/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CineastaCortoLogic;
import co.edu.uniandes.csw.cortos.ejb.CineastaLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoCineastasLogic;

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
public class CineastaCortoLogicTest {
        private PodamFactory fabrica = new PodamFactoryImpl();
    
    @Inject
    private CineastaLogic cl;
    
    @Inject
    private CineastaCortoLogic ccl;
    
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
                .addPackage(CineastaLogic.class.getPackage())
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
         for(int i = 0 ; i < 3 ; ++ i){
            CortoEntity c = fabrica.manufacturePojo(CortoEntity.class);
            em.persist(c);
            data.add(c);
            
        }
        for(int i = 0; i < 3; ++i){
            CineastaEntity c = fabrica.manufacturePojo(CineastaEntity.class);
            c.setCorreo("pepito@correcto.com");
            c.setFechaNacimiento(new Date(98,2,21));
            c.setCortoCineastas(new ArrayList());
            em.persist(c);
            cineastas.add(c);
            if(i==0)
               c.getCortoCineastas().add(data.get(0));
            
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
    public void addCineastaTest() throws BusinessLogicException{
        CortoEntity corto = data.get(1);
        //System.out.println("hola?");
        
        CineastaEntity cineasta = cineastas.get(0);
        CortoEntity resp = ccl.addCorto(cineasta.getId(),corto.getId());
        
        Assert.assertNotNull(resp);
        
        
        
        
        
    }
//    
//    /**
//     * Prueba para obtener toda la lista de cineastas de un corto
//     */
//    @Test
//    public void getCineastasTest(){
//        
//        List<CortoEntity> lista = ccl.getCortos(cineastas.get(0).getId());
//        
//        Assert.assertEquals(1,lista.size());
//    }
  
}
