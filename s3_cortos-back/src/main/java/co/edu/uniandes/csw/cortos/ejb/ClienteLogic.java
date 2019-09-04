/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Arturo Rubio
 */
@Stateless
public class ClienteLogic 
{
    @Inject
    private ClientePersistence persistence;
    
    public ClienteEntity createCliente(ClienteEntity cliente) throws BusinessLogicException
    {
        if(cliente.getNombre() == null)
        {
            throw new BusinessLogicException("El nombre no puede ser nulo");
        }
        cliente = persistence.create(cliente);
        return cliente;
    }
    
}
