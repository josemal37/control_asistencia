/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dominio.Reporte;

/**
 *
 * @author Jose
 */
public class RegistroAsistencia {
    
    private String fecha;
    private String ingresoManiana;
    private String salidaManiana;
    private String ingresoTarde;
    private String salidaTarde;

    public RegistroAsistencia() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIngresoManiana() {
        return ingresoManiana;
    }

    public void setIngresoManiana(String ingresoManiana) {
        this.ingresoManiana = ingresoManiana;
    }

    public String getSalidaManiana() {
        return salidaManiana;
    }

    public void setSalidaManiana(String salidaManiana) {
        this.salidaManiana = salidaManiana;
    }

    public String getIngresoTarde() {
        return ingresoTarde;
    }

    public void setIngresoTarde(String ingresoTarde) {
        this.ingresoTarde = ingresoTarde;
    }

    public String getSalidaTarde() {
        return salidaTarde;
    }

    public void setSalidaTarde(String salidaTarde) {
        this.salidaTarde = salidaTarde;
    }
}
