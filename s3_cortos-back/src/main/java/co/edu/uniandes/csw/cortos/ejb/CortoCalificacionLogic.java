/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoCalificacionLogic {
    private CortoPersistence cp;
    private CalificacionPersistence comentP;
    
    public CalificacionEntity addCalificacion(long comId, long cortoId){
        CalificacionEntity agregar = comentP.find(comId);
        CortoEntity agregue = cp.find(cortoId);
        agregar.setCorto(agregue);
        return agregar;
    }
    
    public List<CalificacionEntity> getCalificaciones(long cortoId){
        return cp.find(cortoId).getCalificaciones();
    }
    public CalificacionEntity getCalificacion(long comentId, long cortoId) throws BusinessLogicException{
        List<CalificacionEntity> comentarios =getCalificaciones(cortoId);
        CalificacionEntity comentario = comentP.find(comentId);
        int index = comentarios.indexOf(comentario);
        if(index >= 0){
            return comentario;
        }
        throw new BusinessLogicException("No se encuentra el comentario en este corto");
    }
}
