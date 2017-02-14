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
            + "INSERT INTO asistencia "
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
            + "?,"
            + "?,"
            + "? "
            + ")";

    private static final String UPDATE_INGRESO_MANIANA = ""
            + "UPDATE asistencia SET "
            + "ingreso_maniana = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";

    private static final String UPDATE_SALIDA_MANIANA = ""
            + "UPDATE asistencia SET "
            + "salida_maniana = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";

    private static final String UPDATE_INGRESO_TARDE = ""
            + "UPDATE asistencia SET "
            + "ingreso_tarde = ? "
            + "WHERE "
            + "ci_empleado = ? AND "
            + "fecha_asistencia = ? ";

    private static final String UPDATE_SALIDA_TARDE = ""
            + "UPDATE asistencia SET "
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

    public int registrarAsistencia(int ciEmpleado, Date fecha, Time hora, String tipoAsistencia) throws SQLException {
        int registrado = REGISTRADO_ERROR;

        if (!this.asistenciaRegistrada(ciEmpleado, fecha)) {
            registrado = this.insertAsistencia(ciEmpleado, fecha, hora, tipoAsistencia);
        } else {
            registrado = this.actualizarAsistencia(ciEmpleado, fecha, hora, tipoAsistencia);
        }

        return registrado;
    }

    public int insertAsistencia(int ciEmpleado, Date fecha, Time hora, String tipoAsistencia) throws SQLException {
        int registrado = REGISTRADO_ERROR;

        if (!this.asistenciaRegistrada(ciEmpleado, fecha)) {
            switch (tipoAsistencia) {
                case INGRESO_MANIANA:
                    if (this.insertAsistencia(ciEmpleado, fecha, hora, null, null, null)) {
                        registrado = REGISTRADO_INGRESO_MANIANA;
                    }
                    break;
                case SALIDA_MANIANA:
                    if (this.insertAsistencia(ciEmpleado, fecha, null, hora, null, null)) {
                        registrado = REGISTRADO_SALIDA_MANIANA;
                    }
                    break;
                case INGRESO_TARDE:
                    if (this.insertAsistencia(ciEmpleado, fecha, null, null, hora, null)) {
                        registrado = REGISTRADO_INGRESO_TARDE;
                    }
                    break;
                case SALIDA_TARDE:
                    if (this.insertAsistencia(ciEmpleado, fecha, null, null, null, hora)) {
                        registrado = REGISTRADO_SALIDA_TARDE;
                    }
                    break;
            }
        }

        return registrado;
    }

    private boolean insertAsistencia(int ciEmpleado, Date fecha, Time ingresoManiana, Time salidaManiana, Time ingresoTarde, Time salidaTarde) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(INSERT_ASISTENCIA);
        pst.setInt(1, ciEmpleado);
        pst.setDate(2, fecha);
        pst.setTime(3, ingresoManiana);
        pst.setTime(4, salidaManiana);
        pst.setTime(5, ingresoTarde);
        pst.setTime(6, salidaTarde);

        return pst.executeUpdate() > 0;
    }

    public int actualizarAsistencia(int ciEmpleado, Date fecha, Time hora, String tipoAsistencia) throws SQLException {
        int registrado = REGISTRADO_ERROR;

        if (!this.asistenciaHorarioRegistrada(ciEmpleado, fecha, tipoAsistencia)) {
            registrado = this.updateAsistencia(ciEmpleado, fecha, hora, tipoAsistencia);
        } else {
            registrado = REGISTRADO_ANTERIOR;
        }

        return registrado;
    }

    public int updateAsistencia(int ciEmpleado, Date fecha, Time hora, String tipoAsistencia) throws SQLException {
        int registrado = REGISTRADO_ERROR;

        PreparedStatement pst = null;

        switch (tipoAsistencia) {
            case INGRESO_MANIANA:
                pst = this.getConexion().prepareStatement(UPDATE_INGRESO_MANIANA);
                break;
            case SALIDA_MANIANA:
                pst = this.getConexion().prepareStatement(UPDATE_SALIDA_MANIANA);
                break;
            case INGRESO_TARDE:
                pst = this.getConexion().prepareStatement(UPDATE_INGRESO_TARDE);
                break;
            case SALIDA_TARDE:
                pst = this.getConexion().prepareStatement(UPDATE_SALIDA_TARDE);
                break;
        }

        if (pst != null) {
            pst.setTime(1, hora);
            pst.setInt(2, ciEmpleado);
            pst.setDate(3, fecha);

            if (pst.executeUpdate() > 0) {
                switch (tipoAsistencia) {
                    case INGRESO_MANIANA:
                        registrado = REGISTRADO_INGRESO_MANIANA;
                        break;
                    case SALIDA_MANIANA:
                        registrado = REGISTRADO_SALIDA_MANIANA;
                        break;
                    case INGRESO_TARDE:
                        registrado = REGISTRADO_INGRESO_TARDE;
                        break;
                    case SALIDA_TARDE:
                        registrado = REGISTRADO_SALIDA_TARDE;
                        break;
                }
            }
        }

        return registrado;
    }

    private int selectCountAsistencia(int ciEmpleado, Date fecha) throws SQLException {
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

    private boolean asistenciaRegistrada(int ciEmpleado, Date fecha) throws SQLException {
        boolean res = false;

        if (this.selectCountAsistencia(ciEmpleado, fecha) > 0) {
            res = true;
        }

        return res;
    }

    public int selectCountAsistenciaHorario(int ciEmpleado, Date fecha, String horarioAsistencia) throws SQLException {
        int res = 0;

        //seleccionamos el horario de trabajo
        String sql = SELECT_COUNT_ASISTENCIA_HORARIO;

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
