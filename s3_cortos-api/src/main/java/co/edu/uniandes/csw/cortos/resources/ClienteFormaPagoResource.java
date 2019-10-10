/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.ComentarioDTO;
import co.edu.uniandes.csw.cortos.dtos.FormaDePagoDTO;
import co.edu.uniandes.csw.cortos.ejb.ClienteComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteFormaPagoLogic;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.FormaDePagoLogic;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author Estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteFormaPagoResource 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteFormaPagoResource.class.getName());
    
    @Inject
    private ClienteFormaPagoLogic clienteFormaPagoLogic;
    @Inject
    private FormaDePagoLogic formaPagoLogic;
    
    /**
     * Guarda un libro dentro de una editorial con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la editorial.
     *
     * @param clienteId Identificador de la editorial que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param fPagoId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - El libro guardado en la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{comentarioId: \\d+}")
    public FormaDePagoDTO addFormaPago(@PathParam("clienteId") Long clienteId, @PathParam("comentarioId") Long fPagoId) {
        LOGGER.log(Level.INFO, "EditorialBooksResource addBook: input: editorialsID: {0} , booksId: {1}", new Object[]{clienteId, fPagoId});
        if (formaPagoLogic.getFormaDePago(fPagoId) == null) {
            throw new WebApplicationException("El recurso /calificacion/" + fPagoId + " no existe.", 404);
        }
        FormaDePagoDTO fPagoDTO = new FormaDePagoDTO(clienteFormaPagoLogic.addFormaPago(fPagoId, clienteId));
        LOGGER.log(Level.INFO, "ClienteCalificacionResource addCalificacion: output: {0}", fPagoDTO);
        return fPagoDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la editorial.
     *
     * @param clienteId Identificador de la editorial que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en la
     * editorial. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<FormaDePagoDTO> getFormaPago(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteCalificacionResorce geicacion: input: {0}", clienteId);
        List<FormaDePagoDTO> listaDetailDTOs = fPagoListEntity2DTO(clienteFormaPagoLogic.getFormasPago(clienteId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la editorial con id asociado.
     *
     * @param clienteId Identificador de la editorial que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param fPagoId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * editorial.
     */
    @GET
    @Path("{calificacionId: \\d+}")
    public FormaDePagoDTO getFormaPago(@PathParam("clienteId") Long clienteId, @PathParam("comentarioId") Long fPagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: input: editorialsID: {0} , booksId: {1}", new Object[]{clienteId, fPagoId});
        if (formaPagoLogic.getFormaDePago(fPagoId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + clienteId + "/books/" + fPagoId + " no existe.", 404);
        }
        FormaDePagoDTO fPagoDTO = new FormaDePagoDTO(clienteFormaPagoLogic.getFormaPago(fPagoId, clienteId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: output: {0}", fPagoDTO);
        return fPagoDTO;
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Editorial
     *
     * @param clienreId Identificador de la editorial que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param fPagos JSONArray {@link BookDTO} El arreglo de libros nuevo para la
     * editorial.
     * @return JSON {@link BookDTO} - El arreglo de libros guardado en la
     * editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<FormaDePagoDTO> replaceFormaPago(@PathParam("clienteId") Long clienreId, List<FormaDePagoDTO> fPagos) {
        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: input: editorialsId: {0} , books: {1}", new Object[]{clienreId, fPagos});
        for (FormaDePagoDTO fPago : fPagos) {
            if (formaPagoLogic.getFormaDePago(fPago.getNumero()) == null) {
                throw new WebApplicationException("El recurso /books/" + fPago.getCcv() + " no existe.", 404);
            }
        }
        List<FormaDePagoDTO> listaDetailDTOs = fPagoListEntity2DTO(clienteFormaPagoLogic.replaceFormaPago(clienreId, fPagoListDTO2Entity(fPagos)));
        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    /**
     * Convierte una lista de BookEntity a una lista de BookDetailDTO.
     *
     * @param entityList Lista de BookEntity a convertir.
     * @return Lista de BookDTO convertida.
     */
    private List<FormaDePagoDTO> fPagoListEntity2DTO(List<FormaDePagoEntity> entityList) {
        List<FormaDePagoDTO> list = new ArrayList<>();
        for (FormaDePagoEntity entity : entityList) {
            list.add(new FormaDePagoDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BookDetailDTO a una lista de BookEntity.
     *
     * @param dtos Lista de BookDetailDTO a convertir.
     * @return Lista de BookEntity convertida.
     */
    private List<FormaDePagoEntity> fPagoListDTO2Entity(List<FormaDePagoDTO> dtos) {
        List<FormaDePagoEntity> list = new ArrayList<>();
        for (FormaDePagoDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
