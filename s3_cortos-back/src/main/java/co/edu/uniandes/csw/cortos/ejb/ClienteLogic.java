/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Arturo Rubio
 */
@Stateless
public class ClienteLogic 
{
    private final static Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    
    @Inject
    private ClientePersistence persistence;
    
    public ClienteEntity createCliente(ClienteEntity cliente) throws BusinessLogicException
    {
         if (persistence.findByName(cliente.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el nombre \"" + cliente.getNombre() + "\"");
        }
        if (persistence.findByCorreo(cliente.getCorreo()) != null) {
            throw new BusinessLogicException("Ya existe un cliente con el correo \"" + cliente.getCorreo() + "\"");
        }
        if (cliente.getNombre() == null || cliente.getNombre().equals("")) {
            throw new BusinessLogicException("El nombre no puede ser null ni vacio \"");
        }
        if (cliente.getContrasenia() == null || cliente.getContrasenia().equals("")) {
            throw new BusinessLogicException("La contraseña no puede ser null ni vacio \"");
        }
        if (cliente.getCorreo() == null || cliente.getCorreo().equals("")) {
            throw new BusinessLogicException("El correo no puede ser null ni vacio \"");
        }
        if (persistence.findByCorreo(cliente.getCorreo())!= null) {
            throw new BusinessLogicException("El correo ya existe \"");
        }
        cliente = persistence.create(cliente);
        return cliente;
    }
    
    public List<ClienteEntity> getClientes()
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los clientes");
        List<ClienteEntity> clientes = persistence.findAll();
        LOGGER.log(Level.INFO,"Termina proceso de consultar todos los clientes");
        return clientes;
    }
    
    public ClienteEntity getCliente(Long id )
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de consultar el cliente con id = {0}",id);
        ClienteEntity clienteEntity= persistence.find(id);
        if(clienteEntity==null || id == null)
        {
            LOGGER.log(Level.INFO,"El cliente con id ={0} no existe",id);
        }
        LOGGER.log(Level.INFO,"Termina el proceso de consultar el cliente con id = {0}", id );
        return clienteEntity;
    }
    
    public ClienteEntity updateCliente (Long id ,ClienteEntity cliente) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia   proceso de actualizar el cliente con id = {0}",id);
        ClienteEntity newEntity = persistence.update(cliente);
        LOGGER.log(Level.INFO,"Termina proceso de actaulizar el cliente con Id={0}", id);
        return newEntity;
    }
    
    public void deleteCliente(Long id ) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", id );
        persistence.delete(id);
        LOGGER.log(Level.INFO,"Termine el proceso de borrar el cliente con id= {0}",id);
    }
    
}
