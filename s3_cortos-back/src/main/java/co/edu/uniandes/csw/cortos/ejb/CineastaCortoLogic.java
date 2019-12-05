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
 * @author Pedro Luis Callejas
 */
@Stateless
public class CineastaCortoLogic {
    @Inject
    CortoPersistence cortoPersistence;
    
    @Inject
    CineastaPersistence cineastaPersistence;
    
    
    public CortoEntity addCorto(Long cineastaId, Long cortoId){
        CineastaEntity addTo = cineastaPersistence.find(cineastaId);
        CortoEntity add = cortoPersistence.find(cortoId);
        
        add.getCineasta().add(addTo);
        return add;
    }
    
    public List<CortoEntity> getCortos(Long cineastaId){
        return cineastaPersistence.find(cineastaId).getCortoCineastas();
    }
}
