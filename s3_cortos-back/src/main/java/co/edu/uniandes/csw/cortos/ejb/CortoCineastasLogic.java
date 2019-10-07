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
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoCineastasLogic {
    @Inject
    private CortoPersistence cp;
    @Inject
    private CineastaPersistence cineP;
    
    public CineastaEntity addCineasta(Long cortoId, Long cineastaId){
        CineastaEntity agregar = cineP.find(cineastaId);
        CortoEntity agreguemeCosas = cp.find(cortoId);
        agreguemeCosas.getCineasta().add(agregar);
        return agregar;
    }
    
    public List<CineastaEntity> getCineastas(Long cortoId){
        return cp.find(cortoId).getCineasta();
    }
    
    public CineastaEntity getCineasta(Long cortoId, Long cineastaId){
        List<CineastaEntity> lista = getCineastas(cortoId);
        CineastaEntity estara = cineP.find(cineastaId);
        int index = lista.indexOf(estara);
        if(index >= 0){
            return lista.get(index);
        }
        return null;
    }
    
    public void removeCineasta(Long cortoId, Long cineastaId){
        CineastaEntity elimineme = cineP.find(cineastaId);
        CortoEntity saquemelo = cp.find(cortoId);
        saquemelo.getCineasta().remove(elimineme);
    }
}
