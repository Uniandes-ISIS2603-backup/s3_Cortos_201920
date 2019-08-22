/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoPersistence {
    
    @PersistenceContext(unitName = "cortosPU")
    protected EntityManager em;
    public CortoEntity create(CortoEntity cortoEntity) {
        em.persist(cortoEntity);
        return cortoEntity;
    }
    
    public void delete(Long id){
        CortoEntity aBorrar = em.find(CortoEntity.class, id);
        em.remove(aBorrar);
    }
    
    
}
