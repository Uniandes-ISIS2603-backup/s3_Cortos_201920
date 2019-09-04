/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoLogic {
    @Inject
    private CortoPersistence persistencia;
    
    public CortoEntity createCorto(CortoEntity c)throws BusinessLogicException{
        if(c.getNombre()==null)
            throw new BusinessLogicException("No escribio ningun nombre");
        if(c.getDescripcion() == null)
            throw new BusinessLogicException("No hay ninguna descripcion");
        if(c.getPrecio() == null)
            throw new BusinessLogicException("No escribio ningun precio");
        c = persistencia.create(c);
        return c;
    }
}
