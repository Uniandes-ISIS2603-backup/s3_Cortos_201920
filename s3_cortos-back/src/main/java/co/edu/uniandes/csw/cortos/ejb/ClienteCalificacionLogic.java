/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Arturo Rubio
 */
@Stateless
public class ClienteCalificacionLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteCalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence calificacionPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Agregar un book a la editorial
     *
     * @param califId El id libro a guardar
     * @param clienteId El id de la editorial en la cual se va a guardar el
     * libro.
     * @return El libro creado.
     */
    public CalificacionEntity addCalificacion(Long califId, Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        CalificacionEntity califEntity = calificacionPersistence.find(califId);
        califEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        return califEntity;
    }

    /**
     * Retorna todos los books asociados a una editorial
     *
     * @param clienteId El ID de la editorial buscada
     * @return La lista de libros de la editorial
     */
    public List<CalificacionEntity> getCalififcaciones(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la editorial con id = {0}", clienteId);
        return clientePersistence.find(clienteId).getCalificaciones();
    }

    /**
     * Retorna un book asociado a una editorial
     *
     * @param clienteId El id de la editorial a buscar.
     * @param califId El id del libro a buscar
     * @return El libro encontrado dentro de la editorial.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * editorial
     */
    public CalificacionEntity getCalificacion(Long clienteId, Long califId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, califId);
        List<CalificacionEntity> calif = clientePersistence.find(clienteId).getCalificaciones();
        CalificacionEntity califEntity = calificacionPersistence.find(califId);
        int index = calif.indexOf(califEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, califId);
        if (index >= 0) {
            return calif.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la editorial");
    }

    /**
     * Remplazar books de una editorial
     *
     * @param books Lista de libros que serán los de la editorial.
     * @param clienteId El id de la editorial que se quiere actualizar.
     * @return La lista de libros actualizada.
     */
    public List<CalificacionEntity> replaceCalificacion(Long clienteId, List<CalificacionEntity> books) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", clienteId);
        ClienteEntity editorialEntity = clientePersistence.find(clienteId);
        List<CalificacionEntity> califList = calificacionPersistence.findAll();
        for (CalificacionEntity calif : califList) {
            if (books.contains(calif)) {
                calif.setCliente(editorialEntity);
            } else if (calif.getCliente()!= null && calif.getCliente().equals(editorialEntity)) {
                calif.setCliente(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", clienteId);
        return books;
    }
}
