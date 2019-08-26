/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



/**
 *
 * @author Santiago Vargas Vega
 */
@Stateless
public class ComentarioPersistence {
    @PersistenceContext (unitName= "cortosPU")
    protected EntityManager em;
    public ComentarioEntity create( ComentarioEntity comentario){
        em.persist(comentario);
        return comentario;
    }
}
