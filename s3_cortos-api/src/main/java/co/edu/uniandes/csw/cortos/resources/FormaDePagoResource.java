/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.FormaDePagoDTO;
import co.edu.uniandes.csw.cortos.ejb.FormaDePagoLogic;
import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Juan Felipe Mejia
 */
@Path("formaDePago")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped 
public class FormaDePagoResource {
    
    @Inject
    private FormaDePagoLogic formaDePagoLogic;
    
    private static final Logger LOGGER = Logger.getLogger(FormaDePagoResource.class.getName());
    
    @POST
    public FormaDePagoDTO createFormaDePago(FormaDePagoDTO formaDePago)throws BusinessLogicException
    {
        FormaDePagoEntity formaDePagoEntity = formaDePago.toEntity();
        FormaDePagoEntity nuevaFormaDePagoEntity = formaDePagoLogic.createFormaDePago(formaDePagoEntity);
        FormaDePagoDTO nuevaFormaDePagoDTO = new FormaDePagoDTO(nuevaFormaDePagoEntity);
        LOGGER.log(Level.INFO, "ClienteResource createCliente: output: {0}", nuevaFormaDePagoDTO);
        return nuevaFormaDePagoDTO; 
    }
    
    @GET
    public List<FormaDePagoDTO> getFormasDePago()
    {
        LOGGER.info("FormaDePagoResource getFormasDePago :input :void");
        List<FormaDePagoDTO> listaFormas = listEntity2DTO(formaDePagoLogic.getFormasDePago());
        LOGGER.log(Level.INFO,"FormaDePagoResource getFormasDePago :output{0}",listaFormas);
        return listaFormas;
    }
    
    @GET
    @Path("{formaDePagoId: \\d+}")
    public FormaDePagoDTO getFormaDePago(@PathParam("formaDePagoId") Long id)
    {
        LOGGER.log(Level.INFO,"FormaDePagoResource getFormaDePago :input:{0}", id);
        FormaDePagoEntity formaDePagoEntity = formaDePagoLogic.getFormaDePago(id);
        if(formaDePagoEntity==null)
        {
            throw new WebApplicationException("El recurso /formaDePago/"+id+"no existe.",404);
        }
        FormaDePagoDTO formaDePagoDTO= new FormaDePagoDTO(formaDePagoEntity);
        return formaDePagoDTO;
    }
    
    @PUT
    @Path("{formaDePagoId:\\d+}")
    public FormaDePagoDTO updateFormaDePago(@PathParam("formaDePagoId") Long id,FormaDePagoDTO formaDePago) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"FormaDePagoResource updateFormaDePago:input:id:{0},formaDePago:{1}",new Object[]{id,formaDePago});
        formaDePago.setNumero(id);
        if(formaDePagoLogic.getFormaDePago(id)==null)
        {
            throw new WebApplicationException("El recurso /formaDePago/"+id+"no existe.",404);
        }
        FormaDePagoDTO formaDePagoDTO= new FormaDePagoDTO(formaDePagoLogic.updateFormaDePago(id, formaDePago.toEntity()));
        LOGGER.log(Level.INFO,"FormaDePagoResorce updateFormaDePago:output:{0}",formaDePagoDTO);
        return formaDePagoDTO;
    }
    
    @DELETE
    @Path("{formaDePagoId:\\d+}")
    public void deleteFormaDePago(@PathParam("formaDePagoId") Long id) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"FormaDePagoResource deleteFormaDePago:input:{0}", id);
        FormaDePagoEntity entity = formaDePagoLogic.getFormaDePago(id);
        if(entity==null){
            throw new WebApplicationException("El recurso /formaDePago/ "+ id + "no existe.",404);
        }
        formaDePagoLogic.deleteFormaDePago(id);
        LOGGER.info("FormaDePagoResorce deleteFormaDePago :output:void");
    }
    
     private List<FormaDePagoDTO> listEntity2DTO(List <FormaDePagoEntity> entityList)
    {
        List<FormaDePagoDTO> list = new ArrayList<>();
        for (FormaDePagoEntity entity:entityList) {
            list.add(new FormaDePagoDTO(entity));
        }
        return list;
    }
    
}
