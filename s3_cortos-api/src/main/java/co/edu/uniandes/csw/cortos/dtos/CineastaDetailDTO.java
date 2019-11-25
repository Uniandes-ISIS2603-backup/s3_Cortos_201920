/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.dtos;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.TemaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
    * @author Pedro Callejas
 */
public class CineastaDetailDTO extends CineastaDTO implements Serializable{
    
     /**
     * Asociación con la claseTemaEntity, que describe los temas de interes del
     * cineasta.
     */
    private List<TemaDTO> temas;

    /**
     * Asociación con la clase CortosEntity, que describe el corto producido por
     * el cineasta.
     */
    private List<CortoDTO> cortos;

    /**
     * Asociación con la clase CineastaEntity, describe el corto asociado a los
     * cineastas "directores".
     */
    
    
     public CineastaDetailDTO()
    {
        super();
    }
    
    public CineastaDetailDTO(CineastaEntity c) {
        super(c);
        if (c.getTemas() != null) {
            temas = new ArrayList<>();
            for (TemaEntity tema : c.getTemas()) {
                temas.add(new TemaDTO(tema));
            }
        }
        if (c.getCortos() != null) {
            cortos = new ArrayList<>();
            for (CortoEntity corto : c.getCortos()) {
                cortos.add(new CortoDTO(corto));
            }
        }
    }
    
     @Override
    public CineastaEntity toEntity(){
        CineastaEntity c = super.toEntity();
        if(getTemas() !=null){
            List<TemaEntity> temEnt = new ArrayList<>();
            for( TemaDTO tem : getTemas() )
                temEnt.add(tem.toEntity());
            c.setTemas(temEnt);
        }
        if(cortos!=null){
            List<CortoEntity> cortEnt = new ArrayList<>();
            for( CortoDTO cort : getCortos() )
                cortEnt.add(cort.toEntity());
            c.setCortos(cortEnt);
        }
        return c;
    }

    /**
     * @return the temas
     */
    public List<TemaDTO> getTemas() {
        return temas;
    }

    /**
     * @param temas the temas to set
     */
    public void setTemas(List<TemaDTO> temas) {
        this.temas = temas;
    }

    /**
     * @return the cortos
     */
    public List<CortoDTO> getCortos() {
        return cortos;
    }

    /**
     * @param cortos the cortos to set
     */
    public void setCortos(List<CortoDTO> cortos) {
        this.cortos = cortos;
    }

    
}
