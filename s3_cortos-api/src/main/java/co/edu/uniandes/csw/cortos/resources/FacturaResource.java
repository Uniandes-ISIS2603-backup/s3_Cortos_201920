/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.FacturaDTO;
import co.edu.uniandes.csw.cortos.ejb.FacturaLogic;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
 * @author Ingrith Barbosa
 */
@Path("factura")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FacturaResource 
{
    @Inject
    FacturaLogic facturaLogic;
   
}
