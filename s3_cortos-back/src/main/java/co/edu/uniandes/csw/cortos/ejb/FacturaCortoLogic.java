/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import co.edu.uniandes.csw.cortos.persistence.FacturaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @authorIngrith Barbosa
 */
@Stateless
public class FacturaCortoLogic 
{
    /**
     * Referencia a la persistencia de los cortos 
     */
    @Inject
    private CortoPersistence cortoP;
    /**
     * Referencia a la persistencia de la factura
     */
    @Inject
    private FacturaPersistence facturaP;
    /**
     * Agrega un corto a la lista de cortos dela factura
     * @param facturaId identificador de la factura
     * @param cortoId identificador del corto a agregar
     * @return corto agregado
     */
    public CortoEntity addCorto(Long facturaId, Long cortoId){
        CortoEntity agregar = cortoP.find(cortoId);
        FacturaEntity agreguemeCosas = facturaP.find(facturaId);
        agreguemeCosas.getCortos().add(agregar);
        return agregar;
    }
    /**
     * Lista de cortos de una factura
     * @param facturaId identificacion de la factura
     * @return lista de cortos de una factura
     */
    public List<CortoEntity> getCortos(Long facturaId){
        return facturaP.find(facturaId).getCortos();
    }
    /**
     * Retorna corto especifico de una factura
     * @param facturaId identifiacion de la factura
     * @param cortoId identificacion del corto
     * @return el corto con la identificacion recibida por parametro
     */
    public CortoEntity getCorto(Long facturaId, Long cortoId){
        List<CortoEntity> lista = getCortos(facturaId);
        CortoEntity existe = cortoP.find(cortoId);
        int index = lista.indexOf(existe);
        if(index >= 0){
            return lista.get(index);
        }
        return null;
    }
    /**
     * Remueve un corto especifico de la lista de cortos de una factura
     * @param facturaId identificacion de la factura
     * @param cortoId identificacion del corto a remover
     */
    public void removeCorto(Long facturaId, Long cortoId){
        CortoEntity eliminado = cortoP.find(cortoId);
        FacturaEntity dueña = facturaP.find(facturaId);
        dueña.getCortos().remove(eliminado);
    }
}
