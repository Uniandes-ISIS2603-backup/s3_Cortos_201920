/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteCalificacionLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.mappers.BusinessLogicExceptionMapper;
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
 * @author Arturo Rubio
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteCalificacionResource 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteCalificacionResource.class.getName());
    
    @Inject
    private ClienteCalificacionLogic clienteCalificacionLogic;
    @Inject
    private CalificacionLogic calificacionLogic;
    
    /**
     * Guarda una calificacion dentro de un cliente con la informacion que recibe el
     * la URL. Se devuelve la calificacion que se guarda el cliente.
     *
     * @param clienteId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param califId Identificador de la calificacion que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - La calificacion guardada en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @POST
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO addCalificacion(@PathParam("clienteId") Long clienteId, @PathParam("calificacionId") Long califId) {
        LOGGER.log(Level.INFO, "EditorialBooksResource addBook: input: editorialsID: {0} , booksId: {1}", new Object[]{clienteId, califId});
        if (calificacionLogic.getCalificacion(califId) == null) {
            throw new WebApplicationException("El recurso /calificacion/" + califId + " no existe.", 404);
        }
        CalificacionDTO califDTO = new CalificacionDTO(clienteCalificacionLogic.addCalificacion(califId, clienteId));
        LOGGER.log(Level.INFO, "ClienteCalificacionResource addCalificacion: output: {0}", califDTO);
        return califDTO;
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
    public List<CalificacionDTO> getCalificacion(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteCalificacionResorce geicacion: input: {0}", clienteId);
        List<CalificacionDTO> listaDetailDTOs = calificacionListEntity2DTO(clienteCalificacionLogic.getCalififcaciones(clienteId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la editorial con id asociado.
     *
     * @param clienteId Identificador de la editorial que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param calificacionId Identificador del libro que se esta buscando. Este debe
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
    public CalificacionDTO getCalificacion(@PathParam("clienteId") Long clienteId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: input: editorialsID: {0} , booksId: {1}", new Object[]{clienteId, calificacionId});
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + clienteId + "/books/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(clienteCalificacionLogic.getCalificacion(calificacionId, clienteId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Editorial
     *
     * @param clienreId Identificador de la editorial que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param calif JSONArray {@link BookDTO} El arreglo de libros nuevo para la
     * editorial.
     * @return JSON {@link BookDTO} - El arreglo de libros guardado en la
     * editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<CalificacionDTO> replaceCalificacion(@PathParam("clienteId") Long clienreId, List<CalificacionDTO> calif) {
        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: input: editorialsId: {0} , books: {1}", new Object[]{clienreId, calif});
        for (CalificacionDTO cali : calif) {
            if (calificacionLogic.getCalificacion(cali.getId()) == null) {
                throw new WebApplicationException("El recurso /books/" + cali.getId() + " no existe.", 404);
            }
        }
        List<CalificacionDTO> listaDetailDTOs = calificacionListEntity2DTO(clienteCalificacionLogic.replaceCalificacion(clienreId, calificacionListDTO2Entity(calif)));
        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    /**
     * Convierte una lista de BookEntity a una lista de BookDetailDTO.
     *
     * @param entityList Lista de BookEntity a convertir.
     * @return Lista de BookDTO convertida.
     */
    private List<CalificacionDTO> calificacionListEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BookDetailDTO a una lista de BookEntity.
     *
     * @param dtos Lista de BookDetailDTO a convertir.
     * @return Lista de BookEntity convertida.
     */
    private List<CalificacionEntity> calificacionListDTO2Entity(List<CalificacionDTO> dtos) {
        List<CalificacionEntity> list = new ArrayList<>();
        for (CalificacionDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
