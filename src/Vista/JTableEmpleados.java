/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vista;

import Dominio.Empleado;
import java.util.ArrayList;

/**
 *
 * @author Jose
 */
public class JTableEmpleados extends JTableGenerico {

    private static final String[] COLUMNAS_EMPLEADOS = {
        "CI", 
        "Nombre", 
        "Apellido paterno", 
        "Apellido materno",
        "Tipo de empleado"
    };
    
    public JTableEmpleados() {
        super();
        this.setColumnas(COLUMNAS_EMPLEADOS);
    }
    
    public void addEmpleado(Empleado empleado) {
        Object[] fila = new Object[COLUMNAS_EMPLEADOS.length];
        //Segun el orden de las columnas
        fila[0] = empleado.getCi();
        fila[1] = empleado.getNombre();
        fila[2] = empleado.getApellidoPaterno();
        fila[3] = empleado.getApellidoMaterno();
        fila[4] = empleado.getNombreTipoEmpleado();
        
        this.addFila(fila);
    }
    
    public void addEmpleados(ArrayList<Empleado> empleados) {
        for(int i = 0; i < empleados.size(); i++) {
            this.addEmpleado(empleados.get(i));
        }
    }
    
    @Override
    public boolean isCellEditable(int i, int j) {
        return false;
    }
}
