/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Inrith Barbosa
 */
@Stateless
public class FacturaClienteLogic 
{
    /**
     * Referencia a la persistencia del cliente
     */
    @Inject
    private ClientePersistence clienteP;
    /**
     * Referencia a la persistencia de la factura
     */
    @Inject
    private FacturaPersistence facturaP;
    
    /**
     * Agrega un cliente a la factura
     * @param facturaId identificador de la factura
     * @param clienteId identificador del cliente a agregar
     * @return corto agregado
     */
    public ClienteEntity addCliente(Long facturaId, Long clienteId){
        ClienteEntity agregar = clienteP.find(clienteId);
        FacturaEntity agreguemeCosas = facturaP.find(facturaId);
        agreguemeCosas.setCliente(agregar);
        return agregar;
    }
    /**
     * Devuelve el cliente asociado a la factura 
     * @param facturaId identificador de la factura
     * @return cliente asociado a la factura
     */
    public ClienteEntity getCliente(Long facturaId)
    {
        FacturaEntity factura = facturaP.find(facturaId);
        return factura.getCliente();
    }
    
}
