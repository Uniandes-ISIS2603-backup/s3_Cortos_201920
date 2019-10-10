/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santiago Vargas
 */
@Stateless
public class CalificacionPersistence {
     private static final Logger LOGGER  = Logger.getLogger(CalificacionPersistence.class.getName());
     @PersistenceContext (unitName= "cortosPU")
    protected EntityManager em;
     
     /**
      * Crear una calificacion
      * @param calificacion
      * @return 
      */
    public CalificacionEntity create( CalificacionEntity calificacion){
       LOGGER.log(Level.INFO,"Creando un calificacion nuevo");
        em.persist(calificacion);
        LOGGER.log(Level.INFO,"calificacion creado");
        return calificacion;
    }
    /**
     * Busca  todas las calificaciones en la base de datos
     * @return 
     */
    public List<CalificacionEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todas las calificacion");
        TypedQuery q = em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
        return q.getResultList();
    }
    /**
     * Busca una calificacion dado su Id
     * @param rateId
     * @return 
     */
     public CalificacionEntity find(Long rateId)
    {
       LOGGER.log(Level.INFO, "Consultando el calificacion con Id{0}",rateId);
        return em.find(CalificacionEntity.class,rateId);
    }
    /**
     * Actualiza una entidad 
     * @param rateEntity
     * @return 
     */
     public CalificacionEntity update(CalificacionEntity rateEntity)
    {
        LOGGER.log(Level.INFO,"Actualizando el calificacion con id {0}", rateEntity.getId());
        return em.merge(rateEntity);
    }
     /**
      * Elimina una entidad
      * @param rateId 
      */
     public void delete (Long rateId){
        LOGGER.log(Level.INFO, "Borrando el calificacion con Id{0}", rateId);
        CalificacionEntity comentEntity = em.find(CalificacionEntity.class,rateId);
        em.remove(comentEntity);
    }
   
}
