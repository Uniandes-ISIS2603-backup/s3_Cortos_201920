/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import co.edu.uniandes.csw.cortos.persistence.TemaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pedro Callejas
 */
@Stateless
public class CineastaTemasLogic {

    @Inject
    private TemaPersistence temaPersistence;

    @Inject
    private CineastaPersistence cineastaPersistence;

    /**
     * Asocia un Tema existente a un Cineasta
     *
     * @param cineastaId Identificador de la instancia de Cineasta
     * @param temaId Identificador de la instancia de Tema
     * @return Instancia de temaEntity que fue asociada a Cineasta
     */
    public TemaEntity addTema( Long temaId,Long cineastaId) {
   
        CineastaEntity authorEntity = cineastaPersistence.find(cineastaId);
        TemaEntity temaEntity = temaPersistence.find(temaId);
        temaEntity.getCineasta().add(authorEntity);
     
        return temaPersistence.find(temaId);
    }

    /**
     * Obtiene una colección de instancias de TemaEntity asociadas a una
     * instancia de Cineasta
     *
     * @param authorsId Identificador de la instancia de Cineasta
     * @return Colección de instancias de TemaEntity asociadas a la instancia de
     * Author
     */
    public List<TemaEntity> getTemas(Long authorsId) {
       
        return cineastaPersistence.find(authorsId).getTemas();
    }

    /**
     * Obtiene una instancia de TemaEntity asociada a una instancia de Cineasta
     *
     * @param authorsId Identificador de la instancia de Cineasta
     * @param temaId Identificador de la instancia de Tema
     * @return La entidadd de Tema del cineasta
     * @throws BusinessLogicException Si el tema no está asociado al cineasta
     */
    public TemaEntity getTema(Long temaId,Long authorsId) throws BusinessLogicException {
       
        List<TemaEntity> temas = getTemas(authorsId);
        TemaEntity temaEntity = temaPersistence.find(temaId);
        int index = temas.indexOf(temaEntity);
      
        if (index >= 0) {
            return temaEntity;
        }
        throw new BusinessLogicException("El tema no está asociado al cineasta");
    }

}
