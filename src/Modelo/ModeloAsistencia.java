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

    private static final String SELECT_COUNT_ASISTENCIA_HORARIO = ""
            + "SELECT "
            + "COUNT(id_asistencia) as cantidad_asistencia "
            + "FROM "
            + "asistencia "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? AND "
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

    public boolean insertAsistencia(int ciEmpleado, Date fecha, Time ingresoManiana, Time salidaManiana, Time ingresoTarde, Time salidaTarde) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(INSERT_ASISTENCIA);
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
        if (this.asistenciaRegistrada(ciEmpleado, fecha)) {
            registrado = this.updateSalidaManiana(ciEmpleado, fecha, hora);
        } else {
            registrado = this.insertIngresoManiana(ciEmpleado, fecha, hora);
        }
        return registrado;
    }

    public int insertIngresoManiana(int ciEmpleado, Date fecha, Time ingresoManiana) throws SQLException {
        int registrado = REGISTRADO_ERROR;
        if (this.insertAsistencia(ciEmpleado, fecha, ingresoManiana, null, null, null)) {
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
        if (this.asistenciaRegistrada(ciEmpleado, fecha)) {
            if (this.asistenciaHorarioRegistrada(ciEmpleado, fecha, INGRESO_TARDE)) {
                registrado = this.updateSalidaTarde(ciEmpleado, fecha, hora);
            } else {
                registrado = this.updateIngresoTarde(ciEmpleado, fecha, hora);
            }
        } else {
            registrado = this.insertIngresoTarde(ciEmpleado, fecha, hora);
        }
        return registrado;
    }

    public int insertIngresoTarde(int ciEmpleado, Date fecha, Time ingresoTarde) throws SQLException {
        int registrado = REGISTRADO_ERROR;
        if (this.asistenciaRegistrada(ciEmpleado, fecha)) {
            registrado = this.updateIngresoTarde(ciEmpleado, fecha, ingresoTarde);
        } else {
            if (this.insertAsistencia(ciEmpleado, fecha, null, null, ingresoTarde, null)) {
                registrado = REGISTRADO_INGRESO_TARDE;
            }
        }
        return registrado;
    }

    public int updateIngresoTarde(int ciEmpleado, Date fecha, Time ingresoTarde) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(UPDATE_INGRESO_TARDE);
        pst.setTime(1, ingresoTarde);
        pst.setInt(2, ciEmpleado);
        pst.setDate(3, fecha);

        int registrado = REGISTRADO_ERROR;
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

        //formateamos la cadena segun el horario
        String sql = SELECT_COUNT_ASISTENCIA_HORARIO;
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
