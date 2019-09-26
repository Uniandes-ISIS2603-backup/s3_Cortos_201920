/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.entities.ClienteEntity;
import java.io.Serializable;

/**
 *
 * @author Arturo Rubio Caballero
 */
public class ClienteDTO implements Serializable 
{
    private Long id;
    
    private String nombre;
    
    private String correo;
    
    private String contrasenia;
    
    public ClienteDTO()
    {
        
    }
    
    public ClienteDTO(ClienteEntity clienteEntity)
    {
        if (clienteEntity != null) {
            this.id = clienteEntity.getId();
            this.nombre = clienteEntity.getNombre();
            this.correo = clienteEntity.getCorreo();
            this.contrasenia = clienteEntity.getContrasenia();
        }
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.id);
        clienteEntity.setNombre(this.nombre);
        clienteEntity.setContrasenia(this.contrasenia);
        clienteEntity.setCorreo(this.correo);
        return clienteEntity;
    }
}
