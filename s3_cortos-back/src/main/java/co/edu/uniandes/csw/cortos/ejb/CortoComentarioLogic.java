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

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoComentarioLogic {
    private CortoPersistence cp;
    private ComentarioPersistence comentP;
    
    public ComentarioEntity addComentario(long comId, long cortoId){
        ComentarioEntity agregar = comentP.find(comId);
        CortoEntity agregue = cp.find(cortoId);
        agregar.setCorto(agregue);
        return agregar;
    }
    
    public List<ComentarioEntity> getComentarios(long cortoId){
        return cp.find(cortoId).getComentarios();
    }
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
