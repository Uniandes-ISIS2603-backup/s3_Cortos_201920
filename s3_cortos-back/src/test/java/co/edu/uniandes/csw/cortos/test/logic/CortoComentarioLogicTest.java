/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CortoComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
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
public class CortoComentarioLogicTest {
    private PodamFactory fabrica = new PodamFactoryImpl();
    
    @Inject
    private CortoLogic cl;
    
    @Inject
    private CortoComentarioLogic ccl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CortoEntity> data = new ArrayList<>();
    
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    
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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from CortoEntity").executeUpdate();
    }
    
    /**
     * Inserta datos iniciales para las pruebas
     */
    private void insertData(){
        for(int i = 0; i < 3; ++i){
            ComentarioEntity c = fabrica.manufacturePojo(ComentarioEntity.class);
            c.setComentario("comentario"+ i);
            em.persist(c);
            comentarios.add(c);
        }
        for(int i = 0 ; i < 3 ; ++ i){
            CortoEntity c = fabrica.manufacturePojo(CortoEntity.class);
            em.persist(c);
            data.add(c);
            if(i == 0)
                comentarios.get(i).setCorto(c);
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
     * Prueba asociar Comentario con Corto
     */
    @Test
    public void addComentarioTest(){
        CortoEntity corto = data.get(0);
        ComentarioEntity comentario = comentarios.get(1);
        ComentarioEntity resp = ccl.addComentario(comentario.getId(), corto.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getComentario(), comentario.getComentario());
        Assert.assertEquals(resp.getId(), comentario.getId());
        Assert.assertEquals(resp.getFecha(), comentario.getFecha());
    }
    
    /**
     * Prueba para obtener toda la lista de comentarios de un corto
     */
    @Test
    public void getComentariosTest(){
        List<ComentarioEntity> lista = ccl.getComentarios(data.get(0).getId());
        
        Assert.assertEquals(1,lista.size());
    }
    /**
     * Prueba para obtener comentario existente
     * @throws BusinessLogicException 
     */
    @Test
    public void getComentarioTest() throws BusinessLogicException{
        CortoEntity c = data.get(0);
        ComentarioEntity comentario = comentarios.get(0);
        ComentarioEntity resp = ccl.getComentario(comentario.getId(),c.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getComentario(), comentario.getComentario());
        Assert.assertEquals(resp.getId(), comentario.getId());
        Assert.assertEquals(resp.getFecha(), comentario.getFecha());
    }
    
    /**
     * Prueba para recibir la excepcion por no existir comentario en la lista
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getComentarioNoExisteTest()throws BusinessLogicException{
        CortoEntity c = data.get(0);
        ComentarioEntity comentario = comentarios.get(1);
        ComentarioEntity resp = ccl.getComentario(comentario.getId(),c.getId());
    }
}
