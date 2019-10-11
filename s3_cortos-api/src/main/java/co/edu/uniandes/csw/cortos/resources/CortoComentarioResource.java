/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.ComentarioDTO;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoComentarioLogic;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CortoComentarioResource {
    /**
     * Referencia de la logica de la relacion entre corto y comentario
     */
    @Inject
    private CortoComentarioLogic cortoComenLogic;
    /**
     * Referencia de la logica del comentario
     */
    @Inject
    private ComentarioLogic comentLogic;
    
    /**
     * Servicio RESFTUL de aniadir un comentario a un corto
     * @param cortoId identificacion del corto
     * @param comentarioId identificacion del comentario
     * @return comentario que se aniade
     */
    @POST
    @Path("{comentarioId : \\d+}")
    public ComentarioDTO addComentario(@PathParam("cortoId") Long cortoId, @PathParam("comentarioId") Long comentarioId){
        if(comentLogic.getComentario(comentarioId)== null){
            throw new WebApplicationException("El recurso /comentario/"+comentarioId+" no existe", 404);
        }
        ComentarioDTO c = new ComentarioDTO(cortoComenLogic.addComentario(comentarioId, cortoId));
        return c;
    }
    /**
     * Lista de comentarios de un corto
     * @param cortoId identificacion del corto
     * @return Lista de comentarios de un corto
     */
    @GET
    public List<ComentarioDTO> getComentarios(@PathParam("cortosId") Long cortoId){
        return comentariosListEntity2DTO(cortoComenLogic.getComentarios(cortoId));
    }
    /**
     *Metodo auxiliar para transformar entidades a dtos
     * @param lista lista de entidades de comentarios
     * @return lista de comentario dto
     */
    private List<ComentarioDTO> comentariosListEntity2DTO(List<ComentarioEntity> lista){
        List<ComentarioDTO> list= new ArrayList<>();
        for(ComentarioEntity c : lista){
            list.add(new ComentarioDTO(c));
        }
        return list;
    }
    /**
     * Comentario particular de un corto
     * @param cortoId identificacion de corto
     * @param comentarioId identificacion de comentarios
     * @return comentario de un corto
     * @throws BusinessLogicException el comentario no pertenece al corto
     */
    @GET
    @Path("comentarioId: \\d+")
    public ComentarioDTO getComentario(@PathParam("cortosId")Long cortoId, @PathParam("comentarioId") Long comentarioId) throws BusinessLogicException{
        if((comentLogic.getComentario(comentarioId))== null)
            throw new WebApplicationException("El recurso /cortos/"+ cortoId+"/comentarios/"+comentarioId+" no existe", 404);
        ComentarioDTO comentario = new ComentarioDTO(cortoComenLogic.getComentario(comentarioId, cortoId));
        return comentario;
    }
}
