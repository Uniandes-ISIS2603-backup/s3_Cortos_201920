/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.cortos.dtos.ComentarioDTO;
import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteCalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.ClienteComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
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
public class ClienteComentarioResource 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteComentarioResource.class.getName());
    
    @Inject
    private ClienteComentarioLogic clienteComentarioLogic;
    @Inject
    private ComentarioLogic comentarioLogic;
    
    /**
     * Guarda un libro dentro de una editorial con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la editorial.
     *
     * @param clienteId Identificador de la editorial que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param comentId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - El libro guardado en la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{comentarioId: \\d+}")
    public ComentarioDTO addComentario(@PathParam("clienteId") Long clienteId, @PathParam("comentarioId") Long comentId) {
        LOGGER.log(Level.INFO, "EditorialBooksResource addBook: input: editorialsID: {0} , booksId: {1}", new Object[]{clienteId, comentId});
        if (comentarioLogic.getComentario(comentId) == null) {
            throw new WebApplicationException("El recurso /calificacion/" + comentId + " no existe.", 404);
        }
        ComentarioDTO comentDTO = new ComentarioDTO(clienteComentarioLogic.addComentario(comentId, clienteId));
        LOGGER.log(Level.INFO, "ClienteCalificacionResource addCalificacion: output: {0}", comentDTO);
        return comentDTO;
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
    public List<ComentarioDTO> getComentarios(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteCalificacionResorce geicacion: input: {0}", clienteId);
        List<ComentarioDTO> listaDetailDTOs = comentarioListEntity2DTO(clienteComentarioLogic.getComentarios(clienteId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la editorial con id asociado.
     *
     * @param clienteId Identificador de la editorial que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param comentarioId Identificador del libro que se esta buscando. Este debe
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
    public ComentarioDTO getComentario(@PathParam("clienteId") Long clienteId, @PathParam("comentarioId") Long comentarioId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: input: editorialsID: {0} , booksId: {1}", new Object[]{clienteId, comentarioId});
        if (comentarioLogic.getComentario(comentarioId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + clienteId + "/books/" + comentarioId + " no existe.", 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(clienteComentarioLogic.getComentario(comentarioId, clienteId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: output: {0}", comentarioDTO);
        return comentarioDTO;
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Editorial
     *
     * @param clienreId Identificador de la editorial que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param coment JSONArray {@link BookDTO} El arreglo de libros nuevo para la
     * editorial.
     * @return JSON {@link BookDTO} - El arreglo de libros guardado en la
     * editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<ComentarioDTO> replaceComentario(@PathParam("clienteId") Long clienreId, List<ComentarioDTO> coment) {
        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: input: editorialsId: {0} , books: {1}", new Object[]{clienreId, coment});
        for (ComentarioDTO cali : coment) {
            if (comentarioLogic.getComentario(cali.getId()) == null) {
                throw new WebApplicationException("El recurso /books/" + cali.getId() + " no existe.", 404);
            }
        }
        List<ComentarioDTO> listaDetailDTOs = comentarioListEntity2DTO(clienteComentarioLogic.replaceComentarios(clienreId, comentarioListDTO2Entity(coment)));
        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    /**
     * Convierte una lista de BookEntity a una lista de BookDetailDTO.
     *
     * @param entityList Lista de BookEntity a convertir.
     * @return Lista de BookDTO convertida.
     */
    private List<ComentarioDTO> comentarioListEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDTO> list = new ArrayList<>();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BookDetailDTO a una lista de BookEntity.
     *
     * @param dtos Lista de BookDetailDTO a convertir.
     * @return Lista de BookEntity convertida.
     */
    private List<ComentarioEntity> comentarioListDTO2Entity(List<ComentarioDTO> dtos) {
        List<ComentarioEntity> list = new ArrayList<>();
        for (ComentarioDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
