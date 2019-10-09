/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.ejb.FacturaLogic;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;



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
   
}
