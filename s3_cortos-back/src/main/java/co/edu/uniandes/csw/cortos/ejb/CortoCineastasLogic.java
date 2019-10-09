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
    /**
     * Referencia a la persistencia del corto
     */
    @Inject
    private CortoPersistence cp;
    /**
     * Referencia a la persistencia de los cineastas
     */
    @Inject
    private CineastaPersistence cineP;
    /**
     * Agrega un cineasta a la lista de cineastas del corto
     * @param cortoId identificador del corto
     * @param cineastaId identificador del cineasta a agregar
     * @return cineasta agregado
     */
    public CineastaEntity addCineasta(Long cortoId, Long cineastaId){
        CineastaEntity agregar = cineP.find(cineastaId);
        CortoEntity agreguemeCosas = cp.find(cortoId);
        agreguemeCosas.getCineasta().add(agregar);
        return agregar;
    }
    /**
     * Lista de cineastas de un corto
     * @param cortoId identificacion del corto
     * @return lista de cineastas de un corto
     */
    public List<CineastaEntity> getCineastas(Long cortoId){
        return cp.find(cortoId).getCineasta();
    }
    /**
     * Retorna cineasta especifico de un corto
     * @param cortoId identifiacion del corto
     * @param cineastaId identificacion del cineasta
     * @return el cineasta con la identificacion recibida por parametro
     */
    public CineastaEntity getCineasta(Long cortoId, Long cineastaId){
        List<CineastaEntity> lista = getCineastas(cortoId);
        CineastaEntity estara = cineP.find(cineastaId);
        int index = lista.indexOf(estara);
        if(index >= 0){
            return lista.get(index);
        }
        return null;
    }
    /**
     * Remueve un cineasta especifico de la lista de cineastas de un corto
     * @param cortoId identificacion del corto
     * @param cineastaId identificacion del cineasta a remover
     */
    public void removeCineasta(Long cortoId, Long cineastaId){
        CineastaEntity elimineme = cineP.find(cineastaId);
        CortoEntity saquemelo = cp.find(cortoId);
        saquemelo.getCineasta().remove(elimineme);
    }
}
