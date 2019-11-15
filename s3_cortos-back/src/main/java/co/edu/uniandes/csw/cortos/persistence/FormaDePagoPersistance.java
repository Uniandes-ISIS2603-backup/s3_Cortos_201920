/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan Felipe Mejia Parra
 */
@Stateless
public class FormaDePagoPersistance {
    
    @PersistenceContext(unitName = "cortosPU")
    protected EntityManager em;
    
    public FormaDePagoEntity create(FormaDePagoEntity formaDePago)
    {
        em.persist(formaDePago);
        
        return formaDePago;
    }
    
    public List<FormaDePagoEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from FormaDePagoEntity u", FormaDePagoEntity.class);
        return query.getResultList();
    }
    
    public FormaDePagoEntity find (Long formaDePagoId)
    {
        return em.find(FormaDePagoEntity.class, formaDePagoId);
    }
    
    public FormaDePagoEntity update(FormaDePagoEntity pFormaEntity)
    {
        return em.merge(pFormaEntity);
    }
    
    public void delete(Long id)
    {
        FormaDePagoEntity borrar = em.find(FormaDePagoEntity.class, id);
        em.remove(borrar);
    }
    
}
