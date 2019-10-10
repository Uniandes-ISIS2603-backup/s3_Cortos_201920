/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoCineastaProductorLogic {
    /**
     * Referencia a la persistencia del corto
     */
    @Inject
    private CortoPersistence cp;
    /**
     * Referencia a la persistencia del cineasta que sera el productor
     */
    @Inject
    private CineastaPersistence cineP;
    /**
     * Modifica al productor de un corto
     * @param cortoId identificacion del corto
     * @param CineastaId identificacion del cineasta con el que se modificara el productor
     * @return retorna el cineasta que sera el nuevo productor
     */
    public CineastaEntity replaceProductor(Long cortoId, Long CineastaId){
        CineastaEntity agregueme = cineP.find(CineastaId);
        CortoEntity metalo = cp.find(cortoId);
        metalo.setProductor(agregueme);
        return agregueme;
    }
    /**
     * quita al productor del corto
     * @param cineastaId quitar este cineasta de productor
     * @param cortoId  identificador del corto al que se le quitara el productor
     */
    public void removeProductor(Long cortoId){
        CortoEntity quitemelo = cp.find(cortoId);
        CineastaEntity puesMeVoy = cineP.find(quitemelo.getProductor().getId());
        quitemelo.setProductor(null);
        puesMeVoy.getCortos().remove(quitemelo);
    }
}
