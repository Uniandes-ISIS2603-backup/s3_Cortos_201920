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
import uk.co.jemos.podam.common.PodamStrategyValue;
/**
 *
 * @author Ingrith Barbosa
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable 
{
  private int numeroFactura;
  private Double costoTotal;
  @Temporal(TemporalType.DATE)
  @PodamStrategyValue(DateStrategy.class)
  private Date fecha;
  
  public FacturaEntity()
  {
      
  }
  public FacturaEntity(int pNumeroFactura, Double pCostoTotal, Date pFecha)
  {
      this.numeroFactura= pNumeroFactura;
      this.costoTotal= pCostoTotal;
      this.fecha= pFecha;
  }
  
  public int getNumeroFactura()
  {
      return numeroFactura;
  }
  public void setNumeroFactura(int pId)
  {
      this.numeroFactura= pId;
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
}
