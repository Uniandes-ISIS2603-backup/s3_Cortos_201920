/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Vargas Vega
 */
    @Stateless
    public class CalificacionLogic {
         private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence persistence;

    public CalificacionEntity createCalificacion(CalificacionEntity calificacionEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Iniciando proceso de creacion de calificacion");
        if(calificacionEntity.getId()==null)
        {
            throw new BusinessLogicException("La calificacion es invalida");
        }
        else if(calificacionEntity.getPuntaje()<0)
        {
            throw new BusinessLogicException("La calificacion es invalida");
        }
        else if( calificacionEntity.getPuntaje()>5)
        {
            throw new BusinessLogicException("La calificacion es invalida");
        }
        persistence.create(calificacionEntity);
        LOGGER.log(Level.INFO,"Se termino la creacion de una calificacion");
        return calificacionEntity;
    }
    
    public List<CalificacionEntity > getCalificaciones()
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los comentarios");
        List<CalificacionEntity> calificaciones = persistence.findAll();
        LOGGER.log(Level.INFO,"Termina proceso de consultar todos los comentarios");
        return calificaciones;
    }
    
    public CalificacionEntity getCalificacion(Long id)
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de consultar la calificacion con id = {0}", id);
        CalificacionEntity calificacionEntity= persistence.find(id);
        if(calificacionEntity==null || id == null)
        {
            LOGGER.log(Level.INFO,"El comentario con id ={0} no existe",id);
        }
        LOGGER.log(Level.INFO,"Termina el proceso con id={0}",id);
        return calificacionEntity;
    }
    public CalificacionEntity updateCalificacion (Long id, CalificacionEntity calificacionEntity)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia proceso de acutalizar el calificacion con id ={0}",id);
        if(calificacionEntity.getId()==null)
        {
            throw new BusinessLogicException("La calificacion es invalida");
        }
        else if(calificacionEntity.getPuntaje()<0)
        {
            throw new BusinessLogicException("La calificacion es invalida");
        }
        else if( calificacionEntity.getPuntaje()>5)
        {
            throw new BusinessLogicException("La calificacion es invalida");
        }
        CalificacionEntity newEntity = persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO,"Termina proceso de actualizar el calificacion con id={0}", id);
        return newEntity;
    }
    public void deleteCalificacion(Long id ) 
    {
        LOGGER.log(Level.INFO,"Inicia proceso de borrar la calificacion con id = {0}", id);
        
        persistence.delete(id);
        LOGGER.log(Level.INFO,"Termina el proceso de borrar la calificacion con id ={0} ", id );
    }

}
