/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    @ManyToMany
    private ArrayList<CortoEntity> cortos = new ArrayList<CortoEntity> ();
    /**
     * Relacion con los cineastas que tengan 
     */
    @PodamExclude
    @ManyToMany
    private ArrayList<CineastaEntity> cineasta = new ArrayList<CineastaEntity>();
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
    public ArrayList<CortoEntity> getCortos() {
        return cortos;
    }

    /**
     * @param cortos the cortos to set
     */
    public void setCortos(ArrayList<CortoEntity> cortos) {
        this.cortos = cortos;
    }

    /**
     * @return the cineasta
     */
    public ArrayList<CineastaEntity> getCineasta() {
        return cineasta;
    }

    /**
     * @param cineasta the cineasta to set
     */
    public void setCineasta(ArrayList<CineastaEntity> cineasta) {
        this.cineasta = cineasta;
    }

    
}
