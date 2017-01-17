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

    private static final String SELECT_COUNT_ASISTENCIA_MANIANA = ""
            + "SELECT "
            + "COUNT(id_asistencia_maniana) as cantidad_asistencia "
            + "FROM "
            + "asistencia_maniana "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia_maniana = ? ";

    private static final String SELECT_COUNT_ASISTENCIA_TARDE = ""
            + "SELECT "
            + "COUNT(id_asistencia_tarde) as cantidad_asistencia "
            + "FROM "
            + "asistencia_tarde "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia_tarde = ? ";

    private static final String INSERT_ASISTENCIA_MANIANA = ""
            + "INSERT INTO asistencia_maniana "
            + "("
            + "ci_empleado, "
            + "fecha_asistencia_maniana, "
            + "ingreso_maniana, "
            + "salida_maniana "
            + ") "
            + "values "
            + "("
            + "?, "
            + "?, "
            + "?, "
            + "? "
            + ")";

    private static final String INSERT_ASISTENCIA_TARDE = ""
            + "INSERT INTO asistencia_tarde "
            + "("
            + "ci_empleado, "
            + "fecha_asistencia_tarde, "
            + "ingreso_tarde, "
            + "salida_tarde "
            + ") "
            + "values "
            + "("
            + "?, "
            + "?, "
            + "?, "
            + "? "
            + ")";

    private static final String UPDATE_SALIDA_MANIANA = ""
            + "UPDATE asistencia_maniana SET "
            + "salida_maniana = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia_maniana = ? ";

    private static final String UPDATE_INGRESO_TARDE = ""
            + "UPDATE ASISTENCIA SET "
            + "ingreso_tarde = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";
    private static final String UPDATE_SALIDA_TARDE = ""
            + "UPDATE asistencia_tarde SET "
            + "salida_tarde = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia_tarde = ? ";

    private static final String SELECT_COUNT_ASISTENCIA_HORARIO_MANIANA = ""
            + "SELECT "
            + "COUNT(id_asistencia_maniana) as cantidad_asistencia "
            + "FROM "
            + "asistencia_maniana "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia_maniana = ? AND "
            + "%s is not null ";

    private static final String SELECT_COUNT_ASISTENCIA_HORARIO_TARDE = ""
            + "SELECT "
            + "COUNT(id_asistencia_tarde) as cantidad_asistencia "
            + "FROM "
            + "asistencia_tarde "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia_tarde = ? AND "
            + "%s is not null ";

    //Tipos de asistencia segun la base de datos
    public static final String INGRESO_MANIANA = "ingreso_maniana";
    public static final String SALIDA_MANIANA = "salida_maniana";

    public static final String INGRESO_TARDE = "ingreso_tarde";
    public static final String SALIDA_TARDE = "salida_tarde";

    //Tipos de retorno de registro de asistencia
    public static final int REGISTRADO_INGRESO_MANIANA = 1;
    public static final int REGISTRADO_SALIDA_MANIANA = 2;
    public static final int REGISTRADO_INGRESO_TARDE = 3;
    public static final int REGISTRADO_SALIDA_TARDE = 4;
    public static final int REGISTRADO_ANTERIOR = 5;
    public static final int REGISTRADO_ERROR = -1;

    public ModeloAsistencia() throws ClassNotFoundException, SQLException {
        super();
    }

    public int selectCountAsistenciaManiana(int ciEmpleado, Date fecha) throws SQLException {
        int res = 0;

        PreparedStatement pst = this.getConexion().prepareStatement(SELECT_COUNT_ASISTENCIA_MANIANA);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            res = rs.getInt("cantidad_asistencia");
        }

        return res;
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

    public boolean asistenciaRegistradaManiana(int ciEmpleado, Date fecha) throws SQLException {
        boolean res = false;

        if (this.selectCountAsistenciaManiana(ciEmpleado, fecha) > 0) {
            res = true;
        }

        return res;
    }

    public boolean asistenciaRegistradaTarde(int ciEmpleado, Date fecha) throws SQLException {
        boolean res = false;

        if (this.selectCountAsistenciaTarde(ciEmpleado, fecha) > 0) {
            res = true;
        }

        return res;
    }

    public boolean insertAsistencia(int ciEmpleado, Date fecha, Time ingresoManiana, Time salidaManiana, Time ingresoTarde, Time salidaTarde) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(INSERT_ASISTENCIA_MANIANA);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        pst.setTime(3, ingresoManiana);
        pst.setTime(4, salidaManiana);
        pst.setTime(5, ingresoTarde);
        pst.setTime(6, salidaTarde);

        return pst.executeUpdate() > 0;
    }

    public int insertAsistenciaManiana(int ciEmpleado, Date fecha, Time hora) throws SQLException {
        int registrado = REGISTRADO_ERROR;
        if (this.asistenciaRegistradaManiana(ciEmpleado, fecha)) {
            registrado = this.updateSalidaManiana(ciEmpleado, fecha, hora);
        } else {
            registrado = this.insertIngresoManiana(ciEmpleado, fecha, hora);
        }
        return registrado;
    }

    public int insertIngresoManiana(int ciEmpleado, Date fecha, Time ingresoManiana) throws SQLException {
        int registrado = REGISTRADO_ERROR;

        PreparedStatement pst = this.getConexion().prepareStatement(INSERT_ASISTENCIA_MANIANA);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        pst.setTime(3, ingresoManiana);
        pst.setTime(4, null);

        if (pst.executeUpdate() > 0) {
            registrado = REGISTRADO_INGRESO_MANIANA;
        }
        return registrado;
    }

    public int updateSalidaManiana(int ci_empleado, Date fecha, Time salidaManiana) throws SQLException {
        int registrado = REGISTRADO_ERROR;

        if (!this.asistenciaHorarioRegistrada(ci_empleado, fecha, SALIDA_MANIANA)) {
            PreparedStatement pst = this.getConexion().prepareStatement(UPDATE_SALIDA_MANIANA);
            pst.setTime(1, salidaManiana);
            pst.setInt(2, ci_empleado);
            pst.setDate(3, fecha);

            if (pst.executeUpdate() > 0) {
                registrado = REGISTRADO_SALIDA_MANIANA;
            }
        } else {
            registrado = REGISTRADO_ANTERIOR;
        }

        return registrado;
    }

    public int insertAsistenciaTarde(int ciEmpleado, Date fecha, Time hora) throws SQLException {
        int registrado = REGISTRADO_ERROR;
        if (this.asistenciaHorarioRegistrada(ciEmpleado, fecha, INGRESO_TARDE)) {
            registrado = this.updateSalidaTarde(ciEmpleado, fecha, hora);
        } else {
            registrado = this.insertIngresoTarde(ciEmpleado, fecha, hora);
        }
        return registrado;
    }

    public int insertIngresoTarde(int ciEmpleado, Date fecha, Time ingresoTarde) throws SQLException {
        int registrado = REGISTRADO_ERROR;
        
        PreparedStatement pst = this.getConexion().prepareStatement(INSERT_ASISTENCIA_TARDE);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        pst.setTime(3, ingresoTarde);
        pst.setTime(4, null);

        if (pst.executeUpdate() > 0) {
            registrado = REGISTRADO_INGRESO_TARDE;
        }
        return registrado;
    }

    public int updateSalidaTarde(int ciEmpleado, Date fecha, Time salidaTarde) throws SQLException {
        int registrado = REGISTRADO_ERROR;
        if (!this.asistenciaHorarioRegistrada(ciEmpleado, fecha, SALIDA_TARDE)) {
            PreparedStatement pst = this.getConexion().prepareStatement(UPDATE_SALIDA_TARDE);
            pst.setTime(1, salidaTarde);
            pst.setInt(2, ciEmpleado);
            pst.setDate(3, fecha);

            if (pst.executeUpdate() > 0) {
                registrado = REGISTRADO_SALIDA_TARDE;
            }
        } else {
            registrado = REGISTRADO_ANTERIOR;
        }

        return registrado;
    }

    public int selectCountAsistenciaHorario(int ciEmpleado, Date fecha, String horarioAsistencia) throws SQLException {
        int res = 0;

        //seleccionamos el horario de trabajo
        String sql = "";
        if (horarioAsistencia.equals(INGRESO_MANIANA) || horarioAsistencia.equals(SALIDA_MANIANA)) {
            sql = SELECT_COUNT_ASISTENCIA_HORARIO_MANIANA;
        } else if (horarioAsistencia.equals(INGRESO_TARDE) || horarioAsistencia.equals(SALIDA_TARDE)) {
            sql = SELECT_COUNT_ASISTENCIA_HORARIO_TARDE;
        }

        //formateamos la cadena segun el horario
        sql = String.format(sql, horarioAsistencia);

        PreparedStatement pst = this.getConexion().prepareStatement(sql);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            res = rs.getInt("cantidad_asistencia");
        }

        return res;
    }

    public boolean asistenciaHorarioRegistrada(int ciEmpleado, Date fecha, String horarioAsistencia) throws SQLException {
        boolean res = false;

        if (this.selectCountAsistenciaHorario(ciEmpleado, fecha, horarioAsistencia) > 0) {
            res = true;
        }

        return res;
    }
}
