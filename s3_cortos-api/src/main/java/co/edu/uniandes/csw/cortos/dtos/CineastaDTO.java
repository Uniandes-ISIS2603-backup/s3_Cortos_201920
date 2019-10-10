/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.adapters.DateAdapter;
import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Pedro Callejas
 */
public class CineastaDTO implements Serializable {

    /**
     * id cineasta
     */
    private Long id;

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
    @XmlJavaTypeAdapter(DateAdapter.class)
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
    private CineastaEntity.Tipo rol;

    public CineastaDTO() {

    }

    public CineastaDTO(CineastaEntity cineastaEntity) {
        if (cineastaEntity != null) {
            this.id = cineastaEntity.getId();
            this.nombre = cineastaEntity.getNombre();
            this.correo = cineastaEntity.getCorreo();
            this.contrasenia = cineastaEntity.getContrasenia();
            this.fechaNacimiento = cineastaEntity.getFechaNacimiento();
            this.telefono = cineastaEntity.getTelefono();
            this.direccion = cineastaEntity.getDireccion();
            this.genero = cineastaEntity.getGenero();
            this.rol = cineastaEntity.getRol();
        }
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CineastaEntity toEntity() {
       
        CineastaEntity cineastaEntity = new CineastaEntity();
        cineastaEntity.setId(this.getId());
        cineastaEntity.setNombre(this.getNombre());
        cineastaEntity.setCorreo(this.getCorreo());
        cineastaEntity.setContrasenia(this.getContrasenia());
        cineastaEntity.setFechaNacimiento(this.getFechaNacimiento());
        cineastaEntity.setTelefono(this.getTelefono());
        cineastaEntity.setDireccion(this.getDireccion());
        cineastaEntity.setGenero(this.getGenero());
        cineastaEntity.setRol(this.getRol());

        return cineastaEntity;
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

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the genero
     */
    public Boolean getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    /**
     * @return the rol
     */
    public CineastaEntity.Tipo getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(CineastaEntity.Tipo rol) {
        this.rol = rol;
    }
  
}
