/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ingrith Barbosa
 */
@Stateless
public class FacturaLogic {
    @Inject
    private FacturaPersistence persistence;
    
    public FacturaEntity createFactura (FacturaEntity factura) throws BusinessLogicException
    {
        if(factura.getFecha()== null)
        {
            throw new BusinessLogicException("La fecha de creación de la factura no puede ser null");
        }
        if(factura.getCostoTotal()==null || factura.getCostoTotal()<0.0)
        {
            throw new BusinessLogicException("El costo de la factura debe ser mayor o igual a 0");
        }
        if(factura.getNumeroFactura()==null || factura.getNumeroFactura()<=0)
        {
            throw new BusinessLogicException("El número que identifica a la factura debe ser mayor a 0 "+factura.getNumeroFactura());
        }
        if(persistence.findByNumber(factura.getNumeroFactura())!=null)
        {
            throw new BusinessLogicException("Ya existe una factura con el número"+factura.getNumeroFactura());
        }
        factura= persistence.create(factura);
        return factura;
    }
}
