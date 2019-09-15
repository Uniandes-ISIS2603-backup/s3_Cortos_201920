/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CortoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastian Gomez
 */
@Stateless
public class CortoLogic {
    /**
     * Referencia a la clase CortoPersistence
     */
    @Inject
    private CortoPersistence persistencia;
    /**
     * Crea un nuevo corto
     * @param c corto a crear
     * @return corto creado
     * @throws BusinessLogicException si nombre, precio o descripcion nulos
     */
    public CortoEntity createCorto(CortoEntity c)throws BusinessLogicException{
        if(c.getNombre()==null)
            throw new BusinessLogicException("No escribio ningun nombre");
        if(c.getDescripcion() == null)
            throw new BusinessLogicException("No hay ninguna descripcion");
        if(c.getPrecio() == null)
            throw new BusinessLogicException("No escribio ningun precio");
        c = persistencia.create(c);
        return c;
    }
    /**
     * Actualiza el corto con el id especificado
     * @param cortoId id del corto a actualizar
     * @param c informacion actualizada del corto
     * @return corto actualizado
     * @throws BusinessLogicException si nombre, precio o descripcion nulos 
     */
    public CortoEntity updateCorto(Long cortoId, CortoEntity c)throws BusinessLogicException{
        if(c.getNombre()==null)
            throw new BusinessLogicException("No escribio ningun nombre");
        if(c.getDescripcion() == null)
            throw new BusinessLogicException("No hay ninguna descripcion");
        if(c.getPrecio() == null)
            throw new BusinessLogicException("No escribio ningun precio");
        CortoEntity n = persistencia.update(c);
        return n;
    }
    /**
     * Obtener un corto por id especifico
     * @param cId id del corto solicitado
     * @return null si el corto no existe, la entidad encontrada
     */
    public CortoEntity getCorto(Long cId){
        CortoEntity e = persistencia.find(cId);
        return e;
    }
    /**
     * Obtener todos los cortos en la base de datos
     * @return lista de los cortos de la base de datos
     */
    public List<CortoEntity> getCortos(){
        List<CortoEntity> cs = persistencia.findAll();
        return cs;
    }
    /**
     * Elimina corto con id especificado
     * @param cId id del corto a eliminar
     */
   public void deleteCorto(Long cId){
       persistencia.delete(cId);
   } 
   
}
