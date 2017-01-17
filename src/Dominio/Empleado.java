/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dominio;

/**
 *
 * @author Jose
 */
public class Empleado {
    
    private int ci;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoEmpleado;
    
    public Empleado() {
        
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }
    
    @Override
    public String toString() {
        String res = this.nombre;
        
        if(this.apellidoPaterno != null) {
            res = res + " " + this.apellidoPaterno;
        }
        if(this.apellidoMaterno != null) {
            res = res + " " + this.apellidoMaterno;
        }
        return res;
    }
}
