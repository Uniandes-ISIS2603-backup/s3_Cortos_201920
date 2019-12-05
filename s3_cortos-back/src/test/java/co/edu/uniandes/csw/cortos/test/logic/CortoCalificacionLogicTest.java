/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoCalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
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
 * @author Juan Sebastian Gomez
 */
@RunWith(Arquillian.class)
public class CortoCalificacionLogicTest {
    private PodamFactory fabrica = new PodamFactoryImpl();
    
    @Inject
    private CortoLogic cl;
    
    @Inject
    private CortoCalificacionLogic ccl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CortoEntity> data = new ArrayList<>();
    
    private List<CalificacionEntity> calificaciones = new ArrayList<>();
    
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from CortoEntity").executeUpdate();
    }
    
    /**
     * Inserta datos iniciales para las pruebas
     */
    private void insertData(){
        for(int i = 0; i < 3; ++i){
            CalificacionEntity c = fabrica.manufacturePojo(CalificacionEntity.class);
            c.setPuntaje(i+1);
            em.persist(c);
            calificaciones.add(c);
        }
        for(int i = 0 ; i < 3 ; ++ i){
            CortoEntity c = fabrica.manufacturePojo(CortoEntity.class);
            em.persist(c);
            data.add(c);
            if(i == 0)
                calificaciones.get(i).setCorto(c);
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
     * Prueba asociar Calificacion con Corto
     */
    @Test
    public void addCalificacionTest(){
        CortoEntity corto = data.get(0);
        CalificacionEntity calificacion = calificaciones.get(1);
        CalificacionEntity resp = ccl.addCalificacion(calificacion.getId(), corto.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getPuntaje(), calificacion.getPuntaje());
        Assert.assertEquals(resp.getId(), calificacion.getId());  
    }
    
    /**
     * Prueba para obtener toda la lista de calificaciones de un corto
     */
    @Test
    public void getCalificacionesTest(){
        List<CalificacionEntity> lista = ccl.getCalificaciones(data.get(0).getId());
        
        Assert.assertEquals(1,lista.size());
    }
    /**
     * Prueba para obtener calificacion existente
     * @throws BusinessLogicException 
     */
    @Test
    public void getCalificacionTest() throws BusinessLogicException{
        CortoEntity c = data.get(0);
        CalificacionEntity cal = calificaciones.get(0);
        CalificacionEntity resp = ccl.getCalificacion(cal.getId(),c.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getPuntaje(), cal.getPuntaje());
        Assert.assertEquals(resp.getId(), cal.getId());
    }
    
    /**
     * Prueba para recibir la excepcion por no existir calificacion en la lista
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getCalificacionNoExisteTest()throws BusinessLogicException{
        CortoEntity c = data.get(0);
        CalificacionEntity cal = calificaciones.get(1);
        CalificacionEntity resp = ccl.getCalificacion(cal.getId(),c.getId());
    }
}
