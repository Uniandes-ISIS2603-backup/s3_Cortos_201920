/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
public class ClienteComentarioLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteComentarioLogic.class.getName());

    @Inject
    private ComentarioPersistence comentarioPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Agregar un book a la editorial
     *
     * @param comentId El id libro a guardar
     * @param clienteId El id de la editorial en la cual se va a guardar el
     * libro.
     * @return El libro creado.
     */
    public ComentarioEntity addComentario(Long comentId, Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        ComentarioEntity comentEntity = comentarioPersistence.find(comentId);
        comentEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        return comentEntity;
    }

    /**
     * Retorna todos los books asociados a una editorial
     *
     * @param clienteId El ID de la editorial buscada
     * @return La lista de libros de la editorial
     */
    public List<ComentarioEntity> getComentarios(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la editorial con id = {0}", clienteId);
        return clientePersistence.find(clienteId).getComentarios();
    }

    /**
     * Retorna un book asociado a una editorial
     *
     * @param clienteId El id de la editorial a buscar.
     * @param comentId El id del libro a buscar
     * @return El libro encontrado dentro de la editorial.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * editorial
     */
    public ComentarioEntity getComentario(Long clienteId, Long comentId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, comentId);
        List<ComentarioEntity> coment = clientePersistence.find(clienteId).getComentarios();
        ComentarioEntity comentEntity = comentarioPersistence.find(comentId);
        int index = coment.indexOf(comentEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, comentId);
        if (index >= 0) {
            return coment.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la editorial");
    }

    /**
     * Remplazar books de una editorial
     *
     * @param coments Lista de libros que serán los de la editorial.
     * @param clienteId El id de la editorial que se quiere actualizar.
     * @return La lista de libros actualizada.
     */
    public List<ComentarioEntity> replaceComentarios(Long clienteId, List<ComentarioEntity> coments) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        List<ComentarioEntity> comentList = comentarioPersistence.findAll();
        for (ComentarioEntity coment : comentList) {
            if (coments.contains(coment)) {
                coment.setCliente(clienteEntity);
            } else if (coment.getCliente()!= null && coment.getCliente().equals(clienteEntity)) {
                coment.setCliente(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", clienteId);
        return coments;
    }
}
