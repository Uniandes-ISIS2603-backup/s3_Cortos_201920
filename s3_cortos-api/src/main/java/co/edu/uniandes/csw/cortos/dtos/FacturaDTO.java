/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.adapters.DateAdapter;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Ingrith Barbosa
 */
public class FacturaDTO implements Serializable
{
    private Long id;
    private Integer numeroFactura;
    private Double costoTotal;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fecha;
    
    public FacturaDTO()
    {
        
    }
    public FacturaDTO(FacturaEntity facturaEntity)
    {
        if (facturaEntity != null) {
            this.id = facturaEntity.getId();
            this.numeroFactura = facturaEntity.getNumeroFactura();
            this.costoTotal=facturaEntity.getCostoTotal();
            this.fecha=facturaEntity.getFecha();
        }
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getNumeroFactura()
    {
        return this.numeroFactura;
    }
    public void setNumeroFactura(Integer newNumero)
    {
        this.numeroFactura=newNumero;
    }
    public Double getCostoTotal()
    {
        return this.costoTotal;
    }
    public void setCostoTotal(Double newCosto)
    {
        this.costoTotal=newCosto;
    }
    public Date getFecha()
    {
        return this.fecha;
    }
    public void setFecha(Date newFecha)
    {
        this.fecha=newFecha;
    }
    public FacturaEntity toEntity() {
        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setId(this.id);
        facturaEntity.setNumeroFactura(this.numeroFactura);
        facturaEntity.setCostoTotal(this.costoTotal);
        facturaEntity.setFecha(this.fecha);
        return facturaEntity;
    }
}
