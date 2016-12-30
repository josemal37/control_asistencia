/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloAsistencia;
import Modelo.ModeloTiempo;
import Vista.VistaMensajes;
import Vista.VistaPrincipal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            ModeloTiempo modeloTiempo = new ModeloTiempo();
            
            //controladores
            controladorAsistencia = new ControladorAsistencia();
            controladorAsistencia.setModeloAsistencia(modeloAsistencia);
            controladorAsistencia.setModeloTiempo(modeloTiempo);
            
            //vistas
            vistaPrincipal = new VistaPrincipal();
            vistaPrincipal.setPanelPrincipal(controladorAsistencia.getVistaRegistroAsistencia());
            vistaPrincipal.setVisible(true);
        } catch (ClassNotFoundException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        } catch (SQLException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        }
    }
}
