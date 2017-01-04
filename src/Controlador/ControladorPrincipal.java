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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

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
            
            //configuramos la vista principal
            this.setVistaPrincipal(vistaPrincipal, vistaRegistroAsistencia);
            
        } catch (ClassNotFoundException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        } catch (SQLException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        }
    }

    private void setVistaPrincipal(final VistaPrincipal vistaPrincipal, VistaRegistroAsistencia vistaRegistroAsistencia) {
        //aniadimos el panel a la vista
        vistaPrincipal.setPanelPrincipal(vistaRegistroAsistencia);
        
        //configuramos los botones de acceso rapido
        JRootPane rootPane = vistaPrincipal.getRootPane();
        rootPane.setDefaultButton(vistaRegistroAsistencia.getjButtonAceptar());
        
        Action menuAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JRootPane rootPane = vistaPrincipal.getRootPane();
                JMenuBar jMenuBar = rootPane.getJMenuBar();
                JMenu menu = jMenuBar.getMenu(0);
                menu.doClick();
            }
        };
        ActionMap actionMap = rootPane.getActionMap();
        final String MENU_ACTION_KEY = "Abrir primero este menú";
        actionMap.put(MENU_ACTION_KEY, menuAction);
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ALT, 0, true), MENU_ACTION_KEY);
        
        //posicionamos y mostramos la vista
        vistaPrincipal.setLocationRelativeTo(null);
        vistaPrincipal.setVisible(true);
    }
}
