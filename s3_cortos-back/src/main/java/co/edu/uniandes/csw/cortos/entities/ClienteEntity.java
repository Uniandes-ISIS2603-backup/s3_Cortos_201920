/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @PodamExclude
    private List<CalificacionEntity> calificaciones = new ArrayList<CalificacionEntity>();
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @PodamExclude
    private List<ComentarioEntity> comentarios = new ArrayList<ComentarioEntity>();

    @OneToMany(mappedBy="cliente",fetch = FetchType.LAZY)
    @PodamExclude
    private List<FacturaEntity> facturas;
    
    @OneToMany(mappedBy="cliente",fetch = FetchType.LAZY)
    @PodamExclude
    private List<FormaDePagoEntity> formasPago;

    public List<FacturaEntity> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaEntity> facturas) {
        this.facturas = facturas;
    }

    public List<FormaDePagoEntity> getFormasPago() {
        return formasPago;
    }

    public void setFormasPago(List<FormaDePagoEntity> formasPago) {
        this.formasPago = formasPago;
    }
    
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
        this.setComentarios(comentarios);
    }

     public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }
    public void addComentario(ComentarioEntity comentario)
    {
        comentarios.add(comentario);
    }
    
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }
    
}
