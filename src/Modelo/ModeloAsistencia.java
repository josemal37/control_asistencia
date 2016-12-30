/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 *
 * @author Jose
 */
public class ModeloAsistencia extends Conexion {

    private static final String SELECT_ASISTENCIA = ""
            + "SELECT "
            + "id_asistencia, "
            + "ci_empleado, "
            + "fecha_asistencia, "
            + "ingreso_maniana, "
            + "salida_maniana, "
            + "ingreso_tarde, "
            + "salida_tarde "
            + "FROM "
            + "asistencia "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";

    private static final String SELECT_COUNT_ASISTENCIA = ""
            + "SELECT "
            + "COUNT(id_asistencia) as cantidad_asistencia "
            + "FROM "
            + "asistencia "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";

    private static final String INSERT_ASISTENCIA = ""
            + "INSERT INTO ASISTENCIA "
            + "("
            + "ci_empleado, "
            + "fecha_asistencia, "
            + "ingreso_maniana, "
            + "salida_maniana, "
            + "ingreso_tarde, "
            + "salida_tarde "
            + ") "
            + "values "
            + "("
            + "?, "
            + "?, "
            + "?, "
            + "?, "
            + "?, "
            + "? "
            + ")";

    private static final String UPDATE_SALIDA_MANIANA = ""
            + "UPDATE ASISTENCIA SET "
            + "salida_maniana = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";

    private static final String UPDATE_INGRESO_TARDE = ""
            + "UPDATE ASISTENCIA SET "
            + "ingreso_tarde = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";
    private static final String UPDATE_SALIDA_TARDE = ""
            + "UPDATE ASISTENCIA SET "
            + "salida_tarde = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";
    
    private static final String SELECT_COUNT_ASISTENCIA_TARDE = ""
            + "SELECT "
            + "COUNT(id_asistencia) as cantidad_asistencia "
            + "FROM "
            + "asistencia "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? AND "
            + "ingreso_tarde is not null ";

    public ModeloAsistencia() throws ClassNotFoundException, SQLException {
        super();
    }

    public int selectCountAsistencia(int ciEmpleado, Date fecha) throws SQLException {
        int res = 0;

        PreparedStatement pst = this.getConexion().prepareStatement(SELECT_COUNT_ASISTENCIA);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            res = rs.getInt("cantidad_asistencia");
        }

        return res;
    }

    public boolean asistenciaRegistrada(int ciEmpleado, Date fecha) throws SQLException {
        boolean res = false;

        if (this.selectCountAsistencia(ciEmpleado, fecha) > 0) {
            res = true;
        }

        return res;
    }

    public void insertAsistencia(int ciEmpleado, Date fecha, Time ingresoManiana, Time salidaManiana, Time ingresoTarde, Time salidaTarde) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(INSERT_ASISTENCIA);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        pst.setTime(3, ingresoManiana);
        pst.setTime(4, salidaManiana);
        pst.setTime(5, ingresoTarde);
        pst.setTime(6, salidaTarde);

        pst.execute();
    }
    
    public void insertAsistenciaManiana(int ciEmpleado, Date fecha, Time hora) throws SQLException {
        if(this.asistenciaRegistrada(ciEmpleado, fecha)) {
            this.updateSalidaManiana(ciEmpleado, fecha, hora);
        } else {
            this.insertIngresoManiana(ciEmpleado, fecha, hora);
        }
    }

    public void insertIngresoManiana(int ciEmpleado, Date fecha, Time ingresoManiana) throws SQLException {
        this.insertAsistencia(ciEmpleado, fecha, ingresoManiana, null, null, null);
    }
    
    public void updateSalidaManiana(int ci_empleado, Date fecha, Time salidaManiana) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(UPDATE_SALIDA_MANIANA);
        pst.setTime(1, salidaManiana);
        pst.setInt(2, ci_empleado);
        pst.setDate(3, fecha);
        
        pst.execute();
    }
    
    public void insertAsistenciaTarde(int ciEmpleado, Date fecha, Time hora) throws SQLException {
        if(this.asistenciaRegistrada(ciEmpleado, fecha)) {
            if(this.asistenciaTardeRegistrada(ciEmpleado, fecha)) {
                this.updateSalidaTarde(ciEmpleado, fecha, hora);
            } else {
                this.updateIngresoTarde(ciEmpleado, fecha, hora);
            }
        } else {
            this.insertIngresoTarde(ciEmpleado, fecha, hora);
        }
    }

    public void insertIngresoTarde(int ciEmpleado, Date fecha, Time ingresoTarde) throws SQLException {
        if (this.asistenciaRegistrada(ciEmpleado, fecha)) {
            this.updateIngresoTarde(ciEmpleado, fecha, ingresoTarde);
        } else {
            this.insertAsistencia(ciEmpleado, fecha, null, null, ingresoTarde, null);
        }
    }
    
    public void updateIngresoTarde(int ciEmpleado, Date fecha, Time ingresoTarde) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(UPDATE_INGRESO_TARDE);
        pst.setTime(1, ingresoTarde);
        pst.setInt(2, ciEmpleado);
        pst.setDate(3, fecha);
        
        pst.execute();
    }
    
    public void updateSalidaTarde(int ciEmpleado, Date fecha, Time salidaTarde) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(UPDATE_SALIDA_TARDE);
        pst.setTime(1, salidaTarde);
        pst.setInt(2, ciEmpleado);
        pst.setDate(3, fecha);
        
        pst.execute();
    }
    
    public int selectCountAsistenciaTarde(int ciEmpleado, Date fecha) throws SQLException {
        int res = 0;

        PreparedStatement pst = this.getConexion().prepareStatement(SELECT_COUNT_ASISTENCIA_TARDE);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            res = rs.getInt("cantidad_asistencia");
        }

        return res;
    }
    
    public boolean asistenciaTardeRegistrada(int ciEmpleado, Date fecha) throws SQLException {
        boolean res = false;
        
        if(this.selectCountAsistenciaTarde(ciEmpleado, fecha) > 0) {
            res = true;
        }
        
        return res;
    }
}
