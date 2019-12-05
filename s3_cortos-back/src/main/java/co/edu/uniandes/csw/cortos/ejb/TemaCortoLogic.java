/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import co.edu.uniandes.csw.cortos.persistence.TemaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Vargas Vega 
 */
@Stateless
public class TemaCortoLogic {
       private static final Logger LOGGER = Logger.getLogger(TemaCineastaLogic.class.getName());
    @Inject
    private TemaPersistence temaPersistence; 
    @Inject
    private CortoPersistence cortoPersistence;  
    
    public CortoEntity addCorto (Long temaId, Long cortoId)
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de asociacion del tema con id ={0} con "
                + "el corto con id = "+ cortoId, temaId  );
        
        CortoEntity corto = cortoPersistence.find(cortoId);
        TemaEntity tema = temaPersistence.find(temaId);
        tema.getCortos().add(corto);
        LOGGER.log(Level.INFO,"Se termina el proceso de asociacion");
        return cortoPersistence.find(cortoId);
        
    }
    
public List<CortoEntity> getCorto(Long temaId)
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de consulta de los cineastas"
                + "asociados con el tema con id={0}", temaId);
        List<CortoEntity> cortos = temaPersistence.find(temaId).getCortos();
        LOGGER.log(Level.INFO,"Se termina el proceso de consulta");
        return cortos;
    }
}
