/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dominio.Empleado;
import Dominio.TipoEmpleado;
import Modelo.ModeloEmpleado;
import Vista.JDialogEmpleado;
import Vista.VistaEmpleados;
import Vista.VistaMensajes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Jose
 */
public class ControladorEmpleado {

    //Modelos
    ModeloEmpleado modeloEmpleado;

    //Vistas
    JDialogEmpleado jDialogEmpleado;

    public ControladorEmpleado(JFrame vistaPrincipal) {
        try {
            this.modeloEmpleado = new ModeloEmpleado();
            this.jDialogEmpleado = new JDialogEmpleado(vistaPrincipal, true, this);
            
            jDialogEmpleado.setLocationRelativeTo(null);
            jDialogEmpleado.setVisible(true);
        } catch (ClassNotFoundException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        } catch (SQLException ex) {
            VistaMensajes.mostrarMensaje(ex.getMessage());
        }
    }

    public ModeloEmpleado getModeloEmpleado() {
        return modeloEmpleado;
    }

    public void setModeloEmpleado(ModeloEmpleado modeloEmpleado) {
        this.modeloEmpleado = modeloEmpleado;
    }
    
    public ArrayList<Empleado> getEmpleados() {
        ArrayList<Empleado> e = new ArrayList<>();
        try {
            e = this.modeloEmpleado.selectEmpleados();
        } catch (SQLException ex) {
            VistaMensajes.mostrarMensaje("Ocurrió un problema al obtener los datos de los empleados.");
        }
        return e;
    }
    
    public boolean addEmpleado(Empleado e) {
        boolean res = false;
        try {
            res = this.modeloEmpleado.insertEmpleado(e);
        } catch (SQLException ex) {
            VistaMensajes.mostrarMensaje("Ocurrió un problema al guardar los datos del empleado.");
        }
        return res;
    }
    
    public ArrayList<TipoEmpleado> getTiposEmpleado() {
        ArrayList<TipoEmpleado> tiposEmpleado = new ArrayList<>();
        try {
            tiposEmpleado = this.modeloEmpleado.selectTiposEmpleado();
        } catch (SQLException ex) {
            VistaMensajes.mostrarMensaje("Ocurrió un problema al obtener los tipos de empleado.");
        }
        return tiposEmpleado;
    }
    
    public void setVistaEmpleados() {
        this.jDialogEmpleado.setVistaEmpleados();
    }
    
    public void setVistaRegistrarEmpleado() {
        this.jDialogEmpleado.setVistaRegistrarEmpleado();
    }
}
