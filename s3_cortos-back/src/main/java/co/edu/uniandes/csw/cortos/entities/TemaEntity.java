/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Vargas  Vega
 */
@Entity
public class TemaEntity extends BaseEntity implements Serializable{
    
    /**
     * Nombre del tema
     */
    private String nombre;
    /**
     * relacion con los cortos que tienen temas de este tipo
     */
    @PodamExclude
    @ManyToMany (mappedBy = "temas")
    private List<CortoEntity> cortos = new ArrayList<> ();
    /**
     * Relacion con los cineastas que tengan 
     */
    @PodamExclude
    @ManyToMany(mappedBy = "temas")

    private List<CineastaEntity> cineasta = new ArrayList<>();
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

    /**
     * @return the cortos
     */
    public List<CortoEntity> getCortos() {
        return cortos;
    }

    /**
     * @param cortos the cortos to set
     */
    public void setCortos(List<CortoEntity> cortos) {
        this.cortos = cortos;
    }

    /**
     * @return the cineasta
     */
    public List<CineastaEntity> getCineasta() {
        return cineasta;
    }

    /**
     * @param cineasta the cineasta to set
     */
    public void setCineasta(List<CineastaEntity> cineasta) {
        this.cineasta = cineasta;
    }

    
}
