/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CineastaDTO;
import co.edu.uniandes.csw.cortos.ejb.CineastaLogic;
import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
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
 * @author Pedro Callejas
 */
@Path("cineasta")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CineastaResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());
    
    @Inject
    private CineastaLogic cineastaLogic;
    @POST
    public CineastaDTO createCineasta(CineastaDTO cliente)throws BusinessLogicException
    {
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CineastaEntity cineastaEntity = cliente.toEntity();
        // Invoca la lógica para crear la editorial nueva
        CineastaEntity nuevoCineastaEntity = cineastaLogic.createCineasta(cineastaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CineastaDTO nuevoCineastaDTO = new CineastaDTO(nuevoCineastaEntity);
        LOGGER.log(Level.INFO, "CineastasResource createCineasta: output: {0}", nuevoCineastaDTO);
        return nuevoCineastaDTO;
    }
    
    @GET
    public List<CineastaDTO> getCineastas()
    {
        LOGGER.info("CineastaResource getCineastas :input : void");
        List<CineastaDTO> listaCineastas = listEntity2DTO(cineastaLogic.getCineastas());
        LOGGER.log(Level.INFO,"CineastaResource getCineastas :output{0}", listaCineastas);
        return listaCineastas;
    }
    
    @GET
    @Path("{cineastaId:\\d+}")
    public CineastaDTO getCliente(@PathParam("cineastaId") long id )
    {
        LOGGER.log(Level.INFO,"CineastaResource getCineastas :input : {0}",id);
        CineastaEntity cineastaEntity = cineastaLogic.getCineasta(id);
        if(cineastaEntity ==null)
        {
            throw new WebApplicationException("El recurso /cineasta/"+ id+"no existe.", 404);
        }
        CineastaDTO cineastaDTO = new CineastaDTO(cineastaEntity);
        return cineastaDTO;
    }
    
    @PUT
    @Path("{cineastaId:\\d+}")
    public CineastaDTO updateCliente(@PathParam("cineastaId") Long id, CineastaDTO cineasta) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"CineastaResource updateCineasta:input:id:{0},cineasta:{1}",new Object[]{id,cineasta});
        cineasta.setId(id);
        if(cineastaLogic.getCineasta(id)==null)
        {
            throw new WebApplicationException("El recurso /cineasta/"+id+"no existe.",404);
        }
        CineastaDTO cineastaDTO = new CineastaDTO(cineastaLogic.updateCineasta(id,cineasta.toEntity()));
        LOGGER.log(Level.INFO,"CineastaResource updateCineastaResource:outpur:{0}",cineastaDTO);
        return cineastaDTO;
    }
    
    @DELETE
    @Path("{cineastaId:\\d+}")
    public void deleteCliente(@PathParam("cineastaId") Long id ) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"CineastaResource deleteCineasta :input:{0}",id);
        CineastaEntity entity = cineastaLogic.getCineasta(id);
        if(entity ==null)
        {
            throw new WebApplicationException("El recurso /cineasta/"+id+"no existe.",404);
        }
        cineastaLogic.deleteCineasta(id);
        LOGGER.info("CineastaResorce deleteCineasta:output:void");
    }
    
    private List<CineastaDTO> listEntity2DTO(List<CineastaEntity> entityList)
    {
        List<CineastaDTO> list = new ArrayList<>();
        for(CineastaEntity entity:entityList){
            list.add(new CineastaDTO(entity));
        }
        return list;
    }
}
