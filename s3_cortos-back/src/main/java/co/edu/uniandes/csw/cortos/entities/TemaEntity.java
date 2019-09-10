/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Vargas  Vega
 */
@Entity
public class TemaEntity extends BaseEntity implements Serializable{
    private String nombre;
    
    @PodamExclude
    @ManyToOne
    private CortoEntity corto;
    
    @PodamExclude
    @ManyToOne
    private CineastaEntity cineasta;
     /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    public void setCorto(CortoEntity corto) {
        this.corto = corto;
    }

    public CortoEntity getCorto() {
        return corto;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cineasta
     */
    public CineastaEntity getCineasta() {
        return cineasta;
    }

    /**
     * @param cineasta the cineasta to set
     */
    public void setCineasta(CineastaEntity cineasta) {
        this.cineasta = cineasta;
    }
    
}
