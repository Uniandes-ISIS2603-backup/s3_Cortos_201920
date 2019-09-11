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
        cliente = persistence.create(cliente);
        return cliente;
    }
    
}
