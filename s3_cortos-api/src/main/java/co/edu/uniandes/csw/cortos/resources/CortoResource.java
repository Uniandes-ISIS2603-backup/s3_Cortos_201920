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
    
    
    private  static final String NO = " no existe";
    private  static final String REC = "el recurso /cortos/";
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
        return new CortoDTO(cl.createCorto(c.toEntity()));
    }
    /**
     * lista de todos los cortos
     * @return lista de cortos
     */
    @GET
    public List<CortoDetailDTO> getCortos(){
        return listEntity2Entity(cl.getCortos());
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
            throw new WebApplicationException(REC + cortosId + NO, 404);
        return new CortoDetailDTO(c);
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
            throw new WebApplicationException(REC + cortosId + NO, 404);
        return new CortoDetailDTO(cl.updateCorto(cortosId, c.toEntity()));
        
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

    
    @Path("{cortosId: \\d+}/comentarios")
    public Class<CortoComentarioResource> getCortoComentarioResource(@PathParam("cortosId") Long cortosId){
        
        
        if(cl.getCorto(cortosId)==null){
            throw new WebApplicationException(REC + cortosId + NO, 404);
        }
        return CortoComentarioResource.class;
    }
 
    @Path("{cortosId: \\d+}/calificaciones")
        public Class<CortoCalificacionResource> getCortoCalResource(@PathParam("cortosId") Long cortosId){
        
        
        if(cl.getCorto(cortosId)==null){
            throw new WebApplicationException(REC + cortosId + NO, 404);
        }
        return CortoCalificacionResource.class;
    }
    @Path("{cortosId: \\d+}/cineastas")
    public Class<CortoCineastasResource> getCortoCineastasResource(@PathParam("cortosId") Long cortosId){
        if(cl.getCorto(cortosId)==null){
            throw new WebApplicationException(REC + cortosId + NO, 404);
        }
        return CortoCineastasResource.class;
    }
  
    
    @Path("{cortosId: \\d+}/facturas")
    public Class<CortoFacturaResource> getCortoFacturasResource(@PathParam("cortosId") Long cortosId){
        if(cl.getCorto(cortosId)==null)
            throw new WebApplicationException(REC+cortosId+NO, 404);
        return CortoFacturaResource.class;
    }
    
    @Path("{cortosId: \\d+}/temas")
    public Class<CortoTemaResource> getCortoTemaResource(@PathParam("cortosId") Long cortosId){
        if(cl.getCorto(cortosId)==null)
            throw new WebApplicationException(REC+cortosId+NO, 404);
        
        return CortoTemaResource.class;
    }
    
    @GET
    @Path("search-{CortoFind: [a-zA-Z][a-zA-Z]*}")
    public List<CortoDetailDTO> getCortoNombreLike(@PathParam("CortoFind") String name )
    {
       
        List<CortoEntity> cortoEntity = cl.getCortoNombreLike(name);
        if(cortoEntity ==null)
        {
            throw new WebApplicationException("El recurso /cliente/"+ name+"no existe.", 404);
        }
        List<CortoDetailDTO> clienteDTO = listEntity2DTO(cortoEntity);
        return clienteDTO;
    }
    private List<CortoDetailDTO> listEntity2DTO(List<CortoEntity> entityList)
    {
        List<CortoDetailDTO> list = new ArrayList<>();
        for(CortoEntity entity:entityList){
            list.add(new CortoDetailDTO(entity));
        }
        return list;
    }
}

