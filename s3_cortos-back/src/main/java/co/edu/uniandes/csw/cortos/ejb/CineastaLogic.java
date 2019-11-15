/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.entities.CortoEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    private int getEdad(Date birthDate) {
        int years;
        int months;

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        //Calculate the days
        if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
        } else if (months == 12) {
            years++;
        }
        return years;
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
            throw new BusinessLogicException("El cineasta no tiene la edad suficiente para realizar el registro  \"");
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

    /**
     * Obtiene la lista de los registros de Cineasta.
     *
     * @return Colección de objetos de CineastaEntity.
     */
    public List<CineastaEntity> getCineastas() {
       
        return persistence.findAll();

    }

    /**
     * Obtiene los datos de una instancia de Cineasta a partir de su ID.
     *
     * @param cineastaId Identificador de la instancia a consultar
     * @return Instancia de CineastaEntity con los datos del Cineasta
     * consultado.
     */
    public CineastaEntity getCineasta(Long cineastaId) {

        return persistence.find(cineastaId);
    }

    /**
     * Actualiza la información de una instancia de Cineasta.
     *
     * @param cineastaId Identificador de la instancia a actualizar
     * @param cineastaEntity Instancia de AuthorEntity con los nuevos datos.
     * @return Instancia de CineastaEntity con los datos actualizados.
     */
    public CineastaEntity updateCineasta(Long cineastaId, CineastaEntity cineastaEntity) {

        CineastaEntity newCineastaEntity = persistence.update(cineastaEntity);

        return newCineastaEntity;
    }

    /**
     * Elimina una instancia de Cineasta de la base de datos.
     *
     * @param cineastaId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el cineasta tiene cortos asociados.
     */
    public void deleteCineasta(Long cineastaId) throws BusinessLogicException {

        List<CortoEntity> cortos = getCineasta(cineastaId).getCortos();
        if (cortos != null && !cortos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el cineasta con id = " + cineastaId + " porque tiene cortos asociados");
        }
        persistence.delete(cineastaId);//pregunta sobre la asociacion de cineastacorto

    }
}
