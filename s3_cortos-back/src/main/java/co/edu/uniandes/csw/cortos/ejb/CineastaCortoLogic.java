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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @corto Pedro Callejas
 */
@Stateless
public class CineastaCortoLogic {
      private static final Logger LOGGER = Logger.getLogger(CineastaCortoLogic.class.getName());

    @Inject
    private CineastaPersistence cineastaPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private CortoPersistence cortoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un corto a un cineasta
     *
     * @param cortoId El id corto a guardar
     * @param cortosId El id del cineasta al cual se le va a guardar el corto.
     * @return El corto que fue agregado al cineasta.
     */
    public CortoEntity addCorto(Long cortosId, Long cortoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el corto con id = {0} al cineasta con id = " + cortoId, cortosId);
        CortoEntity cortoEntity = cortoPersistence.find(cortoId);
        CineastaEntity cineastaEntity = cineastaPersistence.find(cortosId);
        cineastaEntity.setCortoCineastas(cortoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el corto con id = {0} al cineasta con id = " + cortoId, cortosId);
        return cortoPersistence.find(cortosId);
    }

    /**
     *
     * Obtener un premio por medio de su id y el de su autor.
     *
     * @param cortoId id del corto a ser buscado.
     * @return el cineasta solicitada por medio de su id.
     */
    public CortoEntity getCorto(Long cortoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cineasta del corto con id = {0}", cortoId);
        CortoEntity cortoEntity = cineastaPersistence.find(cortoId).getCortoCineastas();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cineasta del corto con id = {0}", cortoId);
        return cortoEntity;
    }

    /**
     * Remplazar autor de un premio
     *
     * @param cortoId el id del premio que se quiere actualizar.
     * @param cortosId El id del nuebo autor asociado al premio.
     * @return el nuevo autor asociado.
     */
    public CortoEntity replaceCorto(Long cortoId, Long cortosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cineasta del premio corto con id = {0}", cortoId);
        CortoEntity cortoEntity = cortoPersistence.find(cortosId);
        CineastaEntity cineastaEntity = cineastaPersistence.find(cortoId);
        cineastaEntity.setCortoCineastas(cortoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el cineasta con id = {0} al corto con id = " + cortoId, cortosId);
        return cortoPersistence.find(cortosId);
    }

    /**
     * Borrar el cineasta de un corto
     *
     * @param cineastasId El corto que se desea borrar del cineasta.
     * @throws BusinessLogicException si el corto no tiene cineasta
     */
    public void removeCorto(Long cineastasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cineasta del corto con id = {0}", cineastasId);
        CineastaEntity cineastaEntity = cineastaPersistence.find(cineastasId);
        if (cineastaEntity.getCortoCineastas() == null) {
            throw new BusinessLogicException("El premio no tiene autor");
        }
        CortoEntity cortoEntity = cortoPersistence.find(cineastaEntity.getCortoCineastas().getId());
        cineastaEntity.setCortoCineastas(null);
        cortoEntity.getCineasta().remove(cineastaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cineasta con id = {0} del corto con id = " + cineastasId, cortoEntity.getId());
    }
}
