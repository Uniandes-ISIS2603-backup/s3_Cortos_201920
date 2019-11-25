/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.test.logic;

import co.edu.uniandes.csw.cortos.ejb.FacturaClienteLogic;
import co.edu.uniandes.csw.cortos.ejb.FacturaLogic;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
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
 * @author Ingrith Barbosa
 */
@RunWith(Arquillian.class)
public class FacturaClienteLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ClientePersistence clienteP;
    
    @Inject
    private FacturaClienteLogic fcl;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<FacturaEntity> data = new ArrayList<>();
    
    private List<ClienteEntity> clientes = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
     @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    /**
     * Limpia datos antes de un test
     */
    private void clearData(){
        em.createQuery("delete from ClienteEntity").executeUpdate();
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }
    /**
     * aniade datos antes de un test
     */
    private void insertData(){
        for(int i = 0; i < 3; i++){
            FacturaEntity f = factory.manufacturePojo(FacturaEntity.class);
            em.persist(f);
            data.add(f);
        }
        for(int i = 0; i < 3; i++){
            ClienteEntity c = factory.manufacturePojo(ClienteEntity.class);
            em.persist(c);
            clientes.add(c);
        }
    }
    /**
     * configuracion de test
     */
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
    @Test
    public void addClienteTest()
    {
        FacturaEntity factura = data.get(0);
        ClienteEntity cliente = clientes.get(1);
        ClienteEntity  resp =fcl.addCliente(factura.getId(),cliente.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(resp.getId(), cliente.getId());
        Assert.assertEquals(resp.getCorreo(),cliente.getCorreo());
        Assert.assertEquals(resp.getNombre(),cliente.getNombre());
        Assert.assertEquals(resp.getContrasenia(),cliente.getContrasenia());
    }
    @Test
    public void getClienteTest()
    {
        ClienteEntity cliente= clientes.get(0);
        ClienteEntity newCliente= clienteP.find(cliente.getId());
        Assert.assertNotNull(newCliente);
        Assert.assertEquals(newCliente.getId(), cliente.getId());
        Assert.assertEquals(newCliente.getCorreo(),cliente.getCorreo());
        Assert.assertEquals(newCliente.getNombre(),cliente.getNombre());
        Assert.assertEquals(newCliente.getContrasenia(),cliente.getContrasenia()); 
    }
}
