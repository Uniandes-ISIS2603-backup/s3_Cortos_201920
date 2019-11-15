/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import java.util.ArrayList;
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
public class ComentarioLogic {
    private final static Logger LOGGER = Logger.getLogger(ComentarioLogic.class.getName());
    
    @Inject
    private ComentarioPersistence persistence;
    
    public ComentarioEntity createComentario (ComentarioEntity comentarioEntity)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Iniciando proceso de creacion de comentario");
        if (comentarioEntity.getId()==null||comentarioEntity.getComentario()==null||comentarioEntity.getComentario().equals("")){
            throw new BusinessLogicException("El comentario es invalido");
        }
        if(validateComent(comentarioEntity))
            
        {
            throw new BusinessLogicException("El comentario contiene palabras ofensivas");
        }
        persistence.create(comentarioEntity);
        LOGGER.log(Level.INFO,"Se termina el proceso de creacion de comentario");
        return comentarioEntity;
    }
    private boolean validateComent(ComentarioEntity comentarioEntity)
    {
        ArrayList<String> palabras = new ArrayList<>();
        palabras.add("marica");
        palabras.add("chimba");
        palabras.add("webon");
        palabras.add("mierda");
        palabras.add("puta");
        palabras.add("cacorro");
        palabras.add("chimbo");
        for(String p : palabras){
            if (comentarioEntity.getComentario().contains(p))
            {
                return true;
            }
        }
        return false;
    }
        
    public List<ComentarioEntity> getComentarios()
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los comentarios");
        List<ComentarioEntity> comentarios = persistence.findAll();
        LOGGER.log(Level.INFO,"Termina proceso de consultar todos los comentarios");
        return comentarios;
    }
    
    public ComentarioEntity getComentario(Long id )
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de consultar el comentario con id = {0}",id);
    
        ComentarioEntity comentarioEntity= persistence.find(id);
        if(comentarioEntity==null || id == null)
        {
            LOGGER.log(Level.INFO,"El comentario con id ={0} no existe",id);
        }
        LOGGER.log(Level.INFO,"Termina el proceso de consultar el libro con id = {0}", id );
        return comentarioEntity;
    }
    
    public ComentarioEntity updateComentario (Long id ,ComentarioEntity comentario) 
    {
        LOGGER.log(Level.INFO,"Inicia   proceso de actualizar el comentario con id = {0}",id);
        ComentarioEntity newEntity = persistence.update(comentario);
        LOGGER.log(Level.INFO,"Termina proceso de actaulizar el comentario con Id={0}", id);
        return newEntity;
    }
    
    public void deleteComentario(Long id ) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el comentario con id = {0}", id );
        persistence.delete(id);
        LOGGER.log(Level.INFO,"Termine el proceso de borrar el comentario con id= {0}",id);
    }
}


