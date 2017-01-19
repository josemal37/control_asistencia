/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dominio.Empleado;
import Dominio.TipoEmpleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jose
 */
public class ModeloEmpleado extends Conexion {

    private static final String SELECT_EMPLEADO_POR_CI = ""
            + "SELECT "
            + "e.ci_empleado, "
            + "e.nombre_empleado, "
            + "e.apellido_paterno_empleado, "
            + "e.apellido_materno_empleado, "
            + "t.id_tipo_empleado, "
            + "t.nombre_tipo_empleado "
            + "FROM "
            + "empleado e "
            + "INNER JOIN tipo_empleado t ON "
            + "t.id_tipo_empleado = e.id_tipo_empleado "
            + "WHERE "
            + "ci_empleado = ? ";
    
    private static final String INSERT_EMPLEADO = ""
            + "INSERT INTO empleado "
            + "( "
            + "ci_empleado, "
            + "nombre_empleado, "
            + "apellido_paterno_empleado, "
            + "apellido_materno_empleado, "
            + "id_tipo_empleado "
            + ") "
            + "VALUES"
            + "( "
            + "?, "
            + "?, "
            + "?, "
            + "?, "
            + "? "
            + ") ";
    
    private static final String SELECT_EMPLEADOS = ""
            + "SELECT "
            + "e.ci_empleado, "
            + "e.nombre_empleado, "
            + "e.apellido_paterno_empleado, "
            + "e.apellido_materno_empleado, "
            + "t.id_tipo_empleado, "
            + "t.nombre_tipo_empleado "
            + "FROM "
            + "empleado e "
            + "INNER JOIN tipo_empleado t ON "
            + "t.id_tipo_empleado = e.id_tipo_empleado ";
    
    private static final String SELECT_TIPOS_EMPLEADO = ""
            + "SELECT "
            + "id_tipo_empleado, "
            + "nombre_tipo_empleado "
            + "FROM "
            + "tipo_empleado ";

    public ModeloEmpleado() throws ClassNotFoundException, SQLException {
        super();
    }

    public Empleado selectEmpleadoPorCI(int ciEmpleado) throws SQLException {
        Empleado e = null;

        PreparedStatement pst = this.getConexion().prepareStatement(SELECT_EMPLEADO_POR_CI);
        pst.setInt(1, ciEmpleado);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            e = new Empleado();
            e.setCi(rs.getInt("ci_empleado"));
            e.setNombre(rs.getString("nombre_empleado"));
            e.setApellidoPaterno(rs.getString("apellido_paterno_empleado"));
            e.setApellidoMaterno(rs.getString("apellido_materno_empleado"));
            e.setIdTipoEmpleado(rs.getInt("id_tipo_empleado"));
            e.setNombreTipoEmpleado(rs.getString("nombre_tipo_empleado"));
        }
        return e;
    }
    
    public ArrayList<Empleado> selectEmpleados() throws SQLException {
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        PreparedStatement pst = this.getConexion().prepareStatement(SELECT_EMPLEADOS);
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()) {
            Empleado e = new Empleado();
            e.setCi(rs.getInt("ci_empleado"));
            e.setNombre(rs.getString("nombre_empleado"));
            e.setApellidoPaterno(rs.getString("apellido_paterno_empleado"));
            e.setApellidoMaterno(rs.getString("apellido_materno_empleado"));
            e.setIdTipoEmpleado(rs.getInt("id_tipo_empleado"));
            e.setNombreTipoEmpleado(rs.getString("nombre_tipo_empleado"));
            empleados.add(e);
        }
        
        return empleados;
    }

    public ArrayList<TipoEmpleado> selectTiposEmpleado() throws SQLException {
        ArrayList<TipoEmpleado> tipos = new ArrayList<>();
        
        PreparedStatement pst = this.getConexion().prepareStatement(SELECT_TIPOS_EMPLEADO);
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()) {
            TipoEmpleado t = new TipoEmpleado();
            t.setId(rs.getInt("id_tipo_empleado"));
            t.setNombreTipoEmpleado(rs.getString("nombre_tipo_empleado"));
            tipos.add(t);
        }
        
        return tipos;
    }
    
    public boolean insertEmpleado(Empleado e) throws SQLException {
        PreparedStatement pst = this.getConexion().prepareStatement(INSERT_EMPLEADO);
        pst.setInt(1, e.getCi());
        pst.setString(2, e.getNombre());
        pst.setString(3, e.getApellidoPaterno());
        pst.setString(4, e.getApellidoMaterno());
        pst.setInt(5, e.getIdTipoEmpleado());
        
        return pst.executeUpdate() > 0;
    }
}
