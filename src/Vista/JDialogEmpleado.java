/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vista;

import Controlador.ControladorEmpleado;
import Dominio.Empleado;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Jose
 */
public class JDialogEmpleado extends javax.swing.JDialog {

    ControladorEmpleado controladorEmpleado;
    
    /**
     * Creates new form JDialogEmpleado
     */
    public JDialogEmpleado(java.awt.Frame parent, boolean modal, ControladorEmpleado controladorEmpleado) {
        super(parent, modal);
        this.controladorEmpleado = controladorEmpleado;
        
        initComponents();
        
        this.setVistaEmpleados();
    }

    public void setVistaEmpleados() {
        VistaEmpleados vistaEmpleados = new VistaEmpleados(controladorEmpleado);
        ArrayList<Empleado> empleados = this.controladorEmpleado.getEmpleados();
        vistaEmpleados.addEmpleados(empleados);
        this.cambiarVista(vistaEmpleados);
    }
    
    public void setVistaRegistrarEmpleado() {
        VistaRegistrarEmpleado vistaRegistrarEmpleado = new VistaRegistrarEmpleado(controladorEmpleado);
        this.cambiarVista(vistaRegistrarEmpleado);
    }
    
    private void cambiarVista(JPanel panel) {
        this.jPanelPrincipal.removeAll();
        this.jPanelPrincipal.add(panel);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPrincipal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 430));

        jPanelPrincipal.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelPrincipal;
    // End of variables declaration//GEN-END:variables
}