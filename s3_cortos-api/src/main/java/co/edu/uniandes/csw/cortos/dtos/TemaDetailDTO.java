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
 * @author Juan Sebastian Gomez
 */
public class TemaDetailDTO extends TemaDTO implements Serializable{
    private List<CortoDTO> cortos;
    private List<CineastaDTO> cineastas;

    public TemaDetailDTO() {
        super();
    }
    
    public TemaDetailDTO(TemaEntity temaEntity){
        super(temaEntity);
        if(temaEntity.getCineasta()!=null){
            cineastas = new ArrayList<>();
            for(CineastaEntity c : temaEntity.getCineasta()){
                cineastas.add(new CineastaDTO(c));
            }
        }
        if(temaEntity.getCortos()!=null){
            cortos = new ArrayList<>();
            for(CortoEntity c : temaEntity.getCortos()){
                cortos.add(new CortoDTO(c));
            }
        }
    }
    @Override
    public TemaEntity toEntity(){
        TemaEntity t = super.toEntity();
        if(cineastas!=null){
            List<CineastaEntity> cines = new ArrayList<>();
            for(CineastaDTO  c : getCineastas()){
                cines.add(c.toEntity());
            }
        }
        if(cortos!=null){
            List<CortoEntity> cortosEntity = new ArrayList<>();
            for(CortoDTO c : getCortos()){
             cortosEntity.add(c.toEntity());
            }
        }
        return t;
    }
    public List<CortoDTO> getCortos() {
        return cortos;
    }

    public void setCortos(List<CortoDTO> cortos) {
        this.cortos = cortos;
    }

    public List<CineastaDTO> getCineastas() {
        return cineastas;
    }

    public void setCineastas(List<CineastaDTO> cineastas) {
        this.cineastas = cineastas;
    }
    
}
