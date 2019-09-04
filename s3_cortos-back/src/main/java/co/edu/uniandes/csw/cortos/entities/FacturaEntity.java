/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
/**
 *
 * @author Ingrith Barbosa
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable 
{
  private int idFactura;
  private Double costoTotal;
  private Date fecha;
  
  public int getIdFactura()
  {
      return idFactura;
  }
  public void setIdFactura(int pId)
  {
      this.idFactura= pId;
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
