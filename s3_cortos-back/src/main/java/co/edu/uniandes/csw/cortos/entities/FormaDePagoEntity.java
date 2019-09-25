/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import co.edu.uniandes.csw.cortos.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamLongValue;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Juan Felipe Mejia Parra
 */
@Entity
public class FormaDePagoEntity extends BaseEntity implements Serializable {
    
    @PodamLongValue(minValue=1000000000000000L, maxValue= 9999999999999999L)
    private Long numero;
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaDeVencimiento;
    
    @PodamIntValue(minValue=100, maxValue=999)
    private int ccv;
    
    public FormaDePagoEntity()
    {
        
    }
    
    public FormaDePagoEntity(Long pNumero, Date pFechaDeVencimiento, int pCcv)
    {
        this.numero = pNumero;
        this.fechaDeVencimiento = pFechaDeVencimiento;
        this.ccv = pCcv;
    }
    
    public Date getFechaDeVencimiento()
    {
        return fechaDeVencimiento;
    }
    
    public Long getNumero()
    {
        return numero;
    }
    
    public int getCcv()
    {
        return ccv;
    }
    
    public void setNumero(Long pNumero)
    {
        this.numero = pNumero;
    }
    
    public void setFechaDeVencimiento(Date pFechaDeVencimiento)
    {
        this.fechaDeVencimiento = pFechaDeVencimiento;
    }
    
    public void setCcv(int pCcv)
    {
        this.ccv = pCcv;
    }
    
}
