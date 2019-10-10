/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CortoDTO;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.ejb.FacturaCortoLogic;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
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
 * @author Ingrith Barbosa
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FacturaCortoResource 
{
    /**
     * Referencia a la logica de asociacion entre factura y corto
     */
    @Inject
    private FacturaCortoLogic fcLogic;
    /**
     * Referencia a la logica de corto
     */
    @Inject
    private CortoLogic cortoLogic;
    
    /**
     * Servicio rest para aniadir corto a factura
     * @param facturaId identificacion de la factura
     * @param cortoId identificacion del corto
     * @return DTO del corto agregado
     */
    @POST
    @Path("{cortoId : \\d+}")
    public CortoDTO addCorto(@PathParam("facturaId") Long facturaId, @PathParam("cortoId") Long cortoId){
        if(cortoLogic.getCorto(cortoId)== null){
            throw new WebApplicationException("El recurso /corto/"+cortoId+" no existe", 404);
        }
        CortoDTO c = new CortoDTO(fcLogic.addCorto(facturaId, cortoId));
        return c;
    }
    /**
     * Retorna lista de cortos de una factura
     * @param facturaId identificacion de la factura
     * @return lista de cortos de una factura
     */
    @GET
    public List<CortoDTO> getCalificaciones(@PathParam("facturaId")Long facturaId){
        return cortosListEntity2DTO(fcLogic.getCortos(facturaId));
    }
    /**
     * Metodo auxiliar para transformar las entidades a dto
     * @param entities lista de entidades
     * @return lista de dtos de las entidades pasadas por parametro
     */
    private List<CortoDTO> cortosListEntity2DTO(List<CortoEntity> entities){
        List<CortoDTO> list = new ArrayList<>();
        for(CortoEntity c : entities)
            list.add(new CortoDTO(c));
        return list;
    }
    /**
     * Corto específico de una factura
     * @param facturaId identificacion de una factura
     * @param cortoId identificacion de un corto
     * @return corto de una factura
     * @throws BusinessLogicException cuando la califiacion no esta en el corto.
     */
    @GET
    @PathParam("cortoId: \\ d+")
    public CortoDTO getCorto(@PathParam("facturaId") Long facturaId, @PathParam("cortoId")Long cortoId) throws BusinessLogicException{
        if(cortoLogic.getCorto(cortoId)== null){
            throw new WebApplicationException("El recurso /factura/"+facturaId+"/corto/"+cortoId+" no existe", 404);
        }
        return new CortoDTO(fcLogic.getCorto(facturaId, cortoId));
    }
}
