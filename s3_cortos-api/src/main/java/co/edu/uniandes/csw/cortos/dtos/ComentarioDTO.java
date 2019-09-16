/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.adapters.DateAdapter;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Santiago Vargas Vega 
 */
public class ComentarioDTO implements Serializable {
    private Long id;
    private String comentario;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fecha;
    
    public ComentarioDTO()
    {
        
    }
    public ComentarioDTO(ComentarioEntity comentarioEntity){
        if (comentarioEntity !=null) {
            this.id = comentarioEntity.getId();
            this.comentario=comentarioEntity.getComentario();
            this. fecha =  comentarioEntity.getFecha();
        }
    }
     
    public ComentarioEntity toEntity()
    {
        ComentarioEntity entidad = new ComentarioEntity();
        entidad.setComentario(getComentario());
        entidad.setFecha(getFecha());
        entidad.setId(getId());
        return entidad;
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
     * @return the cuerpo
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the cuerpo to set
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
