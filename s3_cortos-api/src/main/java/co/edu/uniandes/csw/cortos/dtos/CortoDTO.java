/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.adapters.DateAdapter;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Juan Sebastian Gomez
 */
public class CortoDTO implements Serializable{
    
    private Long id;
    
    private String nombre;
   
   private String descripcion;
   
   private Double precio;
   
   private Double calificacionPromedio;
   
   @XmlJavaTypeAdapter(DateAdapter.class)
   private Date fechaDePublicacion;

    public CortoDTO() {
    }
   
   public CortoDTO(CortoEntity c){
       if(c!=null){
         this.id = c.getId();
         this.nombre = c.getNombre();
         this.descripcion = c. getDescripcion();
         this.calificacionPromedio = c.getCalificacionPromedio();
         this.precio = c.getPrecio();
         this.fechaDePublicacion = c.getFechaDePublicacion();
       }
   }
   
   public CortoEntity toEntity(){
       CortoEntity c = new CortoEntity();
       c.setCalificacionPromedio(this.calificacionPromedio);
       c.setPrecio(this.precio);
       c.setNombre(this.nombre);
       c.setDescripcion(this.descripcion);
       c.setFechaDePublicacion(this.fechaDePublicacion);
       c.setId(this.id);
       return c;
   }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Date getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public void setFechaDePublicacion(Date fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }
   
}
