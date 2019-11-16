/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago  Vargas  Vega
 */
@Stateless
public class ComentarioClienteLogic {
    private static final Logger LOGGER = Logger.getLogger(ComentarioClienteLogic.class.getName());
    @Inject 
    private ComentarioPersistence comentarioPersistence;
    @Inject 
    private ClientePersistence clientePersistence; 
    
    public ClienteEntity addCliente(Long clienteId, Long comentarioId )
    {
        LOGGER.log(Level.INFO,"Inicia proceso de asociar el comentario con id= {0} al cliente con id {1} ",new Object[]{ comentarioId, clienteId } );
        ClienteEntity cliente = clientePersistence.find(clienteId);
        ComentarioEntity comentario = comentarioPersistence.find(comentarioId);
        comentario.setCliente(cliente);
        LOGGER.log(Level.INFO, "Termina proceso de asociar un cliente con un comentario");
        return clientePersistence.find(clienteId);
    }
    
    public ClienteEntity getCliente(Long comentarioId)
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de  consulta el cliente del comentario con id ={0}", comentarioId);
        ClienteEntity clienteEntity = comentarioPersistence.find(comentarioId).getCliente();
        LOGGER.log(Level.INFO,"Termina el proceso de consulta");
        return clienteEntity;
    }
    
}
