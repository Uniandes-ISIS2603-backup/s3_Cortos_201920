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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class CortoFacturaLogic {
    /**
     * Referencia a la persistencia del corto
     */
    @Inject
    private CortoPersistence cp;
    /**
     * Referencia a la persistencia de la factura
     */
    @Inject
    private FacturaPersistence fp;
    /**
     * Modifica la factura de un corto
     * @param cortoId identificacion del corto
     * @param facturaId identificacion factura
     * @return retorna el cineasta que sera el nuevo productor
     */
    public FacturaEntity replaceFactura(Long cortoId, Long facturaId){
        FacturaEntity agregueme = fp.find(facturaId);
        CortoEntity metalo = cp.find(cortoId);
        metalo.setFactura(agregueme);
        return agregueme;
    }
    /**
     * quita la factura del corto
     * @param facturaId identificacion factura
     * @param cortoId  identificador del corto al que se le quitara el productor
     */
    public void removeFactura(Long facturaId, Long cortoId){
        CortoEntity quitemelo = cp.find(cortoId);
        FacturaEntity puesMeVoy = fp.find(facturaId);
        quitemelo.setFactura(null);
        puesMeVoy.getCortos().remove(quitemelo);
    }
}
