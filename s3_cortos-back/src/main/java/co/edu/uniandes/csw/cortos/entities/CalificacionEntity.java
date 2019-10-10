/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamIntValue;

/**
 *
 * @author Santiago Vargas
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    
    
    /**
     * Relacion con la entidad cliente
     */
    @PodamExclude
    @ManyToOne (fetch=FetchType.EAGER)
    private ClienteEntity cliente;
    /**
     * Relacion con la entidad corto 
     */
    @PodamExclude
    @ManyToOne (fetch=FetchType.EAGER)
    private CortoEntity corto;
    /**
     * Variable que representa el puntaje 
     */
    @PodamIntValue(minValue=0, maxValue=5)
    private Integer puntaje;

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

    /**
     * @return the cliente
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the corto
     */
    public CortoEntity getCorto() {
        return corto;
    }

    /**
     * @param corto the corto to set
     */
    public void setCorto(CortoEntity corto) {
        this.corto = corto;
    }

    @Override
    public String toString() {
        return "CalificacionEntity{" + "puntaje=" + puntaje + '}';
    }

   
}

