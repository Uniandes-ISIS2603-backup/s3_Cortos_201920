/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Arturo Rubio
 */
@Stateless
public class ClientePersistence 
{
    @PersistenceContext(unitName = "cortosPU")
    protected EntityManager em;
    
    public ClienteEntity create(ClienteEntity cliente)
    {
        em.persist(cliente);
        return cliente;
    }
}
