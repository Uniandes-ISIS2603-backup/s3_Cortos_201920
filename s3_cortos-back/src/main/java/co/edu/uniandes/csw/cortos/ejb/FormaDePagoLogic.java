/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.FormaDePagoPersistance;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Felipe Mejia
 */
@Stateless
public class FormaDePagoLogic {
    
    @Inject 
    private FormaDePagoPersistance persistence;
    
    public FormaDePagoEntity createFormaDePago(FormaDePagoEntity formaDePago) throws BusinessLogicException
    {
        if(formaDePago.getCcv() < 100 || formaDePago.getCcv() > 999)
        {
            throw new BusinessLogicException("El codigo de seguridad no es valido \"");
        }
        
        if(formaDePago.getNumero() < 1000000000000000L || formaDePago.getNumero() > 9999999999999999L)
        {
            throw new BusinessLogicException("El numero de tarjeta no es valido \"");
        }
        
        if(formaDePago.getFechaDeVencimiento().before(new java.util.Date()))
        {
           throw new BusinessLogicException("La tarjeta está vencida \"");
        }
        
        formaDePago = persistence.create(formaDePago);
        return formaDePago;
    }
    
    public List<FormaDePagoEntity > getFormasDePago()
    {
        List<FormaDePagoEntity> formasDePago = persistence.findAll();
        return formasDePago;
    }
    
    public FormaDePagoEntity getFormaDePago(Long numero)
    {
        FormaDePagoEntity formaDePagoEntity= persistence.find(numero);
        return formaDePagoEntity;
    }
    
     public FormaDePagoEntity updateFormaDePago (Long id, FormaDePagoEntity formaDePago)throws BusinessLogicException
    {
        FormaDePagoEntity newEntity = persistence.update(formaDePago);
        return newEntity;
    }
     
     public void deleteFormaDePago(Long numero ) throws BusinessLogicException
    {
        persistence.delete(numero);
    }
}
