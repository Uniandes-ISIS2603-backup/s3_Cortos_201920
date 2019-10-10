/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CineastaDetailDTO;
import co.edu.uniandes.csw.cortos.dtos.CortoDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.CineastaLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoCineastaProductorLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
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
public class CortoCineastaProductorResource {
    @Inject
    private CortoLogic cl;
    @Inject
    private CortoCineastaProductorLogic ccl;
    @Inject
    private CineastaLogic cineLogic;
    
    @PUT
    public CortoDetailDTO replaceProductor(@PathParam("cortosId") Long cortoId, CineastaDetailDTO cineasta){
        if(cl.getCorto(cortoId) == null )
            throw new WebApplicationException("No existe  el recurso corto con id "+cortoId, 404);
        if(cineLogic.getCineasta(cineasta.getId())== null)
            throw new WebApplicationException("No existe el recurso cineasta con id "+cineasta.getId());
        
        CortoDetailDTO c = new CortoDetailDTO(ccl.replaceProductor(cortoId, cineasta.getId()));
        return c;
    }
}
