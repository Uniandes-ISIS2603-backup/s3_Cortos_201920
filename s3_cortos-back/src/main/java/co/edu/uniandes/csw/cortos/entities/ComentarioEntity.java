/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import co.edu.uniandes.csw.cortos.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;  
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 *
 * @author Santiago Vargas
 */

@Entity
public class ComentarioEntity extends BaseEntity implements Serializable{
    
    private String comentario;
    
   @Temporal(TemporalType.DATE)
   @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    
    

     @PodamExclude
     @ManyToOne
     private CortoEntity corto;

    @PodamExclude
    @ManyToOne (fetch=FetchType.EAGER)
    private ClienteEntity cliente;
 
    
    @PodamExclude
    @OneToOne 
    private ComentarioEntity siguiente;
    /**
     * @return the comentario
     */
   
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        
        this.comentario = comentario;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
     * @return the siguiente
     */
    public ComentarioEntity getSiguiente() {
        return siguiente;
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(ComentarioEntity siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public String toString() {
        return "ComentarioEntity{" + "comentario=" + comentario + ", fecha=" + fecha + '}';
    }
    
}
