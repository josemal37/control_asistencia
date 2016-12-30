/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloAsistencia;
import Modelo.ModeloTiempo;
import Vista.VistaMensajes;
import Vista.VistaRegistroAsistencia;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose
 */
public class ControladorAsistencia {

    //modelos
    ModeloAsistencia modeloAsistencia;
    ModeloTiempo modeloTiempo;

    //vistas
    VistaRegistroAsistencia vistaRegistroAsistencia;

    public ControladorAsistencia() {
        this.vistaRegistroAsistencia = new VistaRegistroAsistencia(this);
    }

    public ModeloAsistencia getModeloAsistencia() {
        return modeloAsistencia;
    }

    public ModeloTiempo getModeloTiempo() {
        return modeloTiempo;
    }

    public VistaRegistroAsistencia getVistaRegistroAsistencia() {
        return vistaRegistroAsistencia;
    }

    public void setModeloAsistencia(ModeloAsistencia modeloAsistencia) {
        this.modeloAsistencia = modeloAsistencia;
    }

    public void setModeloTiempo(ModeloTiempo modeloTiempo) {
        this.modeloTiempo = modeloTiempo;
    }

    public void setVistaRegistroAsistencia(VistaRegistroAsistencia vistaRegistroAsistencia) {
        this.vistaRegistroAsistencia = vistaRegistroAsistencia;
    }

    public Time getHora(String hora) {
        Time res = null;
        try {
            res = this.modeloTiempo.getHora(hora);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorAsistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Date getFechaActual() {
        return this.modeloTiempo.getFechaActual();
    }

    public Time getHoraActual() {
        return this.modeloTiempo.getHoraActual();
    }

    public void registrarAsistencia(int ciEmpleado, Date fechaActual, Time horaActual) {

        try {
            //definimos la hora actual en formato cadena
            String horaActualCadena = this.getHoraCadena(horaActual);

            //registramos los datos
            if (horaActualCadena.compareTo("13:30") >= 0) {
                registrarAsistenciaTarde(ciEmpleado, fechaActual, horaActual);
            } else if (horaActualCadena.compareTo("07:50") >= 0) {
                registrarAsistenciaManiana(ciEmpleado, fechaActual, horaActual);
            }
        } catch (SQLException ex) {
            int sqlState = Integer.parseInt(ex.getSQLState());
            switch(sqlState) {
                case 23503:
                    VistaMensajes.mostrarMensaje("El CI ingresado no se encuentra registrado.");
                    break;
                default:
                    VistaMensajes.mostrarMensaje("Hubo un error en la base de datos: " + sqlState + ".");
                    break;
            }
        }
    }

    private String getHoraCadena(Time hora) {
        String res = "";
        int horaI = hora.getHours();
        int minutosI = hora.getMinutes();
        if (horaI < 10) {
            res = res + "0" + horaI;
        } else {
            res = res + horaI;
        }
        res = res + ":";
        if (minutosI < 10) {
            res = res + "0" + minutosI;
        } else {
            res = res + minutosI;
        }
        return res;
    }

    public void registrarAsistenciaManiana(int ciEmpleado, Date fecha, Time hora) throws SQLException {
        this.modeloAsistencia.insertAsistenciaManiana(ciEmpleado, fecha, hora);
    }

    public void registrarAsistenciaTarde(int ciEmpleado, Date fecha, Time hora) throws SQLException {
        this.modeloAsistencia.insertAsistenciaTarde(ciEmpleado, fecha, hora);
    }
}
