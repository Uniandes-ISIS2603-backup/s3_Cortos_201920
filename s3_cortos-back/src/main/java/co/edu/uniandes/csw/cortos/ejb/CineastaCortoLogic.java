/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @corto Pedro Callejas
 */
@Stateless
public class CineastaCortoLogic {

    @Inject
    private CineastaPersistence cineastaPersistence;

    @Inject
    private CortoPersistence cortoPersistence;

    /**
     * Remplazar el corto de un cineasta.
     *
     * @param cineastaId id del cineasta que se quiere actualizar.
     * @param cortoId El id de la corto que se será del cineasta.
     * @return el nuevo cineasta.
     */
    public CineastaEntity replaceCorto(Long cineastaId, Long cortoId) {
       
        CortoEntity cortoEntity = cortoPersistence.find(cortoId);
        CineastaEntity cineastaEntity = cineastaPersistence.find(cineastaId);
        cineastaEntity.setCortoCineastas(cortoEntity);
       
        return cineastaEntity;
    }

    /**
     * Borrar un cineasta de un corto. Este metodo se utiliza para borrar la
     * relacion de un cineasta.
     *
     * @param cortoId El cineasta que se desea borrar del corto.
     */
    public void removeCorto(Long cortoId) {
        
        CineastaEntity cineastaEntity = cineastaPersistence.find(cortoId);
        CortoEntity cortoEntity = cortoPersistence.find(cineastaEntity.getCortoCineastas().getId());
        cineastaEntity.setCortoCineastas(null);
        cortoEntity.getCineasta().remove(cineastaEntity);
       
    }
}
