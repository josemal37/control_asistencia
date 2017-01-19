/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vista;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Jose
 */
public class JTableGenerico extends JTable {
    
    JTableGenerico() {
        super();
    }
    
    public void setColumnas(String[] columnas) {
        DefaultTableModel modelo = new DefaultTableModel();
        for(int i = 0; i < columnas.length; i++) {
            modelo.addColumn(columnas[i]);
        }
        this.setModel(modelo);
    }
    
    public void addFila(Object[] fila) {
        DefaultTableModel modelo = (DefaultTableModel)this.getModel();
        modelo.addRow(fila);
    }
    
    public void addFilas(ArrayList filas) {
        for(int i = 0; i < filas.size(); i++) {
            this.addFila((Object[])filas.get(i));
        }
    }
}
