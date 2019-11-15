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
    private CortoCalificacionLogic cortoCalificacionLogic;
    @Inject
    private CalificacionLogic calificacionLogic;
    
    @POST
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO addCalificacion(@PathParam("cortosId") Long cortosId,@PathParam("calificacionesId") Long calificacionesId){
        if(calificacionLogic.getCalificacion(calificacionesId)== null){
            throw new WebApplicationException("El recurso/calificacion/"+ calificacionesId+" no existe",404);
        }
        CalificacionDTO c = new CalificacionDTO(cortoCalificacionLogic.addCalificacion(calificacionesId,cortosId));
        return c;
    }
    private List<CalificacionDTO> calificacionListEntity2DTO(List<CalificacionEntity> lista){
        List<CalificacionDTO> list = new ArrayList<>();
        for(CalificacionEntity c : lista){
            list.add(new CalificacionDTO(c));
        }
        return list;
    }
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("cortosId") Long cortosId){
        return calificacionListEntity2DTO(cortoCalificacionLogic.getCalificaciones(cortosId));
    }
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("cortosId") Long cortosId, @PathParam("calificacionesId") Long calificacionesId)throws BusinessLogicException{
        if(calificacionLogic.getCalificacion(calificacionesId)==null){
            throw new WebApplicationException("El recurso/cortos/"+cortosId+"/calificaciones/"+calificacionesId+" no existe",404);
        }
        CalificacionDTO calificacion = new CalificacionDTO(cortoCalificacionLogic.getCalificacion(calificacionesId, cortosId));
        return calificacion;
    }
}
