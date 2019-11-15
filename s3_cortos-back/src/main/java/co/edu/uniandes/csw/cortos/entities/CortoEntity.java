/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import co.edu.uniandes.csw.cortos.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class CortoEntity extends BaseEntity implements Serializable {

    /**
     * Relacion entre comentarios y corto
     */
    @PodamExclude
    @OneToMany(mappedBy = "corto")
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    /**
     * Relacion entre calificaciones y cortos
     */
    @PodamExclude
    @OneToMany(mappedBy = "corto")

    private List<CalificacionEntity> calificaciones = new ArrayList<CalificacionEntity>();
    



    @PodamExclude
    @ManyToOne
    private FacturaEntity factura;

    @PodamExclude
    @ManyToMany
    private List<TemaEntity> temas = new ArrayList<>();

    /**
     * Asociación con la clase CineastaEntity,que describe el cineasta productor
     * del corto.
     */
    @PodamExclude
    @ManyToOne
    private CineastaEntity productor;

    /**
     * Asociación con la clase CineastaEntity,que describe los cineastas
     * asociados del corto.
     */
    @PodamExclude
    @OneToMany(mappedBy = "cortoCineastas")
    private List<CineastaEntity> cineasta = new ArrayList<>();

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
     *
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
     * Actualiza la fecha de publicacion del corto, con la fecha de publicacion
     * recibida por parametro
     *
     * @param nombre
     */
    public void setFechaDePublicacion(Date fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    /**
     * Actualiza la descripcion del corto, con la descripcion recibida por
     * parametro
     *
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
     * @return la descripcion del corto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Actualiza el precio del corto, con el precio recibido por parametro
     *
     * @param nombre
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Actualiza la calificacion promedio del corto, con la calificacion
     * recibida por parametro
     *
     * @param nombre
     */
    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    /**
     * @return calificacion promedio del corto
     */
    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the calificaciones
     */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the temas
     */
    public List<TemaEntity> getTemas() {
        return temas;
    }

    /**
     * @param temas the temas to set
     */
    public void setTemas(List<TemaEntity> temas) {
        this.temas = temas;
    }

    /**
     * @return the productor
     */
    public CineastaEntity getProductor() {
        return productor;
    }

    /**
     * @param productor the productor to set
     */
    public void setProductor(CineastaEntity productor) {
        this.productor = productor;
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

    public FacturaEntity getFactura() {
        return factura;
    }

    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }

}
