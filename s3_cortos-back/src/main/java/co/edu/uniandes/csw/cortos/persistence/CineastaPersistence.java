/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.persistence;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Luis Callejas Pinzon
 */
@Stateless
public class CineastaPersistence {

    @PersistenceContext(unitName = "cortosPU")
    protected EntityManager em;

    public CineastaEntity create(CineastaEntity cineastaEntity) {
        em.persist(cineastaEntity);
        return cineastaEntity;
    }

    public CineastaEntity find(long cineastaId) {
        return em.find(CineastaEntity.class, cineastaId);
    }

    public List<CineastaEntity> findAll() {
        TypedQuery<CineastaEntity> query = em.createQuery("select u from CineastaEntity u", CineastaEntity.class);
        return query.getResultList();
    }

    public CineastaEntity update(CineastaEntity cineastaEntity) {
        return em.merge(cineastaEntity);
    }

    public void delete(long cineastaId) {
        CineastaEntity cineastaEntity = em.find(CineastaEntity.class, cineastaId);
        em.remove(cineastaEntity);
    }

    public CineastaEntity findByName(String name) {
        TypedQuery<CineastaEntity> query = em.createQuery("select e from CineastaEntity e where e.nombre = :name ", CineastaEntity.class);
        query = query.setParameter("name", name);

        List<CineastaEntity> sameName = query.getResultList();
        CineastaEntity result;

        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }

        return result;
    }
    
    public CineastaEntity findByCorreo(String correo) {
        TypedQuery<CineastaEntity> query = em.createQuery("select e from CineastaEntity e where e.correo = :correo ", CineastaEntity.class);
        query = query.setParameter("correo", correo);

        List<CineastaEntity> encontrado = query.getResultList();
        CineastaEntity result;

        if (encontrado == null) {
            result = null;
        } else if (encontrado.isEmpty()) {
            result = null;
        } else {
            result = encontrado.get(0);
        }

        return result;
    }
}
