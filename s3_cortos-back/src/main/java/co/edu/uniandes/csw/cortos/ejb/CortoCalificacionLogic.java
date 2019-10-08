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
    /**
     * Referencia a la persistencia de cortos
     */
    private CortoPersistence cp;
    /**
     * Referencia a la persistencia de Calificacion
     */
    private CalificacionPersistence califP;
    /**
     * Aniade una calificacion a un corto especifico
     * @param caliId id de la calificacion
     * @param cortoId id del corto
     * @return calificacion aniadida
     */
    public CalificacionEntity addCalificacion(long caliId, long cortoId){
        CalificacionEntity agregar = califP.find(caliId);
        CortoEntity agregue = cp.find(cortoId);
        agregar.setCorto(agregue);
        return agregar;
    }
    /**
     * Retorna todas las calificaciones de un corto
     * @param cortoId id del corto
     * @return calificaciones de un corto
     */
    public List<CalificacionEntity> getCalificaciones(long cortoId){
        return cp.find(cortoId).getCalificaciones();
    }
    /**
     * Retorna la calificacion especifica de un corto especifico
     * @param calificacionId identificacion de la califiacion
     * @param cortoId id del corto
     * @return calificacion especifica del corto
     * @throws BusinessLogicException si la calificacion no se encuentra dentro de las calificaciones del corto
     */
    public CalificacionEntity getCalificacion(long calificacionId, long cortoId) throws BusinessLogicException{
        List<CalificacionEntity> comentarios =getCalificaciones(cortoId);
        CalificacionEntity comentario = califP.find(calificacionId);
        int index = comentarios.indexOf(comentario);
        if(index >= 0){
            return comentario;
        }
        throw new BusinessLogicException("No se encuentra el comentario en este corto");
    }
}
