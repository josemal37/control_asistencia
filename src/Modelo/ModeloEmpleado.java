/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;

import Dominio.Empleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jose
 */
public class ModeloEmpleado extends Conexion {
    
    private static final String SELECT_EMPLEADO_POR_CI = ""
            + "SELECT "
            + "ci_empleado, "
            + "nombre_empleado, "
            + "apellido_paterno_empleado, "
            + "apellido_materno_empleado "
            + "FROM "
            + "empleado "
            + "WHERE "
            + "ci_empleado = ? ";
    
    public ModeloEmpleado() throws ClassNotFoundException, SQLException {
        super();
    }
    
    public Empleado selectEmpleadoPorCI(int ciEmpleado) throws SQLException {
        Empleado e = null;
        
        PreparedStatement pst = this.getConexion().prepareStatement(SELECT_EMPLEADO_POR_CI);
        pst.setInt(1, ciEmpleado);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            e = new Empleado();
            e.setCi(rs.getInt("ci_empleado"));
            e.setNombre(rs.getString("nombre_empleado"));
            e.setApellidoPaterno(rs.getString("apellido_paterno_empleado"));
            e.setApellidoMaterno(rs.getString("apellido_materno_empleado"));
        }
        return e;
    }
}
