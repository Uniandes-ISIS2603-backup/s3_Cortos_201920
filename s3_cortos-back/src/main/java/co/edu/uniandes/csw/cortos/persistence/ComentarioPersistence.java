/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



/**
 *
 * @author Santiago Vargas Vega
 */
@Stateless
public class ComentarioPersistence {
    
    private static final Logger LOGGER  = Logger.getLogger(ComentarioPersistence.class.getName());
    @PersistenceContext (unitName= "cortosPU")
    protected EntityManager em;
    
    /**
     * Crear un nuevo comentaro en la base de Datos 
     * @param comentario
     * @return 
     */
    public ComentarioEntity create( ComentarioEntity comentario){
        LOGGER.log(Level.INFO,"Creando un comentario nuevo");
        em.persist(comentario);
        LOGGER.log(Level.INFO,"Comentario creado");
        return comentario;
    }
    /**
     * Busca todos los comentarios en la base de datos
     * @return 
     */
    public List<ComentarioEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los comentarios");
        TypedQuery q = em.createQuery("select u from ComentarioEntity u", ComentarioEntity.class);
        return q.getResultList();
    }
    
    /**
     * Busca un comentario dado su ID
     * @param comentId
     * @return 
     */
    public ComentarioEntity find(Long comentId)
    {
       LOGGER.log(Level.INFO, "Consultando el comentario con Id{0}",comentId);
        return em.find(ComentarioEntity.class,comentId);
    }
    /**
     * Actualuiza un comentario 
     * @param comentEntity
     * @return 
     */
    public ComentarioEntity update(ComentarioEntity comentEntity)
    {
        LOGGER.log(Level.INFO,"Actualizando el comentario con id {0}", comentEntity.getId());
        return em.merge(comentEntity);
    }
    /**
     * Elimina un comentario dado su id
     * @param comentId 
     */
    public void delete (Long comentId){
        LOGGER.log(Level.INFO, "Borrando el comentario con Id{0}", comentId);
        ComentarioEntity comentEntity = em.find(ComentarioEntity.class,comentId);
        em.remove(comentEntity);
    }
}
