/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.ComentarioDTO;
import co.edu.uniandes.csw.cortos.ejb.ComentarioLogic;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Santiago Vargas Vega
 */
@Path("comentarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped  
public class ComentarioResource {
    
    private static final Logger LOGGER = Logger.getLogger(ComentarioResource.class.getName());
    private  static final String NO = " no existe.";
    private  static final String REC ="El recurso /comentario/";
    @Inject 
    private ComentarioLogic comentarioLogic; 
    @POST
    public ComentarioDTO createComentario(ComentarioDTO comentario) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"ComentarioResource createComentario : input : {0}",comentario);
        ComentarioDTO nuevoComentarioDTO = new ComentarioDTO(comentarioLogic.createComentario(comentario.toEntity()));
        LOGGER.log(Level.INFO,"ComentarioResource createComentario : output : {0}",nuevoComentarioDTO);
        return nuevoComentarioDTO;
    }
    
    @GET 
    public List<ComentarioDTO> getComentarios()
    {
        LOGGER.info("ComentarioResource getComentarios :input :void");
        List<ComentarioDTO> listaComentarios = listEntity2DTO(comentarioLogic.getComentarios());
        LOGGER.log(Level.INFO,"ComentarioResource getComentarios :output{0}",listaComentarios);
        return listaComentarios;
    }
    
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO getComentario(@PathParam("comentariosId") Long id)
    {
        LOGGER.log(Level.INFO,"ComentarioResource getComentario :input:{0}", id);
        if (id == null)
              throw new WebApplicationException(REC+id+"si existe comentario1.",404);
        ComentarioEntity comentarioEntity = comentarioLogic.getComentario(id);
        if(comentarioEntity==null)
        {
            throw new WebApplicationException(REC+id+NO,404);
        }
        ComentarioDTO comentarioDTO= new ComentarioDTO(comentarioEntity);
        return comentarioDTO;
    }
    
    @PUT
    @Path("{comentariosId:\\d+}")
    public ComentarioDTO updateComentario(@PathParam("comentariosId") Long id,ComentarioDTO comentario)throws BusinessLogicException{
        LOGGER.log(Level.INFO,"ComentarioResource updateComentario:input:id:{0},comentario:{1}",new Object[]{id,comentario});
        comentario.setId(id);
        if(comentarioLogic.getComentario(id)==null)
        {
            throw new WebApplicationException(REC+id+NO,404);
        }
        ComentarioDTO comentarioDTO= new ComentarioDTO(comentarioLogic.updateComentario(id, comentario.toEntity()));
        LOGGER.log(Level.INFO,"ComentarioResorce updateComentario:output:{0}",comentarioDTO);
        return comentarioDTO;
    }
    
    @DELETE
    @Path("{comentariosId:\\d+}")
    public void deleteComentario(@PathParam("comentariosId") Long id) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"ComentarioResource deleteComentario:input:{0}", id);
        ComentarioEntity entity = comentarioLogic.getComentario(id);
        if(entity==null){
            throw new WebApplicationException("El recurso /comentario/ "+ id + NO,404);
        }
        comentarioLogic.deleteComentario(id);
        LOGGER.info("ComentarioResorce deleteComentario :output:void");
    }
    
    private List<ComentarioDTO> listEntity2DTO(List <ComentarioEntity> entityList)
    {
        List<ComentarioDTO> list = new ArrayList<>();
        for (ComentarioEntity entity:entityList) {
            list.add(new ComentarioDTO(entity));
        }
        return list;
    }
}
