/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.ClientePersistence;
import co.edu.uniandes.csw.cortos.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
public class ClienteFacturasLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteFacturasLogic.class.getName());

    @Inject
    private FacturaPersistence facturasPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Agregar un book a la editorial
     *
     * @param factId El id libro a guardar
     * @param clienteId El id de la editorial en la cual se va a guardar el
     * libro.
     * @return El libro creado.
     */
    public FacturaEntity addfactura(Long factId, Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        FacturaEntity facturaEntity = facturasPersistence.find(factId);
        //facturaEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una calificacion al cliente con id = {0}", clienteId);
        return facturaEntity;
    }

    /**
     * Retorna todos los books asociados a una editorial
     *
     * @param clienteId El ID de la editorial buscada
     * @return La lista de libros de la editorial
     */
    public List<FacturaEntity> getFacturas(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la editorial con id = {0}", clienteId);
        return clientePersistence.find(clienteId).getFacturas();
    }

    /**
     * Retorna un book asociado a una editorial
     *
     * @param clienteId El id de la editorial a buscar.
     * @param facturaId El id del libro a buscar
     * @return El libro encontrado dentro de la editorial.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * editorial
     */
    public FacturaEntity getFactura(Long clienteId, Long facturaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, facturaId);
        List<FacturaEntity> coment = clientePersistence.find(clienteId).getFacturas();
        FacturaEntity facturaEntity = facturasPersistence.find(facturaId);
        int index = coment.indexOf(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} de la editorial con id = " + clienteId, facturaId);
        if (index >= 0) {
            return coment.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la editorial");
    }

    /**
     * Remplazar books de una editorial
     *
     * @param facts Lista de libros que serán los de la editorial.
     * @param clienteId El id de la editorial que se quiere actualizar.
     * @return La lista de libros actualizada.
     */
    public List<FacturaEntity> replaceFactura(Long clienteId, List<FacturaEntity> facts) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        List<FacturaEntity> factList = facturasPersistence.findAll();
        for (FacturaEntity fact : factList) {
            /*if (facts.contains(fact)) {
                fact.setCliente(clienteEntity);
            } else if (fact.getCliente()!= null && fact.getCliente().equals(clienteEntity)) {
                fact.setCliente(null);
            }*/
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", clienteId);
        return facts;
    }
}
