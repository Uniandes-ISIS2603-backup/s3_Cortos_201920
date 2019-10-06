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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Pedro Luis Callejas Pinzon
 */
@Entity
public class CineastaEntity extends BaseEntity implements Serializable {

    /**
     * enumeracion que modela el tipo de cineasta{aficionado,profesional}.
     */
    public enum Tipo {
        AFICIONADO,
        PROFESIONAL
    }

    /**
     * Asociación con la claseTemaEntity, que describe los temas de interes del
     * cineasta.
     */
    @PodamExclude
    @OneToMany
    private List<TemaEntity> temas;

    /**
     * Asociación con la clase CortosEntity, que describe el corto producido por
     * el cineasta.
     */
    @PodamExclude
    @OneToMany(
            mappedBy = "productor",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<CortoEntity> cortos = new ArrayList<CortoEntity>();

    /**
     * Asociación con la clase CineastaEntity, describe el corto asociado a los
     * cineastas "directores".
     */
    @PodamExclude
    @ManyToOne
    private CortoEntity cortoCineastas;

    /**
     * nombre cineasta
     */
    private String nombre;

    /**
     * correo asociado al cineasta
     */
    private String correo;

    /**
     * contrasenia del correo del cineasta
     */
    private String contrasenia;

    /**
     * fecha de nacimiento del cineasta
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaNacimiento;

    /**
     * telefono del cineasta
     */
    private String telefono;

    /**
     * direccion del domicilio del cineasta
     */
    private String direccion;

    /**
     * genero del cineasta
     */
    private Boolean genero;

    /**
     * tipo de cineasta {aficionado,profesional}
     */
    private Tipo rol;

    /**
     * @return nombre del cineasta
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre nombre por el que se va a cambiar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return correo del cineasta
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo correo por el que se va a cambiar
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return contrasenia del correo del cineasta
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia contrasenia nueva
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return fecha de nacimiento del cineasta
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento fecha de nacimiento nueva
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return telefono del cineasta
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono telefono nuevo
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return direccion del cineasta
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion direccion nueva
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return genero
     */
    public Boolean getGenero() {
        return genero;
    }

    /**
     * @param genero genero por el que va a ser cambiado
     */
    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    /**
     * @return rol del cineasta
     */
    public Tipo getRol() {
        return rol;
    }

    /**
     * @param rol rol nuevo del cineasta
     */
    public void setRol(Tipo rol) {
        this.rol = rol;
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
     * @return the cortoCineastas
     */
    public CortoEntity getCortoCineastas() {
        return cortoCineastas;
    }

    /**
     * @param cortoCineastas the cortoCineastas to set
     */
    public void setCortoCineastas(CortoEntity cortoCineastas) {
        this.cortoCineastas = cortoCineastas;
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

}
