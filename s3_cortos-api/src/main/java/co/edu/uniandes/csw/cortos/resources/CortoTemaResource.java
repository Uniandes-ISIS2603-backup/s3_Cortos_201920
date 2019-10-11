/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.TemaDTO;
import co.edu.uniandes.csw.cortos.dtos.TemaDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoTemaLogic;
import co.edu.uniandes.csw.cortos.ejb.TemaLogic;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
public class CortoTemaResource {
    /**
     * Referencia a la logica del corto
     */
    @Inject
    private CortoLogic cl;
    
    /**
     * Referencia a la logica de corto y tema
     */
    @Inject
    private CortoTemaLogic ctl;
    
    /**
     * Referencia a la logica del tema
     */
    @Inject
    private TemaLogic tl;
    
    
    /**
     * Servicio que crea un tema para un cort
     * @param cortoId identificacion del corto
     * @param temaId identificacion del tema
     * @return tema agregado
     */
    @POST
    @Path("{temaId: \\ d+}")
    public TemaDTO addTema(@PathParam("cortoId")Long cortoId, @PathParam("temaId")Long temaId){
        if(tl.getTema(temaId)== null)
            throw new WebApplicationException("no existe el recurso tema con el id "+ temaId, 404);
        if(cl.getCorto(cortoId) == null)
            throw new WebApplicationException("no existe el recurso corto con el id "+ cortoId, 404);
        TemaDTO t = new TemaDTO(ctl.addTema(temaId, cortoId));
        return t;
    }
    
    /**
     * Funcion auxiliar para transformar entidades a dto en listas
     * @param lista lista de entidades
     * @return lista de dtos
     */
    private List<TemaDetailDTO> temasListEntity2DTO(List<TemaEntity> lista){
        List<TemaDetailDTO> r = new ArrayList<>();
        for(TemaEntity t : lista){
            r.add(new TemaDetailDTO(t));
        }
        return r;
    }
    
    /**
     * Servicio para obtener todos los temas de un corto
     * @param cortoId identificacion de un corto
     * @return lista de temas de un corto
     */
    @GET
    public List<TemaDetailDTO> getTemas(@PathParam("cortosId") Long cortoId){
        return temasListEntity2DTO(ctl.getTemas(cortoId));
    }
    
    /**
     * Retorna un tema especifico de un corto especifico 
     * @param cortoId identificacion de un corto
     * @param temaId identificacion de un tema 
     * @return tema 
     * @throws BusinessLogicException 
     */
    @GET
    public TemaDetailDTO getTema(@PathParam("cortosId")Long cortoId, @PathParam("temaId")Long temaId) throws BusinessLogicException{
        if(tl.getTema(temaId)== null)
            throw new WebApplicationException("no existe el recurso tema con el id "+ temaId, 404);
        if(cl.getCorto(cortoId) == null)
            throw new WebApplicationException("no existe el recurso corto con el id "+ cortoId, 404);
        TemaDetailDTO t = new TemaDetailDTO(ctl.getTema(temaId, cortoId));
        return t;
    }
}
