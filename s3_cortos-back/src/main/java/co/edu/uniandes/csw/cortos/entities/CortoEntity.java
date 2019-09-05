/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import co.edu.uniandes.csw.cortos.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Entity
public class CortoEntity extends BaseEntity implements Serializable{
    @PodamExclude
    @OneToMany(mappedBy = "corto")
    private List<ComentarioEntity> comentarios;
    /**
     * nombre del corto
     */
    String nombre;
    /**
     * Fecha de publicacion del corto
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)        
    Date fechaDePublicacion;
    /**
     * Descripcion del corto
     */
    String descripcion;
    /**
     * Precio del corto
     */
    Double precio;
    /**
     * Calificacion promedio del corto
     */
    Double calificacionPromedio;
    /**
     * @return nombre del corto
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Actualiza el nombre del corto, con el nombre recibido por parametro
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * @return la fecha de publicacion del corto
     */
    public Date getFechaDePublicacion() {
        return fechaDePublicacion;
    }
    /**
     * Actualiza la fecha de publicacion del corto, con la fecha de publicacion recibida por parametro
     * @param nombre 
     */
    public void setFechaDePublicacion(Date fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }
    /**
     * @return la descripcion del corto 
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Actualiza la descripcion del corto, con la descripcion recibida por parametro
     * @param nombre 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * @return el precio del corto
     */
    public Double getPrecio() {
        return precio;
    }
    /**
     * Actualiza el precio del corto, con el precio recibido por parametro
     * @param nombre 
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    /**
     * @return calificacion promedio del corto 
     */
    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }
    /**
     * Actualiza la calificacion promedio del corto, con la calificacion recibida por parametro
     * @param nombre 
     */
    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }
}