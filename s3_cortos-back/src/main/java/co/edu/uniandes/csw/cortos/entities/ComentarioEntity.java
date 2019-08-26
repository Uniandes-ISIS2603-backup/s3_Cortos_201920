/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Vargas
 */

@Entity
public class ComentarioEntity extends BaseEntity implements Serializable{
    private String comentario;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    
    @PodamExclude
    @ManyToOne (fetch=FetchType.EAGER)
    
    private CortoEntity corto;
    
    @PodamExclude
    @ManyToOne (fetch=FetchType.EAGER)
    private ClienteEntity cliente;
    
    @PodamExclude
    @OneToOne (mappedBy="comentario",fetch=FetchType.EAGER)
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
}
