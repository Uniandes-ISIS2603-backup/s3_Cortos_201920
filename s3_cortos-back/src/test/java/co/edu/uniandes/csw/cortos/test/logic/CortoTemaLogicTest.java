/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoTemaLogic;
import co.edu.uniandes.csw.cortos.ejb.TemaLogic;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
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
public class CortoTemaLogicTest {
    private PodamFactory fabrica = new PodamFactoryImpl();
    
    @Inject
    private TemaLogic tl;
    
    @Inject
    private CortoTemaLogic ctl;
    
    //private CortoEntity c = new CortoEntity();
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CortoEntity> data = new ArrayList<>();
    
    private List<TemaEntity> temas = new ArrayList<>();
    private static int contador = 0;
    private static int contadorInsert = 0;
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
        //System.out.println(temas.size() + "hola hola hola "+ data.size());
        em.createQuery("delete from CortoEntity").executeUpdate();
        em.createQuery("delete from TemaEntity").executeUpdate();
        
    }
    
    /**
     * Inserta datos iniciales para las pruebas
     */
    private void insertData(){
       
       for(int i = 0 ; i < 3; i++){
        CortoEntity c = fabrica.manufacturePojo(CortoEntity.class);
        
        c.setTemas(new ArrayList());
        em.persist(c);
        
        data.add(c);
       }
        
        for(int i = 0; i < 3; i++){
            TemaEntity t = fabrica.manufacturePojo(TemaEntity.class);
            t.setCortos(new ArrayList<>());
            t.getCortos().add(data.get(0));
            em.persist(t);
            temas.add(t);
            if(i<2){
                
            //System.out.println(temas.size() + "   t");
            data.get(0).getTemas().add(t);
            }
            
        }
        System.out.println(data.size() + "    "+ temas.size() + "   t");
    }
    
    /**
     * Configuracion inicial de la prueba
     */
    @Before
    public void configTest(){
        System.out.println(contador);
        try{
            
            utx.begin();
            
            clearData();
            
            //System.out.println("entro a insert/salgo de clear");
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
        System.out.println(temas.size() + "  qwerqwerqwer "+data.size());
    }
    
    /**
     * Prueba asociar tema con Corto
     */
    @Test
    public void addTemaTest() throws BusinessLogicException{

        TemaEntity t = fabrica.manufacturePojo(TemaEntity.class);
        
        tl.createTema(t);
        TemaEntity n = ctl.addTema(t.getId(), data.get(0).getId());
        Assert.assertNotNull(n);
    }
    
    /**
     * Prueba para obtener toda la lista de temas de un corto
     */
    @Test
    public void getTemasTest(){
       
        
        List<TemaEntity> lista = ctl.getTemas(data.get(0).getId());
        
        Assert.assertEquals(2,lista.size());
        for(int i = 0 ; i < 2; ++i){
            Assert.assertEquals(lista.get(i), temas.get(i));
        }
    }
    /**
     * Prueba para obtener Tema existente
     * @throws BusinessLogicException 
     */
    @Test
    public void getTemaTest() throws BusinessLogicException{
        
        CortoEntity c = data.get(0);
        TemaEntity tema = temas.get(0);
        TemaEntity resp = ctl.getTema(tema.getId(),c.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getNombre(), tema.getNombre());
    }
    
    /**
     * Prueba para recibir la excepcion por no existir Tema en la lista
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getTemaNoExisteTest()throws BusinessLogicException{
       System.out.println(data.size() + "    "+ temas.size() + "   t en test");
        CortoEntity c = data.get(0);
        TemaEntity tema = temas.get(2);
        TemaEntity resp = ctl.getTema(tema.getId(),c.getId());
    }
}
