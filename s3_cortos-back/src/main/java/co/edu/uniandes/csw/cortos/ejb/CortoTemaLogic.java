/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import co.edu.uniandes.csw.cortos.persistence.TemaPersistence;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastian Gomez
 */
public class CortoTemaLogic {
    /**
     * Referencia a la persistencia de corto
     */
    @Inject
    private CortoPersistence cp;
    /**
     * Referencia a la persistencia de tema
     */
    @Inject
    private TemaPersistence tp;
    /**
     * Aniade tema a la lista de temas de un corto
     * @param temaId identificacion del tema
     * @param cortoId identficacion del corto
     * @return tema aniadido
     */
    public TemaEntity addTema(long temaId, long cortoId){
        System.out.println(cortoId);
        TemaEntity agregar = tp.find(temaId);
        CortoEntity agregue = cp.find(cortoId);
        agregue.getTemas().add(agregar);
        return agregar;
    }
    /**
     * lista de temas de un corto
     * @param cortoId identificacion de corto
     * @return  lista de temas de un corto
     */
    public List<TemaEntity> getTemas(long cortoId){
        return cp.find(cortoId).getTemas();
    }
    /**
     * Retorna un tema de un corto
     * @param temaId identificacion de un tema
     * @param cortoId identificacion del corto
     * @return el tema especifico solicitado de un corto
     * @throws BusinessLogicException  El tema no pertenece al corto
     */
    public TemaEntity getTema(long temaId, long cortoId) throws BusinessLogicException{
        List<TemaEntity> comentarios =getTemas(cortoId);
        TemaEntity comentario = tp.find(temaId);
        int index = comentarios.indexOf(comentario);
        if(index >= 0){
            return comentario;
        }
        throw new BusinessLogicException("No se encuentra el tema en este corto");
    }
}
