/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.FacturaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ingrith Barbosa
 */
@Stateless
public class FacturaPersistence {
    
    @PersistenceContext(unitName = "cortosPU")
    protected EntityManager em;
    
    /**
     * Crea una factura en la base de datos
     *
     * @param facturaEntity objeto factura que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FacturaEntity create(FacturaEntity factura)
    {
       em.persist(factura);
       return factura;
    }
    /**
     * Devuelve todas las facturas de la base de datos.
     *
     * @return una lista con todas las facturas que encuentre en la base de
     * datos.
     */
    public List<FacturaEntity> findAll() 
    {
        TypedQuery query = em.createQuery("select u from FacturaEntity u", FacturaEntity.class);
        return query.getResultList();
    }
    /**
     * Busca si hay alguna factura con el id que se envía de argumento
     *
     * @param facturaId: id correspondiente a la factura buscada.
     * @return La factura con el id buscado.
     */
    public FacturaEntity find(Long facturaId) 
    {
        return em.find(FacturaEntity.class, facturaId);
    }
    /**
     * Actualiza una factura.
     *
     * @param facturaEntity: la factura que viene con los nuevos cambios. Por
     * ejemplo el costo total pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una factura con los cambios aplicados.
     */
    public FacturaEntity update(FacturaEntity facturaEntity)
    {
        return em.merge(facturaEntity);
    }
    public FacturaEntity delete(FacturaEntity facturaEntity)
    {
        em.remove(facturaEntity);
        return facturaEntity;
    }
    public FacturaEntity findByNumber(Integer pNumeroFactura)
    {
        TypedQuery<FacturaEntity> query = em.createQuery("select e from FacturaEntity e where e.numeroFactura = :pNumeroFactura ", FacturaEntity.class);
        query = query.setParameter("pNumeroFactura", pNumeroFactura);

        List<FacturaEntity> sameNumber = query.getResultList();
        FacturaEntity result;

        if (sameNumber == null) {
            result = null;
        } else if (sameNumber.isEmpty()) {
            result = null;
        } else {
            result = sameNumber.get(0);
        }
        return result;
    }
}
