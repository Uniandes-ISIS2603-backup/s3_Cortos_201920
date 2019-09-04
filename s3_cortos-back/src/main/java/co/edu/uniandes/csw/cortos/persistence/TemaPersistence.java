/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santiago  Vargas  Vega
 */
@Stateless
public class TemaPersistence {
    
    private static final Logger LOGGER  = Logger.getLogger(TemaPersistence.class.getName());

    @PersistenceContext(unitName= "cortosPU")
    protected EntityManager em;
    public  TemaEntity create(TemaEntity tema)
    {
        LOGGER.log(Level.INFO,"Creando un tema nuevo");
        em.persist(tema);
        LOGGER.log(Level.INFO,"tema creado");
        return tema;
    }
    public List<TemaEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los temas");
        TypedQuery q = em.createQuery("select u from TemaEntity u", TemaEntity.class);
        return q.getResultList();
    }
    
      public TemaEntity find(Long temaId)
    {
       LOGGER.log(Level.INFO, "Consultando el tema con Id{0}",temaId);
        return em.find(TemaEntity.class,temaId);
    }
       public TemaEntity update(TemaEntity temaEntity)
    {
        LOGGER.log(Level.INFO,"Actualizando el tema con id {0}", temaEntity.getId());
        return em.merge(temaEntity);
    }
       
    public void delete (Long temaId){
     LOGGER.log(Level.INFO, "Borrando el tema con Id{0}", temaId);
     TemaEntity temaEntity = em.find(TemaEntity.class,temaId);
        em.remove(temaEntity);
    }
    
}
