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
    @Inject
    private CortoPersistence cp;
    @Inject
    private CineastaPersistence cineP;
    
    public CineastaEntity replaceProductor(Long cortoId, Long CineastaId){
        CineastaEntity agregueme = cineP.find(CineastaId);
        CortoEntity metalo = cp.find(cortoId);
        metalo.setProductor(agregueme);
        return agregueme;
    }
    
    public void removeProductor(Long cineastaId, Long cortoId){
        CortoEntity quitemelo = cp.find(cortoId);
        CineastaEntity puesMeVoy = cineP.find(cineastaId);
        quitemelo.setProductor(null);
        puesMeVoy.getCortos().remove(quitemelo);
    }
}
