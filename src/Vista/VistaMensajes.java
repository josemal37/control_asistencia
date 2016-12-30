/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vista;

import javax.swing.JOptionPane;

/**
 *
 * @author Jose
 */
public class VistaMensajes extends JOptionPane {
    
    public VistaMensajes() {
        super();
    }
    
    public static void mostrarMensaje(String message) {
        VistaMensajes.showMessageDialog(null, message);
    }
}
