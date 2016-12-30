/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Jose
 */
public class ModeloTiempo {
    
    public ModeloTiempo() {
        super();
    }
    
    public java.sql.Date getFecha(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    public java.sql.Time getHora(java.util.Date date) {
        return new java.sql.Time(date.getTime());
    }
    
    public java.sql.Time getHora(String hora) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date horaDate = dateFormat.parse(hora);
        
        return this.getHora(horaDate);
    }
    
    public java.sql.Date getFechaActual() {
        java.util.Date currentDate = new java.util.Date();
        
        return new java.sql.Date(currentDate.getTime());
    }
    
    public java.sql.Time getHoraActual() {
        java.util.Date currentDate = new java.util.Date();
        
        return new java.sql.Time(currentDate.getTime());
    }
}
