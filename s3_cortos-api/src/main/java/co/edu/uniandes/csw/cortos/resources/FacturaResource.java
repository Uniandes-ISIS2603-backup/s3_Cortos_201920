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
import java.util.logging.Level;

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
    
    /**
     * Crea una factura
     * @param c DTO de factura
     * @return factura que se debe crear
     * @throws BusinessLogicException no se cumplen reglas de negocio
     */
    @POST
    public FacturaDTO createFactura(FacturaDTO c) throws BusinessLogicException{
        FacturaDTO nuevo = new FacturaDTO(facturaLogic.createFactura(c.toEntity()));
        return nuevo;
    }
    /**
     * lista de todas las facturas
     * @return lista de facturas
     */
    @GET
    public List<FacturaDetailDTO> getFacturas(){
        List<FacturaDetailDTO> lista = listEntity2Entity(facturaLogic.getFacturas());
        return lista;
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
            throw new WebApplicationException("el recurso /factura/" + facturaId + " no existe", 404);
        FacturaDetailDTO n = new FacturaDetailDTO(c);
        return n;
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
            throw new WebApplicationException("el recurso /books/" + facturaId + " no existe", 404);
        FacturaDetailDTO n = new FacturaDetailDTO(facturaLogic.updateFactura(facturaId, c.toEntity()));
        return n;
    }
    @DELETE
    @Path("{facturaId:\\d+}")
    public void deleteFactura(@PathParam("facturaId") Long id ) throws BusinessLogicException
    {
        //LOGGER.log(Level.INFO,"FacturaResource deleteFactura :input:{0}",id);
        FacturaEntity entity = facturaLogic.getFactura(id);
        if(entity ==null)
        {
            throw new WebApplicationException("El recurso /factura/"+id+"no existe.",404);
        }
        facturaLogic.deleteFactura(id, entity);
        //LOGGER.info("FacturaResorce deleteFactura:output:void");
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
    
}
