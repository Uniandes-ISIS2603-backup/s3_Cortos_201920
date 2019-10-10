/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Vargas Vega
 */
@Stateless
public class CalificacionCortoLogic {
    private static final  Logger LOGGER = Logger.getLogger(CalificacionCortoLogic.class.getName());
   @Inject
   private CortoPersistence cortoPersistence; 
  @Inject
  private CalificacionPersistence calificacionPersistence ; 
  
  public CortoEntity addCorto (Long cortoId, Long calificacionId)
  {
       LOGGER.log(Level.INFO, "Se inicia proceso de asosiacion de la  calificacion con id ={0} con el corto con id = "+ cortoId,calificacionId);
        CortoEntity corto = cortoPersistence.find(cortoId);
        CalificacionEntity calificacion = calificacionPersistence.find(calificacionId);
        calificacion.setCorto(corto);
        LOGGER.log(Level.INFO, "Se termina proceso de asociacion");
        return cortoPersistence.find(cortoId);
  }
  
  
  public CortoEntity getCorto (Long calificacionId)
  {
    LOGGER.log(Level.INFO, "Se inicia proceso de consulta de corto con la calificacion con id ={0}", calificacionId);
    CortoEntity corto = calificacionPersistence.find(calificacionId).getCorto();
    LOGGER.log(Level.INFO, "Se termina el proceso de consulta ");
    return corto; 
  }
  

}
