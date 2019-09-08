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
        ArrayList<String> palabras = new ArrayList<String>();
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
    
    public void deleteComent(Long id) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de eliminacion del comentario con id {0}",id);
        persistence.delete(id);     
    }
    
    
}


