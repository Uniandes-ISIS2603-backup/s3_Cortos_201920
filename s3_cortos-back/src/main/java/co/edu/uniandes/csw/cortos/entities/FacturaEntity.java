/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import co.edu.uniandes.csw.cortos.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamDoubleValue;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamStrategyValue;
/**
 *
 * @author Ingrith Barbosa
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable 
{
  @PodamExclude
  @ManyToOne
  private ClienteEntity cliente;
    
  @PodamExclude
  @OneToMany(mappedBy = "factura")
  private List<CortoEntity> cortos;
  
  @PodamIntValue(minValue=1, maxValue= Integer.MAX_VALUE)
  private Integer numeroFactura;
  
  @PodamDoubleValue(minValue=0.0, maxValue=Double.MAX_VALUE)
  private Double costoTotal;
  
  @Temporal(TemporalType.DATE)
  @PodamStrategyValue(DateStrategy.class)
  private Date fecha;
  
  public FacturaEntity()
  {
      //Constructor vacío por clase serializable
  }
  public FacturaEntity(Integer pNumeroFactura, Double pCostoTotal, Date pFecha)
  {
      this.numeroFactura= pNumeroFactura;
      this.costoTotal= pCostoTotal;
      this.fecha= pFecha;
  }
  
  public Integer getNumeroFactura()
  {
      return numeroFactura;
  }
  public void setNumeroFactura(Integer pNumeroFactura)
  {
      this.numeroFactura= pNumeroFactura;
  }
  public Double getCostoTotal()
  {
      return costoTotal;
  }
  public void setCostoTotal(Double pCostoTotal)
  {
      this.costoTotal= pCostoTotal;
  }
  public Date getFecha()
  {
      return fecha;
  }
  public void setFecha(Date pFecha)
  {
      this.fecha=pFecha;
  }
  /**
   * Obtiene la colección de cortos.
   *
   * @return colección cortos.
   */
  public List<CortoEntity> getCortos() {
      return cortos;
  }
  /**    
   * Establece el valor de la colección de cortos.
   *
   * @param cortos nuevo valor de la colección.
   */
  public void setCortos(List<CortoEntity> cortos) {
      this.cortos = cortos;
  }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FacturaEntity other = (FacturaEntity) obj;
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.cortos, other.cortos)) {
            return false;
        }
        if (!Objects.equals(this.numeroFactura, other.numeroFactura)) {
            return false;
        }
        if (!Objects.equals(this.costoTotal, other.costoTotal)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }
  
  
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.cliente);
        hash = 59 * hash + Objects.hashCode(this.cortos);
        hash = 59 * hash + Objects.hashCode(this.numeroFactura);
        hash = 59 * hash + Objects.hashCode(this.costoTotal);
        hash = 59 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

  


    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    
}
