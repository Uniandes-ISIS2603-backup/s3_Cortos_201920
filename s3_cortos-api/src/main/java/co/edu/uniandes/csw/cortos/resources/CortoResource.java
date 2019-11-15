/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.cortos.dtos.CortoDTO;
import co.edu.uniandes.csw.cortos.dtos.CortoDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.CortoCalificacionLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Path("cortos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CortoResource {
    
    private static final Logger LOGGER = Logger.getLogger(CortoResource.class.getName());
    /**
     * Referencia de logica de corto
     */
    @Inject
    private CortoLogic cl;
    @Inject
    private CortoCalificacionLogic cortoCaliLogic;
    /**
     * Crea un corto
     * @param c DTO de corto
     * @return corto que se debe crear
     * @throws BusinessLogicException no se cumplen reglas de negocio
     */
    @POST
    public CortoDTO createCorto(CortoDTO c) throws BusinessLogicException{
        CortoDTO nuevo = new CortoDTO(cl.createCorto(c.toEntity()));
        return nuevo;
    }
    /**
     * lista de todos los cortos
     * @return lista de cortos
     */
    @GET
    public List<CortoDetailDTO> getCortos(){

        List<CortoDetailDTO> lista = listEntity2Entity(cl.getCortos());
        return lista;
    }
    /**
     * Corto con el id por parametro
     * @param cortosId identificacion del corto
     * @return corto con identificacion por paramtero 
     */
    @GET
    @Path("{cortosId: \\d+}")
    public CortoDetailDTO getCorto(@PathParam("cortosId") Long cortosId){
        CortoEntity c = cl.getCorto(cortosId);
        if(c == null)
            throw new WebApplicationException("el recurso /cortos/" + cortosId + " no existe", 404);
        CortoDetailDTO n = new CortoDetailDTO(c);
        return n;
    }
    /**
     * Modifica el corto con id por parametro
     * @param cortosId identificacion del corto
     * @param c corto con info para modificar el anterior
     * @return corto modificado
     * @throws BusinessLogicException se incumple regla de negocio
     */
    @PUT
    @Path("{cortosId: \\d+}")
    public CortoDetailDTO updateCorto(@PathParam("cortosId") Long cortosId, CortoDetailDTO c) throws BusinessLogicException{
        c.setId(cortosId);
        if(cl.getCorto(cortosId)== null)
            throw new WebApplicationException("el recurso /cortos/" + cortosId + " no existe", 404);
        CortoDetailDTO n = new CortoDetailDTO(cl.updateCorto(cortosId, c.toEntity()));
        return n;
    }
    
    
    /**
     * convertir entidades en DTO
     * @param l lista entidades
     * @return lista de dtos. 
     */
    public List<CortoDetailDTO> listEntity2Entity(List<CortoEntity> l){
        List<CortoDetailDTO> lista = new ArrayList<>();
        for(CortoEntity c : l)
            lista.add(new CortoDetailDTO(c));
        return lista;
    }
    
   
     /**
     * Retorna lista de calificaciones de un corto
     * @param cortosId identificacion del corto
     * @return lista de calificaciones de un corto
     */
//    @Path("{cortosId: \\d+}/calificaciones")
//    @GET
//    public List<CalificacionDTO> getCalificaciones(@PathParam("cortosId")Long cortosId){
//        return calificacionesListEntity2DTO(cortoCaliLogic.getCalificaciones(cortosId));
//    }
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
    
    @Path("{cortosId: \\d+}/comentarios")
    public Class<CortoComentarioResource> getCortoComentarioResource(@PathParam("cortosId") Long cortosId){
        
        
        if(cl.getCorto(cortosId)==null){
            throw new WebApplicationException("el recurso /cortos/" + cortosId + " no existe", 404);
        }
        return CortoComentarioResource.class;
    }
 
    @Path("{cortosId: \\d+}/calificaciones")
        public Class<CortoCalificacionResource> getCortoCalResource(@PathParam("cortosId") Long cortosId){
        
        
        if(cl.getCorto(cortosId)==null){
            throw new WebApplicationException("el recurso /cortos/" + cortosId + " no existe", 404);
        }
        return CortoCalificacionResource.class;
    }
}

