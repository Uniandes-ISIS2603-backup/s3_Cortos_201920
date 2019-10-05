/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CortoDTO;
import co.edu.uniandes.csw.cortos.dtos.CortoDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.CortoLogic;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 * @author Juan Sebastian Gomez
 */
@Path("cortos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CortoResource {
    @Inject
    private CortoLogic cl;
    
    @POST
    public CortoDTO createCorto(CortoDTO c) throws BusinessLogicException{
        CortoDTO nuevo = new CortoDTO(cl.createCorto(c.toEntity()));
        return nuevo;
    }
    
    @GET
    public List<CortoDetailDTO> getCortos(){
        List<CortoDetailDTO> lista = listEntity2Entity(cl.getCortos());
        return lista;
    }
    
    @GET
    @Path("{cortosId: \\d+}")
    public CortoDetailDTO getCorto(@PathParam("cortosId") Long cortoId){
        CortoEntity c = cl.getCorto(cortoId);
        if(c == null)
            throw new WebApplicationException("el recurso /books/" + cortoId + " no existe", 404);
        CortoDetailDTO n = new CortoDetailDTO(c);
        return n;
    }
    
    @PUT
    @Path("{cortosId: \\d+}")
    public CortoDetailDTO updateCorto(@PathParam("cortosId") Long cortoId, CortoDetailDTO c) throws BusinessLogicException{
        c.setId(cortoId);
        if(cl.getCorto(cortoId)== null)
            throw new WebApplicationException("el recurso /books/" + cortoId + " no existe", 404);
        CortoDetailDTO n = new CortoDetailDTO(cl.updateCorto(cortoId, c.toEntity()));
        return n;
    }
    
    
            
    public List<CortoDetailDTO> listEntity2Entity(List<CortoEntity> l){
        List<CortoDetailDTO> lista = new ArrayList<>();
        for(CortoEntity c : l)
            lista.add(new CortoDetailDTO(c));
        return lista;
    }
}

