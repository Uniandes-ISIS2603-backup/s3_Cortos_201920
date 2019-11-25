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
    
    public ClienteEntity findByName(String name) {
        TypedQuery<ClienteEntity> query = em.createQuery("select e from ClienteEntity e where e.nombre = :name ", ClienteEntity.class);
        query = query.setParameter("name", name);

        List<ClienteEntity> sameName = query.getResultList();
        ClienteEntity result;

        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }

        return result;
    }
    
    public List<ClienteEntity> findByNameLike(String name) {
        String q = "'%"+ name +"%'";
        TypedQuery<ClienteEntity> query = em.createQuery("select e from ClienteEntity e where e.nombre like " + q, ClienteEntity.class);

        List<ClienteEntity> sameName = query.getResultList();

        return sameName;
    }
    
    public ClienteEntity findByCorreo(String correo) {
        TypedQuery<ClienteEntity> query = em.createQuery("select e from ClienteEntity e where e.correo = :correo ", ClienteEntity.class);
        query = query.setParameter("correo", correo);

        List<ClienteEntity> encontrado = query.getResultList();
        ClienteEntity result;

        if (encontrado == null) {
            result = null;
        } else if (encontrado.isEmpty()) {
            result = null;
        } else {
            result = encontrado.get(0);
        }

        return result;
    }
}
