/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteFormaPagoLogic;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.FormaDePagoLogic;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
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
 * @author Arturo Rubio
 */
@RunWith(Arquillian.class)
public class ClienteFormaPagoLogicTest 
{
    private PodamFactory fabrica = new PodamFactoryImpl();
    
    @Inject
    private FormaDePagoLogic fl;
    
    @Inject
    private ClienteFormaPagoLogic cfl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ClienteEntity> data = new ArrayList<>();
    
    private List<FormaDePagoEntity> formasPago = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    /**
     * Limpia las tablas que estan en la prueba
     */
    private void clearData(){
        em.createQuery("delete from FormaDePagoEntity").executeUpdate();
        em.createQuery("delete from FormaDePagoEntity").executeUpdate();
    }
    
    /**
     * Inserta datos iniciales para las pruebas
     */
    private void insertData(){
        for(int i = 0; i < 3; ++i){
            FormaDePagoEntity c = fabrica.manufacturePojo(FormaDePagoEntity.class);
            c.setCcv(i);
            em.persist(c);
            formasPago.add(c);
        }
        for(int i = 0 ; i < 3 ; ++ i){
            ClienteEntity c = fabrica.manufacturePojo(ClienteEntity.class);
            em.persist(c);
            data.add(c);
            if(i == 0)
                formasPago.get(i).setCliente(c);
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
    public void addFormaPagoTest(){
        ClienteEntity c = data.get(0);
        FormaDePagoEntity fPago = formasPago.get(1);
        fPago.setCliente(c);
        FormaDePagoEntity resp = cfl.addFormaPago(fPago.getId(), fPago.getCliente().getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getCliente(), fPago.getCliente());
    }
    
    /**
     * Prueba para obtener toda la lista de comentarios de un corto
     */
    @Test
    public void getFormasPagoTest(){
        List<FormaDePagoEntity> lista = cfl.getFormasPago(data.get(0).getId());
        
        Assert.assertEquals(1,lista.size());
    }
    /**
     * Prueba para obtener comentario existente
     * @throws BusinessLogicException 
     */
    @Test
    public void getFormaPagoTest() throws BusinessLogicException{
        ClienteEntity c = data.get(0);
        FormaDePagoEntity fPago = formasPago.get(0);
        FormaDePagoEntity resp = cfl.getFormaPago(c.getId(),fPago.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getCliente(), fPago.getCliente());
        Assert.assertEquals(resp.getId(), fPago.getId());
    }
    
    /**
     * Prueba para recibir la excepcion por no existir comentario en la lista
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getFormaPagoNoExisteTest()throws BusinessLogicException{
        ClienteEntity c = data.get(0);
        FormaDePagoEntity fPago = formasPago.get(1);
        FormaDePagoEntity resp = cfl.getFormaPago(c.getId(),fPago.getId());
    }
}
