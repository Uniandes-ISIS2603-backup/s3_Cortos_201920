/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoCalificacionLogic;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Juan Sebastian Gomez
 */
public class CortoCalificacionResource {
    
    @Inject
    private CortoCalificacionLogic cortoComenLogic;
    @Inject
    private CalificacionLogic comentLogic;
    
    
    @POST
    @Path("{comentarioId : \\d+}")
    public CalificacionDTO addComentario(@PathParam("cortoId") Long cortoId, @PathParam("comentarioId") Long comentarioId){
        if(comentLogic.getCalificacion(comentarioId)== null){
            throw new WebApplicationException("El recurso /comentario/"+comentarioId+" no existe", 404);
        }
        CalificacionDTO c = new CalificacionDTO(cortoComenLogic.addCalificacion(comentarioId, cortoId));
        return c;
    }
    
}
