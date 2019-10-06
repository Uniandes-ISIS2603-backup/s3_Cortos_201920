/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import java.io.Serializable;

/**
 *
 * @author Estudiante
 */
public class TemaDTO extends CascaraDTO implements Serializable{
    
    private Long id;
    
    private String nombre;
     
      public TemaDTO() {

    }

    public TemaDTO(TemaEntity temaEntity) {
        if (temaEntity != null) {
            
            this.id = temaEntity.getId();
            this.nombre = temaEntity.getNombre();
        }
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public TemaEntity toEntity() {
       
        TemaEntity temaEntity = new TemaEntity();
        temaEntity.setId(this.getId());
        temaEntity.setNombre(this.getNombre());

        return temaEntity;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
