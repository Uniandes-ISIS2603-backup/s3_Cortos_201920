/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import java.util.Date;
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

    private Boolean correoValido(String correo) {
        String[] arregloCorreo = correo.trim().split("@");

        return arregloCorreo.length == 2;
    }

    private Integer getEdad(Date fechaNacimiento) {
        Date fechaActual = new Date();
        long delta = fechaActual.getTime() - fechaNacimiento.getTime();
        long edad = delta / (1000 * 60 * 60 * 24 * 365);
        return (int) edad;
    }

    public CineastaEntity createCineasta(CineastaEntity cineasta) throws BusinessLogicException {

        if (cineasta.getNombre() == null || cineasta.getNombre().equals("")) {
            throw new BusinessLogicException("El nombre no puede ser null ni vacio \"");
        }
        if (cineasta.getCorreo() == null || cineasta.getCorreo().equals("")) {
            throw new BusinessLogicException("El correo no puede ser null ni vacio \"");
        }
        if (persistence.findByName(cineasta.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un cineasta con el nombre \"" + cineasta.getNombre() + "\"");
        }
        if (!correoValido(cineasta.getCorreo())) {
            throw new BusinessLogicException("El correo no esta bien escrito \"");
        }
        if (persistence.findByCorreo(cineasta.getCorreo()) != null) {
            throw new BusinessLogicException("Ya existe un cineasta con el correo \"" + cineasta.getCorreo() + "\"");
        }
        if (cineasta.getContrasenia() == null || cineasta.getContrasenia().equals("")) {
            throw new BusinessLogicException("La contraseña no puede ser null ni vacio \"");
        }
        if (cineasta.getFechaNacimiento() == null) {
            throw new BusinessLogicException("La fecha de nacimiento no puede ser null \"");
        }
        if (getEdad(cineasta.getFechaNacimiento()) < 13 || getEdad(cineasta.getFechaNacimiento()) > 120) {
            throw new BusinessLogicException("El cineasta tiene la edad suficiente para realizar el registro \"");
        }
        if (cineasta.getTelefono().equals("") || cineasta.getTelefono() == null) {
            throw new BusinessLogicException("El telefono no puede ser vacio ni null \"");
        }
        if (cineasta.getDireccion().equals("") || cineasta.getDireccion() == null) {
            throw new BusinessLogicException("La dirección no puede ser vacio ni null \"");
        }
        if (cineasta.getRol() == null) {
            throw new BusinessLogicException("El rol no puede ser null \"");
        }
        if (cineasta.getGenero() == null) {
            throw new BusinessLogicException("El género no puede null \"");
        }
        cineasta = persistence.create(cineasta);
        return cineasta;
    }

}
