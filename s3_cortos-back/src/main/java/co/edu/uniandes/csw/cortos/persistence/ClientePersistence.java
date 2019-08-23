/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import static java.util.Collections.list;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import static org.eclipse.persistence.jpa.jpql.utility.CollectionTools.list;

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
    
    public List<ClienteEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        return query.getResultList();
    }
    
    public ClienteEntity find (Long clienteId)
    {
        return em.find(ClienteEntity.class, clienteId);
    }
    
    public ClienteEntity update(ClienteEntity clienteEntity)
    {
        return em.merge(clienteEntity);
    }
    
    public void delete(Long clienteId)
    {
        ClienteEntity clienteEntity = em.find(ClienteEntity.class, clienteId);
        em.remove(clienteEntity);
    }
}
