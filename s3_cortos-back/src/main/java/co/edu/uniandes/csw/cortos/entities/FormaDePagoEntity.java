/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Juan Felipe Mejia Parra
 */
@Entity
public class FormaDePagoEntity extends BaseEntity implements Serializable {
    
    private String numero;
    private String fechaDeVencimiento;
    private String ccv;
    
    
    public String getFechaDeVencimiento()
    {
        return fechaDeVencimiento;
    }
    
    public String getNumero()
    {
        return numero;
    }
    
    public String getCcv()
    {
        return ccv;
    }
    
    public void setNumero(String pNumero)
    {
        this.numero = pNumero;
    }
    
    public void setFechaDeVencimiento(String pFechaDeVencimiento)
    {
        this.fechaDeVencimiento = pFechaDeVencimiento;
    }
    
    public void setCcv(String pCcv)
    {
        this.ccv = pCcv;
    }
    
}
