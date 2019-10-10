
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;
import co.edu.uniandes.csw.cortos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.cortos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
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
@Path("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource {
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    @Inject 
    private CalificacionLogic calificacionLogic; 
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"CalifacacionResource createCalificacion : input : {0}", calificacion);
        CalificacionDTO nuevaCalificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacion(calificacion.toEntity()));
        LOGGER.log(Level.INFO,"CalificacionResoruce createCalificacion :output:{0}", nuevaCalificacionDTO);
        return nuevaCalificacionDTO;
    }
    @GET
    public List<CalificacionDTO> getCalificaciones()
    {
        LOGGER.info("CalificacionResource getCalificaciones :input : void");
        List<CalificacionDTO> listaCalificaciones = listEntity2DTO(calificacionLogic.getCalificaciones());
        LOGGER.log(Level.INFO,"ComentarioResource getCalificaciones :output{0}", listaCalificaciones);
        return listaCalificaciones;
    }
    
    @GET
    @Path("{calificacionesId:\\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionId") long id )
    {
        LOGGER.log(Level.INFO,"CalificacionReosurce getCalificacion :input : {0}",id);
        CalificacionEntity calificacionEntity = calificacionLogic.getCalificacion(id);
        if(calificacionEntity ==null)
        {
            throw new WebApplicationException("El recurso /calificacion/"+ id+"no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionEntity);
        return calificacionDTO;
    }
    
    @PUT
    @Path("{calificacionesId:\\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionId") Long id, CalificacionDTO calificacion) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"CalificacionResource updateCalificacion:input:id:{0},calificacion:{1}",new Object[]{id,calificacion});
        calificacion.setId(id);
        if(calificacionLogic.getCalificacion(id)==null)
        {
            throw new WebApplicationException("El recurso /calificacion/"+id+"no existe.",404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(id,calificacion.toEntity()));
        LOGGER.log(Level.INFO,"CalificacionResource updateCalificacionResource:outpur:{0}",calificacionDTO);
        return calificacionDTO;
    }   
    @DELETE
    @Path("{calificacionesId:\\d+}")
    public void deleteComentario(@PathParam("calificacionId") Long id ) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"ComentarioResource deleteComentario :input:{0}",id);
        CalificacionEntity entity = calificacionLogic.getCalificacion(id);
        if(entity ==null)
        {
            throw new WebApplicationException("El recurso /calificacion/"+id+"no existe.",404);
        }
        calificacionLogic.deleteCalificacion(id);
        LOGGER.info("ComentarioResorce deleteCalificacion:output:void");
    }
    
    
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList)
    {
        List<CalificacionDTO> list = new ArrayList<>();
        for(CalificacionEntity entity:entityList){
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
