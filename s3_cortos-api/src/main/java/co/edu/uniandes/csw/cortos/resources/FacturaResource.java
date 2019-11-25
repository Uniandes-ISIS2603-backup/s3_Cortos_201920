/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.FacturaDTO;
import co.edu.uniandes.csw.cortos.dtos.FacturaDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.FacturaLogic;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;

import java.util.ArrayList;

import java.util.List;


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
 * @author Ingrith Barbosa
 */
@Path("factura")

@Consumes("application/json")
@Produces("application/json")
@RequestScoped
public class FacturaResource 
{
    @Inject
    FacturaLogic facturaLogic;
    private static final String NO = " no existe";
    private static final String REC = "el recurso factura/";
    /**
     * Crea una factura
     * @param c DTO de factura
     * @return factura que se debe crear
     * @throws BusinessLogicException no se cumplen reglas de negocio
     */
    @POST
    public FacturaDTO createFactura(FacturaDTO c) throws BusinessLogicException{
        return new FacturaDTO(facturaLogic.createFactura(c.toEntity()));
        
    }
    /**
     * lista de todas las facturas
     * @return lista de facturas
     */
    @GET
    public List<FacturaDetailDTO> getFacturas(){
        return listEntity2Entity(facturaLogic.getFacturas());
        
    }
    /**
     * Factura con el id por parametro
     * @param facturaId identificacion del factura
     * @return factura con identificacion por paramtero 
     */
    @GET
    @Path("{facturaId: \\d+}")
    public FacturaDetailDTO getFactura(@PathParam("facturaId") Long facturaId){
        FacturaEntity c = facturaLogic.getFactura(facturaId);
        if(c == null)
            throw new WebApplicationException(REC + facturaId + NO, 404);
        return new FacturaDetailDTO(c);
    }
    /**
     * Modifica el factura con id por parametro
     * @param facturaId identificacion del factura
     * @param c factura con info para modificar el anterior
     * @return factura modificado
     * @throws BusinessLogicException se incumple regla de negocio
     */
    @PUT
    @Path("{facturasId: \\d+}")
    public FacturaDetailDTO updateFactura(@PathParam("facturasId") Long facturaId, FacturaDetailDTO c) throws BusinessLogicException{
        c.setId(facturaId);
        if(facturaLogic.getFactura(facturaId)== null)
            throw new WebApplicationException(REC + facturaId + NO, 404);
        return new FacturaDetailDTO(facturaLogic.updateFactura(facturaId, c.toEntity()));
        
    }
    @DELETE
    @Path("{facturaId:\\d+}")
    public void deleteFactura(@PathParam("facturaId") Long id ) throws BusinessLogicException
    {
        FacturaEntity entity = facturaLogic.getFactura(id);
        if(entity ==null)
        {
            throw new WebApplicationException(REC+id+NO,404);
        }
        facturaLogic.deleteFactura(id, entity);
    }
    
    /**
     * convertir entidades en DTO
     * @param l lista entidades
     * @return lista de dtos. 
     */
    public List<FacturaDetailDTO> listEntity2Entity(List<FacturaEntity> l){
        List<FacturaDetailDTO> lista = new ArrayList<>();
        for(FacturaEntity c : l)
            lista.add(new FacturaDetailDTO(c));
        return lista;
    }
    
    @Path("{facturaId: \\+d}/cortos")
    public Class<FacturaCortoResource> getFacturaCortoResource(@PathParam("facturaId") Long facturaId){
        if(facturaLogic.getFactura(facturaId)== null){
            throw new WebApplicationException(REC+facturaId+NO, 404);
        }
        return FacturaCortoResource.class;
    }
}
