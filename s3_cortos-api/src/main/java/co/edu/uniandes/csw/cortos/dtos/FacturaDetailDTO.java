/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ingrith Barbosa
 */
public class FacturaDetailDTO extends FacturaDTO implements Serializable
{
    // relación  uno o muchos libros
    private List<CortoDTO> cortos;
    
    public FacturaDetailDTO()
    {
        super();
    }
    /**
     * Crea un objeto FacturaDetailDTO a partir de un objeto FacturaEntity
     * incluyendo los atributos de FacturaDTO.
     *
     * @param facturaEntity Entidad FacturaEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public FacturaDetailDTO(FacturaEntity facturaEntity) {
        super(facturaEntity);
        if (facturaEntity != null) {
            cortos = new ArrayList<>();
            for (CortoEntity entityCortos : facturaEntity.getCortos()) {
                cortos.add(new CortoDTO(entityCortos));
            }
        }
    }
    /**
     * Convierte un objeto FacturaDetailDTO a FacturaEntity incluyendo los
     * atributos de FacturaDTO.
     *
     * @return Nueva objeto FacturaEntity.
     *
     */
    @Override
    public FacturaEntity toEntity() {
        FacturaEntity facturaEntity = super.toEntity();
        if (cortos != null) {
            List<CortoEntity> cortosEntity = new ArrayList<>();
            for (CortoDTO dtoCorto : cortos) {
                cortosEntity.add(dtoCorto.toEntity());
            }
            facturaEntity.setCortos(cortosEntity);
        }
        return facturaEntity;
    }
     /**
   * Obtiene la colección de cortos.
   *
   * @return colección cortos.
   */
  public List<CortoDTO> getCortos() {
      return cortos;
  }
  /**    
   * Establece el valor de la colección de cortos.
   *
   * @param cortos nuevo valor de la colección.
   */
  public void setCortos(List<CortoDTO> cortos) {
      this.cortos = cortos;
  }

}
