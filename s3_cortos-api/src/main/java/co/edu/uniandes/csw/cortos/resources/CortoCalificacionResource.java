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
    /**
     * Referencia a la logica de asociacion entre corto y calificacion
     */
    @Inject
    private CortoCalificacionLogic cortoCaliLogic;
    /**
     * Referencia a la logica de calificacion
     */
    @Inject
    private CalificacionLogic calificacionLogic;
    
    /**
     * Servicio rest para aniadir comentario a un corto
     * @param cortoId identificacion del corto
     * @param calificacionId identificacion de la calificacion
     * @return DTO de la calificacion agregada
     */
    @POST
    @Path("{calificacionId : \\d+}")
    public CalificacionDTO addComentario(@PathParam("cortosId") Long cortoId, @PathParam("calificacionId") Long calificacionId){
        if(calificacionLogic.getCalificacion(calificacionId)== null){
            throw new WebApplicationException("El recurso /comentario/"+calificacionId+" no existe", 404);
        }
        CalificacionDTO c = new CalificacionDTO(cortoCaliLogic.addCalificacion(calificacionId, cortoId));
        return c;
    }
    /**
     * Retorna lista de calificaciones de un corto
     * @param cortoId identificacion del corto
     * @return lista de calificaciones de un corto
     */
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("cortosId")Long cortoId){
        return calificacionesListEntity2DTO(cortoCaliLogic.getCalificaciones(cortoId));
    }
    /**
     * Metodo auxiliar para transformar las entidades a dto
     * @param entities lista de entidades
     * @return lista de dtos de las entidades pasadas por parametro
     */
    private List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entities){
        List<CalificacionDTO> list = new ArrayList<>();
        for(CalificacionEntity c : entities)
            list.add(new CalificacionDTO(c));
        return list;
    }
    /**
     * Calificacion especifica de un corto
     * @param cortoId identificacion de un corto
     * @param calificacionId identificacion de una calificacion
     * @return calificacion de un corto 
     * @throws BusinessLogicException cuando la califiacion no esta en el corto.
     */
    @GET
    @PathParam("calificacionId: \\ d+")
    public CalificacionDTO getCalificacion(@PathParam("cortosId") Long cortoId, @PathParam("calificacionId")Long calificacionId) throws BusinessLogicException{
        if(calificacionLogic.getCalificacion(calificacionId)== null){
            throw new WebApplicationException("El recurso /cortos/"+cortoId+"/calificaciones/"+calificacionId+" no existe", 404);
        }
        return new CalificacionDTO(cortoCaliLogic.getCalificacion(calificacionId, cortoId));
    }
}
