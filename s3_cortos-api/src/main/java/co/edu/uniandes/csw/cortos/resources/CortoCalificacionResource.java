/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoCalificacionLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
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
public class CortoCalificacionResource {
    
    @Inject
    private CortoCalificacionLogic cortoCaliLogic;
    @Inject
    private CalificacionLogic calificacionLogic;
    
    
    @POST
    @Path("{calificacionId : \\d+}")
    public CalificacionDTO addComentario(@PathParam("cortosId") Long cortoId, @PathParam("comentarioId") Long comentarioId){
        if(calificacionLogic.getCalificacion(comentarioId)== null){
            throw new WebApplicationException("El recurso /comentario/"+comentarioId+" no existe", 404);
        }
        CalificacionDTO c = new CalificacionDTO(cortoCaliLogic.addCalificacion(comentarioId, cortoId));
        return c;
    }
    
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("cortosId")Long cortoId){
        return calificacionesListEntity2DTO(cortoCaliLogic.getCalificaciones(cortoId));
    }
    
    public List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> dtos){
        List<CalificacionDTO> list = new ArrayList<>();
        for(CalificacionEntity c : dtos)
            list.add(new CalificacionDTO(c));
        return list;
    }
    
    @GET
    @PathParam("calificacionId: \\ d+")
    public CalificacionDTO getCalificacion(@PathParam("cortosId") Long cortoId, @PathParam("calificacionId")Long calificacionId) throws BusinessLogicException{
        if(calificacionLogic.getCalificacion(calificacionId)== null){
            throw new WebApplicationException("El recurso /cortos/"+cortoId+"/calificaciones/"+calificacionId+" no existe", 404);
        }
        return new CalificacionDTO(cortoCaliLogic.getCalificacion(calificacionId, cortoId));
    }
}
