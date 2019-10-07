/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import java.io.Serializable;

/**
 *
 * @author Santiago Vargas Vega
 */
public class CalificacionDTO implements Serializable{
    private Long id;
    
    private Integer puntaje;
    
    public CalificacionDTO()
    {
        
    }
    
    public CalificacionDTO(CalificacionEntity entity)
    {
        if(entity!=null){
            this.id=entity.getId();
            this.puntaje= entity.getPuntaje();
        }
    }

    public CalificacionEntity toEntity()
    {
        CalificacionEntity entidad = new CalificacionEntity();
        entidad.setId(id);
        entidad.setPuntaje(puntaje);
        return entidad ;
    }
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the puntaje
     */
    public Integer getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }
    
}
