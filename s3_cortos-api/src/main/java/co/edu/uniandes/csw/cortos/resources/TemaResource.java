/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.TemaDTO;
import co.edu.uniandes.csw.cortos.dtos.TemaDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.TemaLogic;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
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
 * @author Santiago Vargas Vega
 */

@Path("temas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TemaResource {
    private static final Logger LOGGER = Logger.getLogger(TemaResource.class.getName ());
    private static final String NO = " no existe";
    private static final String REC = " el recurso /temas/";
    @Inject 
    private TemaLogic temaLogic;
    
    @POST
    public TemaDTO createTema(TemaDTO tema) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "TemaResource createTema : input : {0}",tema);
        TemaDTO nuevoTemaDTO  = new TemaDTO (temaLogic.createTema(tema.toEntity()));
        LOGGER.log(Level.INFO, "TemaResource createTema : output : {0}",  nuevoTemaDTO);
        return nuevoTemaDTO;
    }
    @GET
    public List<TemaDetailDTO> getTemas()
    {
        LOGGER.info("TemaResource getTemas : input : void");
        List<TemaDetailDTO> listaTemas = listEntity2DTO (temaLogic.getTemas());
        LOGGER.log(Level.INFO, "TemaResource getTEmas : output {0}", listaTemas);
        return listaTemas;
    }
    @GET
    @Path ("{temaId:\\d+}")
    public TemaDetailDTO getTema(@PathParam("temaId") long id )
    {
        LOGGER.log(Level.INFO, "TemaResource getTema: input : {0}", id);
        TemaEntity temaEntity = temaLogic.getTema(id);
        if (temaEntity ==null)
        {
            throw new WebApplicationException(REC+ id+NO, 404);       
        }
        
       return new TemaDetailDTO(temaEntity);
        
    }
    
    @PUT
    @Path ("{temaId:\\d+}")
    public TemaDTO updateTema (@PathParam ("temaId") long id , TemaDTO tema ) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "TemaResource updateTema:input:id{0},tema:{1}", new Object[]{id,tema});
        tema.setId(id);
        if(temaLogic.getTema(id)==null)
        {
             throw new WebApplicationException(REC+id+NO,404);
        }
        TemaDTO temaDTO = new TemaDTO (temaLogic.updateTema(id, tema.toEntity()));
        LOGGER.log(Level.INFO, "TemaResource updateTemaResource:output:{0}", temaDTO);
        return temaDTO;
    }
    
    @DELETE
    @Path("{temaId:\\d+}")
    public void deleteTema (@PathParam  ("temaId") Long id ) throws BusinessLogicException
    {
        LOGGER.log (Level.INFO,"TemaResource deleteTema :input : {0}", id );
        TemaEntity entity = temaLogic.getTema(id);
        if(entity==null)
        {
            throw new WebApplicationException(REC+id+NO,404);
        }
        temaLogic.deleteTema(id);
        LOGGER.info("TemaResource deleteTema:output:void");
    }
    
    private List<TemaDetailDTO> listEntity2DTO (List<TemaEntity> entityList)
    {
        List<TemaDetailDTO> list = new ArrayList<>();
        for(TemaEntity entity :entityList)
        {
            list.add(new TemaDetailDTO(entity));
        }
        return list;
    }
}
