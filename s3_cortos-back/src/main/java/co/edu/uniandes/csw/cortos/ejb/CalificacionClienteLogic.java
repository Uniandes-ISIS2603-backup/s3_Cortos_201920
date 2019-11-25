/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Vargas Vega
 */
@Stateless
public class CalificacionClienteLogic {
    private static final  Logger LOGGER = Logger.getLogger(CalificacionClienteLogic.class.getName());
    @Inject 
    private CalificacionPersistence calificacionPersistence;
    @Inject
    private ClientePersistence clientePersistence; 
    
    public ClienteEntity addCliente(Long calificacionId, Long clienteId)
    {
        LOGGER.log(Level.INFO, "Se inicia proceso de asosiacion de la  calificacion con id ={0} con el cliente con id ={1} ", new Object[]{clienteId,calificacionId} );
        ClienteEntity cliente = clientePersistence.find(clienteId);
        CalificacionEntity calificacion = calificacionPersistence.find(calificacionId);
        calificacion.setCliente(cliente);
        LOGGER.log(Level.INFO, "Se termina proceso de asociacion");
        return clientePersistence.find(clienteId);
        
    }
    
    public ClienteEntity getCliente(Long calificacionId)
    {
        LOGGER.log(Level.INFO, "Se inicia proceso de consulta de cliente con la calificacion con id ={0}", calificacionId);
        ClienteEntity cliente= calificacionPersistence.find(calificacionId).getCliente();
        LOGGER.log(Level.INFO, "Se termina el proceso de consulta ");
        return cliente;
    }
    
}
