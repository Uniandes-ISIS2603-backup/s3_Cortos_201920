/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;


import co.edu.uniandes.csw.cortos.dtos.CortoDetailDTO;
import co.edu.uniandes.csw.cortos.dtos.FacturaDTO;
import co.edu.uniandes.csw.cortos.ejb.CortoFacturaLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.ejb.FacturaLogic;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
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
public class CortoFacturaResource {
    @Inject
    private CortoLogic cl;
    @Inject
    private CortoFacturaLogic cfl;
    @Inject
    private FacturaLogic fl;
    
    @PUT
    public CortoDetailDTO replaceFactura(@PathParam("cortosId")Long cortosId, FacturaDTO factura){
        if(cl.getCorto(cortosId)==null)
            throw new WebApplicationException("El recurso corto con id "+cortosId, 404);
        if(fl.getFactura(factura.getId())==null)
            throw new WebApplicationException("El recurso factura con id "+factura.getId());
        CortoDetailDTO c = new CortoDetailDTO(cfl.replaceFactura(cortosId, factura.getId()));
        return c;
    }
}
