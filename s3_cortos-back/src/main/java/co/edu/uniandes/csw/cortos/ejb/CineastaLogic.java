/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.cortos.ejb;

import co.edu.uniandes.csw.cortos.entities.CineastaEntity;
import co.edu.uniandes.csw.cortos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.cortos.persistence.CineastaPersistence;
import java.util.Calendar;
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

 private int getEdad(Date birthDate)
   {
      int years = 0;
      int months = 0;
      int days = 0;
 
      //create calendar object for birth day
      Calendar birthDay = Calendar.getInstance();
      birthDay.setTimeInMillis(birthDate.getTime());
 
      //create calendar object for current day
      long currentTime = System.currentTimeMillis();
      Calendar now = Calendar.getInstance();
      now.setTimeInMillis(currentTime);
       System.out.println(now.get(Calendar.YEAR));
       System.out.println(birthDay.get(Calendar.YEAR));
      //Get difference between years
      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
      int currMonth = now.get(Calendar.MONTH) + 1;
      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
 
      //Get difference between months
      months = currMonth - birthMonth;
 
      //if month difference is in negative then reduce years by one
      //and calculate the number of months.
      if (months < 0)
      {
         years--;
         months = 12 - birthMonth + currMonth;
         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            months--;
      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         years--;
         months = 11;
      }
 
      //Calculate the days
      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         int today = now.get(Calendar.DAY_OF_MONTH);
         now.add(Calendar.MONTH, -1);
         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
      }
      else
      {
         days = 0;
         if (months == 12)
         {
            years++;
            months = 0;
         }
      }
       System.out.println(years);
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
            throw new BusinessLogicException("El cineasta no tiene la edad suficiente para realizar el registro  \"") ;
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
