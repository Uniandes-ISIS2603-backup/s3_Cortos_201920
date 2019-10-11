/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
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
public class TemaCineastaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(TemaCineastaLogic.class.getName());
    @Inject
    private TemaPersistence temaPersistence; 
    @Inject
    private CineastaPersistence cineastaPersistence;  
    
    public CineastaEntity addCineasta (Long temaId, Long cineastaId)
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de asociacion del tema con id ={0} con "
                + "el cineasta con id = "+ cineastaId, temaId  );
        
        CineastaEntity cineasta = cineastaPersistence.find(cineastaId);
        TemaEntity tema = temaPersistence.find(temaId);
        tema.getCineasta().add(cineasta);
        LOGGER.log(Level.INFO,"Se termina el proceso de asociacion");
        return cineastaPersistence.find(cineastaId);
        
    }
    
    public List<CineastaEntity> getCineasta(Long temaId)
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de consulta de los cineastas"
                + "asociados con el tema con id={0}", temaId);
        List<CineastaEntity> cineastas = temaPersistence.find(temaId).getCineasta();
        LOGGER.log(Level.INFO,"Se termina el proceso de consulta");
        return cineastas;
    }
}
