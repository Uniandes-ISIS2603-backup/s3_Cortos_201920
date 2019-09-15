/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoPersistence {
    
    @PersistenceContext(unitName = "cortosPU")
    protected EntityManager em;
    /**
     * Crea un nuevo elemento en la base de datos y lo persiste
     * @param cortoEntity entidad a persistir
     * @return corto persistido
     */
    public CortoEntity create(CortoEntity cortoEntity) {
        em.persist(cortoEntity);
        return cortoEntity;
    }
    /**
     * Eliminar corto con id especifico
     * @param id identificador del corto a eliminar
     */
    public void delete(Long id){
        CortoEntity aBorrar = em.find(CortoEntity.class, id);
        em.remove(aBorrar);
    }
    /**
     * Actualiza corto
     * @param cortoEntity corto con informacion actualizada
     * @return corto actualizado
     */
    public CortoEntity update(CortoEntity cortoEntity){
        return em.merge(cortoEntity);
    }
    /**
     * Obtener corto con id especificado
     * @param cortoId id del corto a encontrar
     * @return corto encontrado o nulo
     */
    public CortoEntity find(Long cortoId){
        return em.find(CortoEntity.class, cortoId);
    }
    /**
     * Lista de cortos en la base de datos
     * @return todos los cortos en una lista de la base de datos
     */
    public List<CortoEntity> findAll(){
        Query q = em.createQuery("select u from CortoEntity u");
        return q.getResultList();
    }
    
}
