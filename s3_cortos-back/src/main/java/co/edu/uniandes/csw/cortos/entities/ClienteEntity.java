/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Arturo Rubio
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable 
{
    private String nombre;
    
    private String correo;
    
    private String contrasenia;
//    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
//    @PodamExclude
//    private Collection<CalificacionEntity> calificaciones;

//    public Collection<CalificacionEntity> getCalificaciones() {
//        return calificaciones;
//    }
//
//    public void setCalificaciones(Collection<CalificacionEntity> calificaciones) {
//        this.calificaciones = calificaciones;
//    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setNombre(String pNombre)
    {
        this.nombre = pNombre;
    }
    
    public String getCorreo()
    {
        return correo;
    }
    
    public void setCorreo(String pCorreo)
    {
        this.correo = pCorreo;
    }
    
    public String getContrasenia()
    {
        return contrasenia;
    }
    
    public void setContrasenia(String pContrasenia)
    {
        this.contrasenia = pContrasenia;
    }
}
