/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Luis Callejas Pinzon
 */
@Stateless
public class CineastaPersistence {
    
    @PersistenceContext(unitName = "cortosPU")
    protected EntityManager em;
            
    public CineastaEntity create (CineastaEntity cineastaEntity)
    {
        em.persist(cineastaEntity);
        return cineastaEntity;
    }
    
    public CineastaEntity find (long cineastaId)
    {
       return em.find(CineastaEntity.class,cineastaId);
    }
    
    public List<CineastaEntity> findAll()
    {
        TypedQuery<CineastaEntity> query = em.createQuery("select u from CineastaEntity u",CineastaEntity.class);
        return query.getResultList();
    }
    
    public CineastaEntity update (CineastaEntity cineastaEntity)
    {
        return em.merge(cineastaEntity);
    }
    
    public void delete (long cineastaId)
    {
        CineastaEntity cineastaEntity = em.find(CineastaEntity.class,cineastaId);
        em.remove(cineastaEntity);
    }
}