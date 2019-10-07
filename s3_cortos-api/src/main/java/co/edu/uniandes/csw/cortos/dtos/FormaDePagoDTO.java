/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.entities.FormaDePagoEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Estudiante
 */
public class FormaDePagoDTO implements Serializable {
    
    private Long numero;
    private Integer ccv;
    private Date fechaDeVencimiento;
    
    public FormaDePagoDTO()
    {
       
    }
    
    public FormaDePagoDTO(FormaDePagoEntity entity)
    {
        if(entity!=null){
            this.numero=entity.getNumero();
            this.ccv= entity.getCcv();
            this.fechaDeVencimiento = entity.getFechaDeVencimiento();
        }
    }
    
    public FormaDePagoEntity toEntity()
    {
        FormaDePagoEntity entidad = new FormaDePagoEntity();
        entidad.setCcv(ccv);
        entidad.setFechaDeVencimiento(fechaDeVencimiento);
        entidad.setNumero(numero);
        return entidad ;
    }
    
    /**
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * @return the ccv
     */
    public Integer getCcv() {
        return ccv;
    }

    /**
     * @param ccv the ccv to set
     */
    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    /**
     * @return the fechaDeVencimiento
     */
    public Date getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    /**
     * @param fechaDeVencimiento the fechaDeVencimiento to set
     */
    public void setFechaDeVencimiento(Date fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }
    
    
}
