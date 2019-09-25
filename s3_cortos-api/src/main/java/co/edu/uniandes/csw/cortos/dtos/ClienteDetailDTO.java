/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.entities.CalificacionEntity;
import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import co.edu.uniandes.csw.cortos.entities.ComentarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arturo Rubio Caballero
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable
{
    private List<CalificacionDTO> calificaciones;
    
    private List<ComentarioDTO> comentarios;
    
    public ClienteDetailDTO()
    {
        super();
    }
    
    public ClienteDetailDTO(ClienteEntity c) {
        super(c);
        if (c.getCalificaciones() != null) {
            calificaciones = new ArrayList<>();
            for (CalificacionEntity cal : c.getCalificaciones()) {
                calificaciones.add(new CalificacionDTO(cal));
            }
        }
        if (c.getComentarios() != null) {
            comentarios = new ArrayList<>();
            for (ComentarioEntity comm : c.getComentarios()) {
                comentarios.add(new ComentarioDTO(comm));
            }
        }
    }

    @Override
    public ClienteEntity toEntity(){
        ClienteEntity c = super.toEntity();
        if(comentarios!=null){
            List<ComentarioEntity> comentEnt = new ArrayList<>();
            for( ComentarioDTO coment : getComentarios() )
                comentEnt.add(coment.toEntity());
            c.setComentarios(comentEnt);
        }
        if(calificaciones!=null){
            List<CalificacionEntity> califEnt = new ArrayList<>();
            for( CalificacionDTO calif : getCalificaciones() )
                califEnt.add(calif.toEntity());
            c.setCalificaciones(califEnt);
        }
        return c;
    }

    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }
            
}
