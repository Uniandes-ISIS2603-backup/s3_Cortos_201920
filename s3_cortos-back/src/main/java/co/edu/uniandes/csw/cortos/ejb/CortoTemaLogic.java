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

/**
 *
 * @author Juan Sebastian Gomez
 */
public class CortoTemaLogic {
    private CortoPersistence cp;
    private TemaPersistence comentP;
    
    public TemaEntity addTema(long comId, long cortoId){
        TemaEntity agregar = comentP.find(comId);
        CortoEntity agregue = cp.find(cortoId);
        agregue.getTemas().add(agregar);
        return agregar;
    }
    
    public List<TemaEntity> getTemas(long cortoId){
        return cp.find(cortoId).getTemas();
    }
    public TemaEntity getTema(long comentId, long cortoId) throws BusinessLogicException{
        List<TemaEntity> comentarios =getTemas(cortoId);
        TemaEntity comentario = comentP.find(comentId);
        int index = comentarios.indexOf(comentario);
        if(index >= 0){
            return comentario;
        }
        throw new BusinessLogicException("No se encuentra el tema en este corto");
    }
}
