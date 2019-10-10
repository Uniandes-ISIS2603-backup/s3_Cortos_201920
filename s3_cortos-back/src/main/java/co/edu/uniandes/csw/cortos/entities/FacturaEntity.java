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
import javax.persistence.Entity;
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
     * Metodo no usado
     *
     * @param obj Object que se compara.
     * @return despreciado.
     * @deprecated (solo arregla code smell)
     */
    @Override
    @Deprecated
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Metodo no usado
     *
     * @return nada.
     * @deprecated (solo arregla code smell)
     */
    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }

    public List<CortoEntity> getCortos() {
        return cortos;
    }

    public void setCortos(List<CortoEntity> cortos) {
        this.cortos = cortos;
    }
    
}
