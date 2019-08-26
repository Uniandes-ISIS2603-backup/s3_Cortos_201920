/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Santiago  Vargas  Vega
 */
@Stateless
public class TemaPersistence {
    @PersistenceContext(unitName= "cortosPU")
    protected EntityManager em;
    public  TemaEntity create(TemaEntity tema)
    {
        em.persist(tema);
        return tema;
    }
    
}
