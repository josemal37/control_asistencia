/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dominio.Reporte;

import Dominio.Empleado;
import java.util.ArrayList;

/**
 *
 * @author Jose
 */
public class DatosReporte {
    
    private Empleado empleado;
    
    private ArrayList<RegistroAsistencia> registros;

    public DatosReporte() {
        registros = new ArrayList<>();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public ArrayList<RegistroAsistencia> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<RegistroAsistencia> registros) {
        this.registros = registros;
    }
    
    public void addRegistro(RegistroAsistencia r) {
        this.registros.add(r);
    }
}
