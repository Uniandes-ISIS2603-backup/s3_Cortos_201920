/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Pedro Luis Callejas Pinzon
 */
@Stateless
public class CineastaLogic {

    @Inject
    private CineastaPersistence persistence;

    public CineastaEntity createCineasta(CineastaEntity cineasta) throws BusinessLogicException {

        if (persistence.findByName(cineasta.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un cineasta con el nombre \"" + cineasta.getNombre() + "\"");
        }
        if (persistence.findByCorreo(cineasta.getCorreo()) != null) {
            throw new BusinessLogicException("Ya existe un cineasta con el correo \"" + cineasta.getCorreo() + "\"");
        }
        if (cineasta.getNombre() == null || cineasta.getNombre().equals("")) {
            throw new BusinessLogicException("El nombre no puede ser null ni vacio \"");
        }
        if (cineasta.getContrasenia() == null || cineasta.getContrasenia().equals("")) {
            throw new BusinessLogicException("La contraseña no puede ser null ni vacio \"");
        }
        
        cineasta = persistence.create(cineasta);
        return cineasta;
    }

}
