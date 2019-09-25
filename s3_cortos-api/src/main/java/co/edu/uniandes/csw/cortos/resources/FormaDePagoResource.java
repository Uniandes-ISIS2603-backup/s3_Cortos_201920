/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.FormaDePagoDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Juan Felipe Mejia
 */
@Path("formaDePago")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped 
public class FormaDePagoResource {
    
    private static final Logger LOGGER = Logger.getLogger(FormaDePagoResource.class.getName());
    
    @POST
    public FormaDePagoDTO createFormaDePago(FormaDePagoDTO formaDePago)
    {
       return formaDePago; 
    }
    
    @GET
    public FormaDePagoDTO getFormaDePago(FormaDePagoDTO formaDePago)
    {
        return formaDePago;
    }
    
    @PUT
    public FormaDePagoDTO putFormaDePago(FormaDePagoDTO formaDePago)
    {
        return formaDePago;
    }
    
    @DELETE
    public FormaDePagoDTO DeleteFormaDePago(FormaDePagoDTO formaDePago)
    {
        return formaDePago;
    }
    
}
