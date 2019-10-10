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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pedro Callejas
 */
@Stateless
public class CineastaTemasLogic {

    private static final Logger LOGGER = Logger.getLogger(CineastaTemasLogic.class.getName());

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
    public TemaEntity addTema(Long cineastaId, Long temaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un tema al cineasta con id = {0}", cineastaId);
        CineastaEntity authorEntity = cineastaPersistence.find(cineastaId);
        TemaEntity temaEntity = temaPersistence.find(temaId);
        temaEntity.getCineasta().add(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un tema al cineasta con id = {0}", cineastaId);
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
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los temas del cineasta con id = {0}", authorsId);
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
    public TemaEntity getTema(Long authorsId, Long temaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el tema con id = {0} del cineasta con id = " + authorsId, temaId);
        List<TemaEntity> temas = cineastaPersistence.find(authorsId).getTemas();
        TemaEntity temaEntity = temaPersistence.find(temaId);
        int index = temas.indexOf(temaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el tema con id = {0} del cineasta con id = " + authorsId, temaId);
        if (index >= 0) {
            return temas.get(index);
        }
        throw new BusinessLogicException("El tema no está asociado al cineasta");
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Author
     *
     * @param authorId Identificador de la instancia de Author
     * @param temas Colección de instancias de TemaEntity a asociar a instancia
     * de Cineasta
     * @return Nueva colección de TemasEntity asociada a la instancia de
     * Cineasta
     */
    public List<TemaEntity> replaceTemas(Long authorId, List<TemaEntity> temas) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los temas asocidos al cineasta con id = {0}", authorId);
        CineastaEntity authorEntity = cineastaPersistence.find(authorId);
        List<TemaEntity> temaList = temaPersistence.findAll();
        for (TemaEntity tema : temaList) {
            if (temas.contains(tema)) {
                if (!tema.getCineasta().contains(authorEntity)) {
                    tema.getCineasta().add(authorEntity);
                }
            } else {
                tema.getCineasta().remove(authorEntity);
            }
        }
        authorEntity.setTemas(temas);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los temas asocidos al cineasta con id = {0}", authorId);
        return authorEntity.getTemas();
    }

    /**
     * Desasocia un Tema existente de un Cineasta existente
     *
     * @param authorsId Identificador de la instancia de Cineasta
     * @param temaId Identificador de la instancia de Tema
     */
    public void removeBook(Long authorsId, Long temaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un tema del cineasta con id = {0}", authorsId);
        CineastaEntity authorEntity = cineastaPersistence.find(authorsId);
        TemaEntity temaEntity = temaPersistence.find(temaId);
        temaEntity.getCineasta().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un tema del cineasta con id = {0}", authorsId);
    }
}
