/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.TemaPersistence;
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
public class TemaLogic {
    
    private static final  Logger LOGGER  = Logger.getLogger(TemaLogic.class.getName());
    
    @Inject 
    private TemaPersistence persistence;
    
    public TemaEntity createTema( TemaEntity temaEntity ) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Iniciando proceso de creacion de un tema");
        if (temaEntity.getId()==null)
        {
            throw new BusinessLogicException("El tema es invalido");
        }
        
        if(temaEntity.getNombre().equals(""))
        {
            throw new BusinessLogicException("El nombre del tema es invalido  ");
        }
        
        persistence.create (temaEntity);
        LOGGER.log(Level.INFO,"Se termino la creacion de un tema");
        return temaEntity;
    }
    
    public List<TemaEntity> getTemas()
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de consultar todos los temas");
        List<TemaEntity> temas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los comentarios");
        return temas;
    }
    
    public TemaEntity getTema(Long id)
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar el tema con ud ={0}",id);
        TemaEntity temaEntity = persistence.find(id);
        if (temaEntity ==null)
        {
            LOGGER.log(Level.INFO," El comentario con id = {0} no existe", id );
        }
        LOGGER.log(Level.INFO, "Termina el proceso de consultar el tema ");
        return temaEntity;
    }
    
    public TemaEntity updateTema (Long id , TemaEntity tema) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualzar el tema con id = {0}",id);
        TemaEntity newEntity = persistence.update(tema);
        LOGGER.log(Level.INFO, "Termina el proceso de actualizar el tema con id = {0}", id);
        return newEntity;
    }
    
    public void deleteTema( Long id)
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de borrar la calificacion con id  = {0}", id);
        persistence.delete (id);
        LOGGER.log (Level.INFO, "Termina el proceso de borrar la calificacion con id = {0}", id);
    }
    
    
    
}
