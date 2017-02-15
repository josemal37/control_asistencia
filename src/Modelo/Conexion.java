/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jose
 */
public class Conexion {

    public static final String URL = "jdbc:postgresql://%s:%s/%s";
    public static final String DEFAULT_DATABASE = "fundatca_personal";
    public static final String DEFAULT_DATABASE_IP = "127.0.0.1";
    public static final String DEFAULT_DATABASE_USERNAME = "fundatca_per";
    public static final String DEFAULT_DATABASE_PASSWORD = "personal123";
    public static final String DEFAULT_DATABASE_PORT = "5432";
    Connection conexion = null;
    
    public Conexion() throws ClassNotFoundException, SQLException {
        
        String databaseIP = DEFAULT_DATABASE_IP;
        String databasePort = DEFAULT_DATABASE_PORT;
        String database = DEFAULT_DATABASE;
        String databaseUsername = DEFAULT_DATABASE_USERNAME;
        String databasePassword = DEFAULT_DATABASE_PASSWORD;
        
        Class.forName("org.postgresql.Driver");
        String url = String.format(URL, databaseIP, databasePort, database);
        conexion = DriverManager.getConnection(url, databaseUsername, databasePassword);
    }
    
    public Connection getConexion() {
        return this.conexion;
    }
}
