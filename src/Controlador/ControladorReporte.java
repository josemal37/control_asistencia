/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dominio.Empleado;
import Dominio.Reporte.DatosReporte;
import Dominio.Reporte.RegistroAsistencia;
import Modelo.ModeloAsistencia;
import Vista.JDialogReporte;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Jose
 */
public class ControladorReporte {

    JDialogReporte jDialogReporte;

    public ControladorReporte(JFrame padre) {
        this.jDialogReporte = new JDialogReporte(padre, true, this);
        this.jDialogReporte.setLocationRelativeTo(null);
        this.jDialogReporte.setVisible(true);
    }

    public ArrayList<DatosReporte> getDatosReporte(int mes, int anio) {
        ArrayList<DatosReporte> dr = new ArrayList<>();
        try {
            ModeloAsistencia m = new ModeloAsistencia();
            dr = m.selectAsistenciaMes(mes, anio);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dr;
    }

    public void GuardarReporte(int mes, int anio, File archivo) {
        try {
            if (!(archivo.getName().substring(archivo.getName().lastIndexOf('.') + 1)).equalsIgnoreCase("pdf")) {
                archivo = new File(archivo.toString() + ".pdf");
            }
            FileOutputStream fos = new FileOutputStream(archivo);
            Document documento = new Document();
            ArrayList<DatosReporte> datos = this.getDatosReporte(mes, anio);

            PdfWriter.getInstance(documento, fos).setInitialLeading(20);

            Font regular = new Font(FontFamily.HELVETICA, 12);
            Font negrilla = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
            Font h1 = new Font(FontFamily.HELVETICA, 24, Font.BOLD);

            documento.open();

            for (int i = 0; i < datos.size(); i++) {
                DatosReporte dr = datos.get(i);
                Empleado e = dr.getEmpleado();
                ArrayList<RegistroAsistencia> asistencia = dr.getRegistros();
                
                //titulo
                Paragraph titulo = new Paragraph();
                titulo.add(new Chunk("Reporte de asistencia", h1));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(15);
                documento.add(titulo);

                //periodo
                Paragraph fecha = new Paragraph();
                fecha.add(new Chunk("Periodo: ", negrilla));
                fecha.add(new Chunk(mes + " / " + anio, regular));
                documento.add(fecha);

                //nombre del empleado
                Paragraph nombreEmpleado = new Paragraph();
                nombreEmpleado.add(new Chunk("Empleado: ", negrilla));
                nombreEmpleado.add(new Chunk(e.getNombre(), regular));
                nombreEmpleado.add(new Chunk(" " + e.getApellidoPaterno(), regular));
                nombreEmpleado.add(new Chunk(" " + e.getApellidoMaterno(), regular));
                documento.add(nombreEmpleado);
                
                //tabla de datos
                PdfPTable tabla = new PdfPTable(5);
                tabla.setSpacingAfter(15);
                tabla.setSpacingBefore(15);
                tabla.addCell("Fecha");
                tabla.addCell("Ingreso mañana");
                tabla.addCell("Salida mañana");
                tabla.addCell("Ingreso tarde");
                tabla.addCell("Salida tarde");
                for (int j = 0; j < asistencia.size(); j++) {
                    RegistroAsistencia r = asistencia.get(j);
                    tabla.addCell(r.getFecha());
                    tabla.addCell(r.getIngresoManiana());
                    tabla.addCell(r.getSalidaManiana());
                    tabla.addCell(r.getIngresoTarde());
                    tabla.addCell(r.getSalidaTarde());
                }
                documento.add(tabla);
                
                //nueva pagina
                documento.newPage();
            }

            documento.close();
        } catch (DocumentException ex) {
            Logger.getLogger(ControladorReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JDialogReporte getjDialogReporte() {
        return jDialogReporte;
    }

    public void setjDialogReporte(JDialogReporte jDialogReporte) {
        this.jDialogReporte = jDialogReporte;
    }
    
    public void cerrar() {
        this.jDialogReporte.dispose();
    }
}
