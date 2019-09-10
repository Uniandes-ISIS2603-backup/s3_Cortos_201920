/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
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
         private final static Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence persistence;

    public CalificacionEntity createCalificacion(CalificacionEntity calificacionEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Iniciando proceso de creacion de calificacion");
        if(calificacionEntity.getId()==null||calificacionEntity.getPuntaje()<0|| calificacionEntity.getPuntaje()>5)
        {
            throw new BusinessLogicException("La calificacion es invalida");
        }
        persistence.create(calificacionEntity);
        LOGGER.log(Level.INFO,"Se termino la creacion de una calificacion");
        return calificacionEntity;
    }
    
    public void deleteCalificacion (Long id )throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de eliminacion de una calificacion");
        persistence.delete (id);
    }
}
