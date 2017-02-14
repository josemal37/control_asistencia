/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dominio.Empleado;
import Modelo.ModeloAsistencia;
import Modelo.ModeloEmpleado;
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
    ModeloEmpleado modeloEmpleado;
    ModeloTiempo modeloTiempo;

    //vistas
    VistaRegistroAsistencia vistaRegistroAsistencia;

    public ControladorAsistencia() {
        this.vistaRegistroAsistencia = new VistaRegistroAsistencia(this);
    }

    public ModeloAsistencia getModeloAsistencia() {
        return modeloAsistencia;
    }

    public void setModeloAsistencia(ModeloAsistencia modeloAsistencia) {
        this.modeloAsistencia = modeloAsistencia;
    }

    public ModeloEmpleado getModeloEmpleado() {
        return modeloEmpleado;
    }

    public void setModeloEmpleado(ModeloEmpleado modeloEmpleado) {
        this.modeloEmpleado = modeloEmpleado;
    }

    public ModeloTiempo getModeloTiempo() {
        return modeloTiempo;
    }

    public void setModeloTiempo(ModeloTiempo modeloTiempo) {
        this.modeloTiempo = modeloTiempo;
    }

    public VistaRegistroAsistencia getVistaRegistroAsistencia() {
        return vistaRegistroAsistencia;
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

    public boolean registrarAsistencia(int ciEmpleado, Date fechaActual, Time horaActual, String tipoAsistencia) {

        boolean exitoso = false;
        
        try {
            //definimos la hora actual en formato cadena
            String horaActualCadena = this.getHoraCadena(horaActual);

            //registramos los datos
            int registrado = this.modeloAsistencia.registrarAsistencia(ciEmpleado, fechaActual, horaActual, tipoAsistencia);
            
            //recuperamos los datos del empleado
            Empleado e = this.modeloEmpleado.selectEmpleadoPorCI(ciEmpleado);
            
            //mostramos un mensaje de registro de asistencia
            switch(registrado) {
                case ModeloAsistencia.REGISTRADO_INGRESO_MANIANA:
                    VistaMensajes.mostrarMensaje(e + "\nRegistro ingreso mañana a las " + horaActualCadena + " exitoso.");
                    exitoso = true;
                    break;
                case ModeloAsistencia.REGISTRADO_SALIDA_MANIANA:
                    VistaMensajes.mostrarMensaje(e + "\nRegistro salida mañana a las " + horaActualCadena + " exitoso.");
                    exitoso = true;
                    break;
                case ModeloAsistencia.REGISTRADO_INGRESO_TARDE:
                    VistaMensajes.mostrarMensaje(e + "\nRegistro ingreso tarde a las " + horaActualCadena + " exitoso.");
                    exitoso = true;
                    break;
                case ModeloAsistencia.REGISTRADO_SALIDA_TARDE:
                    VistaMensajes.mostrarMensaje(e + "\nRegistro salida tarde a las " + horaActualCadena + " exitoso.");
                    exitoso = true;
                    break;
                case ModeloAsistencia.REGISTRADO_ANTERIOR:
                    VistaMensajes.mostrarMensaje(e + "\nUsted ya registró una salida para este horario.");
                    exitoso = true;
                    break;
                case ModeloAsistencia.REGISTRADO_ERROR:
                    VistaMensajes.mostrarMensaje("Hubo un error al registrar los datos.");
                    break;
            }
        } catch (SQLException ex) {
            String sqlState = ex.getSQLState();
            switch(sqlState) {
                case "23503":
                    VistaMensajes.mostrarMensaje("El CI ingresado no se encuentra registrado.");
                    break;
                default:
                    VistaMensajes.mostrarMensaje("Hubo un error en la base de datos: " + sqlState + ".");
                    break;
            }
        }
        
        return exitoso;
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
}
