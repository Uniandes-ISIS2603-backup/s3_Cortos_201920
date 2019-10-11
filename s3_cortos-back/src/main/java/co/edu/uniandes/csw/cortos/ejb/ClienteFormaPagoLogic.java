/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.cortos.persistence.FormaDePagoPersistance;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class ClienteFormaPagoLogic
{
    private static final Logger LOGGER = Logger.getLogger(ClienteFormaPagoLogic.class.getName());

    @Inject
    private FormaDePagoPersistance fPagoPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Agregar un book a la editorial
     *
     * @param formaPagoId El id libro a guardar
     * @param clienteId El id de la editorial en la cual se va a guardar el
     * libro.
     * @return El libro creado.
     */
    public FormaDePagoEntity addFormaPago(Long formaPagoId, Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        FormaDePagoEntity fPagoEntity = fPagoPersistence.find(formaPagoId);
        //fPagoEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        return fPagoEntity;
    }

    /**
     * Retorna todos los books asociados a una editorial
     *
     * @param clienteId El ID de la editorial buscada
     * @return La lista de libros de la editorial
     */
    public List<FormaDePagoEntity> getFormasPago(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la editorial con id = {0}", clienteId);
        return clientePersistence.find(clienteId).getFormasPago();
    }

    /**
     * Retorna un book asociado a una editorial
     *
     * @param clienteId El id de la editorial a buscar.
     * @param fPagoId El id del libro a buscar
     * @return El libro encontrado dentro de la editorial.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * editorial
     */
    public FormaDePagoEntity getFormaPago(Long clienteId, Long fPagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, fPagoId);
        List<FormaDePagoEntity> coment = clientePersistence.find(clienteId).getFormasPago();
        FormaDePagoEntity fPagoEntity = fPagoPersistence.find(fPagoId);
        int index = coment.indexOf(fPagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, fPagoId);
        if (index >= 0) {
            return coment.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la editorial");
    }

    /**
     * Remplazar books de una editorial
     *
     * @param forms Lista de libros que serán los de la editorial.
     * @param clienteId El id de la editorial que se quiere actualizar.
     * @return La lista de libros actualizada.
     */
    public List<FormaDePagoEntity> replaceFormaPago(Long clienteId, List<FormaDePagoEntity> forms) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        List<FormaDePagoEntity> formList = fPagoPersistence.findAll();
        for (FormaDePagoEntity form : formList) {
            /* if (forms.contains(form)) {
                form.setCliente(clienteEntity);
            } else if (form.getCliente()!= null && form.getCliente().equals(clienteEntity)) {
                form.setCliente(null);
            }*/
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", clienteId);
        return forms;
    }
}
