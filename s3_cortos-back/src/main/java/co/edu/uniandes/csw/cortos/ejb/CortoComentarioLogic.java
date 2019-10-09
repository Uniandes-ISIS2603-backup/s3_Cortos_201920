/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoComentarioLogic {
    /**
     * Referencia a persistencia de corto
     */
    @Inject
    private CortoPersistence cp;
    /**
     * Referencia a persistencia de comentario
     */
    @Inject
    private ComentarioPersistence comentP;
    /**
     * Aniadir un comentario a un corto
     * @param comId identificacion del comentario
     * @param cortoId identificacion del corto
     * @return comentario aniadido
     */
    public ComentarioEntity addComentario(long comId, long cortoId){
        ComentarioEntity agregar = comentP.find(comId);
        CortoEntity agregue = cp.find(cortoId);
        agregar.setCorto(agregue);
        return agregar;
    }
    /**
     * Lista de comentarios de un corto
     * @param cortoId identificacion del corto
     * @return lista de comentarios de un corto especifico
     */
    public List<ComentarioEntity> getComentarios(long cortoId){
        return cp.find(cortoId).getComentarios();
    }
    /**
     * Comentario especifico de un corto
     * @param comentId identificacion de un comentario
     * @param cortoId identificacion de un corto
     * @return comentario especifico del corto
     * @throws BusinessLogicException el comentario no existe en la lista de comentarios 
     */
    public ComentarioEntity getComentario(long comentId, long cortoId) throws BusinessLogicException{
        List<ComentarioEntity> comentarios =getComentarios(cortoId);
        ComentarioEntity comentario = comentP.find(comentId);
        int index = comentarios.indexOf(comentario);
        if(index >= 0){
            return comentario;
        }
        throw new BusinessLogicException("No se encuentra el comentario en este corto");
    }
    
}
