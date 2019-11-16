/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.resources;

import co.edu.uniandes.csw.cortos.dtos.CineastaDTO;
import co.edu.uniandes.csw.cortos.dtos.CineastaDetailDTO;
import co.edu.uniandes.csw.cortos.ejb.CineastaLogic;
import co.edu.uniandes.csw.cortos.ejb.CortoCineastasLogic;
import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
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
public class CortoCineastasResource {
    /**
     * Referencia de la logica de la relacion entre corto y cineasta
     */
    @Inject
    private CortoCineastasLogic cortoCineastasLogic;
    /**
     * Referencia de la logica del cineasta
     */
    @Inject
    private CineastaLogic cineastaLogic;
    
    /**
     * Servicio RESFTUL de aniadir un cineasta a un corto
     * @param cortoId identificacion del corto
     * @param cineastaId identificacion del cineasta
     * @return cineasta que se aniade
     */
    @POST
    @Path("{cineastaId : \\d+}")
    public CineastaDTO addCineasta(@PathParam("cortoId") Long cortoId, @PathParam("cineastaId") Long cineastaId){
        if(cineastaLogic.getCineasta(cineastaId)== null){
            throw new WebApplicationException("El recurso /cineasta/"+cineastaId+" no existe", 404);
        }
        CineastaDTO c = new CineastaDTO(cortoCineastasLogic.addCineasta(cineastaId, cortoId));
        return c;
    }
    /**
     * Lista de cineastas de un corto
     * @param cortoId identificacion del corto
     * @return Lista de cineastas de un corto
     */
    @GET
    public List<CineastaDetailDTO> getCineastas(@PathParam("cortosId") Long cortoId){
        return cineastasListEntity2DTO(cortoCineastasLogic.getCineastas(cortoId));
    }
    /**
     *Metodo auxiliar para transformar entidades a dtos
     * @param lista lista de entidades de cineastas
     * @return lista de cineasta dto
     */
    public List<CineastaDetailDTO> cineastasListEntity2DTO(List<CineastaEntity> lista){
        List<CineastaDetailDTO> list= new ArrayList<>();
        for(CineastaEntity c : lista){
            list.add(new CineastaDetailDTO(c));
        }
        return list;
    }
    /**
     * Cineasta particular de un corto
     * @param cortoId identificacion de corto
     * @param cineastaId identificacion de cineastas
     * @return cineasta de un corto
     * @throws BusinessLogicException el cineasta no pertenece al corto
     */
    @GET
    @Path("cineastaId: \\d+")
    public CineastaDetailDTO getCineasta(@PathParam("cortosId")Long cortoId, @PathParam("cineastaId") Long cineastaId) throws BusinessLogicException{
        if((cineastaLogic.getCineasta(cineastaId))== null)
            throw new WebApplicationException("El recurso /cortos/"+ cortoId+"/cineastas/"+cineastaId+" no existe", 404);
        CineastaDetailDTO cineasta = new CineastaDetailDTO(cortoCineastasLogic.getCineasta(cineastaId, cortoId));
        return cineasta;
    }
}
