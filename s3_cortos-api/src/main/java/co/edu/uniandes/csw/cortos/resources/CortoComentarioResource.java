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
    @Inject
    private CortoComentarioLogic cortoComenLogic;
    @Inject
    private ComentarioLogic comentLogic;
    
    
    @POST
    @Path("{comentarioId : \\d+}")
    public ComentarioDTO addComentario(@PathParam("cortoId") Long cortoId, @PathParam("comentarioId") Long comentarioId){
        if(comentLogic.getComentario(comentarioId)== null){
            throw new WebApplicationException("El recurso /comentario/"+comentarioId+" no existe", 404);
        }
        ComentarioDTO c = new ComentarioDTO(cortoComenLogic.addComentario(comentarioId, cortoId));
        return c;
    }
    @GET
    public List<ComentarioDTO> getComentarios(@PathParam("cortosId") Long cortoId){
        return null;
    }
    public List<ComentarioDTO> comentariosListEntity2DTO(List<ComentarioEntity> lista){
        List<ComentarioDTO> list= new ArrayList<>();
        for(ComentarioEntity c : lista){
            list.add(new ComentarioDTO(c));
        }
        return list;
    }
}
