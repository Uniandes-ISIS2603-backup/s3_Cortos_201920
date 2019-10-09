/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ingrith Barbosa
 */
public class FacturaDetailDTO extends FacturaDTO implements Serializable
{
    // relación  uno o muchos libros
    private List<CortoDTO> cortos;
    
    public FacturaDetailDTO()
    {
        super();
    }
}
