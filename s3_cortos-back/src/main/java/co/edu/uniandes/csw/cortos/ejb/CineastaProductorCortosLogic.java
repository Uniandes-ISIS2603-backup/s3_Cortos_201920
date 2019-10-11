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
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pedro Callejas
 */
@Stateless
public class CineastaProductorCortosLogic {

    @Inject
    private CortoPersistence cortoPersistence;

    @Inject
    private CineastaPersistence cineastaPersistence;

    /**
     * Asocia un Corto existente a un Cineasta
     *
     * @param cineastaId Identificador de la instancia de Cineasta
     * @param cortoId Identificador de la instancia de Corto
     * @return Instancia de cortoEntity que fue asociada a Cineasta
     */
    public CortoEntity addCorto(Long cineastaId, Long cortoId) {
      
        CineastaEntity authorEntity = cineastaPersistence.find(cineastaId);
        CortoEntity cortoEntity = cortoPersistence.find(cortoId);
        cortoEntity.getCineasta().add(authorEntity);
      
        return cortoPersistence.find(cortoId);
    }

    /**
     * Obtiene una colección de instancias de CortoEntity asociadas a una
     * instancia de Cineasta
     *
     * @param authorsId Identificador de la instancia de Cineasta
     * @return Colección de instancias de CortoEntity asociadas a la instancia
     * de Cineasta
     */
    public List<CortoEntity> getCortos(Long authorsId) {
      
        return cineastaPersistence.find(authorsId).getCortos();
    }

    /**
     * Obtiene una instancia de CortoEntity asociada a una instancia de Cineasta
     *
     * @param authorsId Identificador de la instancia de Cineasta
     * @param cortoId Identificador de la instancia de Corto
     * @return La entidadd de Corto del cineasta
     * @throws BusinessLogicException Si el corto no está asociado al cineasta
     */
    public CortoEntity getCorto(Long authorsId, Long cortoId) throws BusinessLogicException {
       
        List<CortoEntity> cortos = cineastaPersistence.find(authorsId).getCortos();
        CortoEntity cortoEntity = cortoPersistence.find(cortoId);
        int index = cortos.indexOf(cortoEntity);
      
        if (index >= 0) {
            return cortos.get(index);
        }
        throw new BusinessLogicException("El corto no está asociado al cineasta");
    }

    /**
     * Remplaza las instancias de Corto asociadas a una instancia de Cineasta
     *
     * @param authorId Identificador de la instancia de Cineasta
     * @param cortos Colección de instancias de CortoEntity a asociar a
     * instancia de Cineasta
     * @return Nueva colección de CortoEntity asociada a la instancia de
     * Cineasta
     */
    public List<CortoEntity> replaceCortos(Long authorId, List<CortoEntity> cortos) {
      
        CineastaEntity authorEntity = cineastaPersistence.find(authorId);
        List<CortoEntity> cortoList = cortoPersistence.findAll();
        for (CortoEntity tema : cortoList) {
            if (cortos.contains(tema)) {
                if (!tema.getCineasta().contains(authorEntity)) {
                    tema.getCineasta().add(authorEntity);
                }
            } else {
                tema.getCineasta().remove(authorEntity);
            }
        }
        authorEntity.setCortos(cortos);
      
        return authorEntity.getCortos();
    }

    /**
     * Desasocia un Corto existente de un Cineasta existente
     *
     * @param authorsId Identificador de la instancia de Cineasta
     * @param cortoId Identificador de la instancia de Corto
     */
    public void removeCortos(Long authorsId, Long cortoId) {
       
        CineastaEntity authorEntity = cineastaPersistence.find(authorsId);
        CortoEntity cortoEntity = cortoPersistence.find(cortoId);
        cortoEntity.getCineasta().remove(authorEntity);
      
    }
}
