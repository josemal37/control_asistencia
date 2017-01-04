/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloAsistencia;
import Modelo.ModeloEmpleado;
import Modelo.ModeloTiempo;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import Vista.VistaRegistroAsistencia;
import java.sql.SQLException;

/**
 *
 * @author Jose
 */
public class ControladorPrincipal {

    ControladorAsistencia controladorAsistencia;
    VistaPrincipal vistaPrincipal;

    public ControladorPrincipal() {
        try {
            //modelos
            ModeloAsistencia modeloAsistencia = new ModeloAsistencia();
            ModeloEmpleado modeloEmpleado = new ModeloEmpleado();
            ModeloTiempo modeloTiempo = new ModeloTiempo();
            
            //controladores
            controladorAsistencia = new ControladorAsistencia();
            controladorAsistencia.setModeloAsistencia(modeloAsistencia);
            controladorAsistencia.setModeloEmpleado(modeloEmpleado);
            controladorAsistencia.setModeloTiempo(modeloTiempo);
            
            //vistas
            vistaPrincipal = new VistaPrincipal();
            VistaRegistroAsistencia vistaRegistroAsistencia = controladorAsistencia.getVistaRegistroAsistencia();
            vistaPrincipal.setPanelPrincipal(vistaRegistroAsistencia);
            vistaPrincipal.setLocationRelativeTo(null);
            vistaPrincipal.getRootPane().setDefaultButton(vistaRegistroAsistencia.getjButtonAceptar());
            vistaPrincipal.setVisible(true);
        } catch (ClassNotFoundException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        } catch (SQLException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        }
    }
}
