package dao;
import java.sql.*;
import java.util.*;
import models.*;

public abstract class DAO_Abstract {

    private static final String controller = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost/supermercado_psp";
    private static final String user = "root";
    private static final String password = "";

    Connection cn = null;
    Statement stm = null;
    ResultSet rs = null;

    public DAO_Abstract() {
        try {
            cn= DriverManager.getConnection(url, user, password);
            stm = cn.createStatement();
            System.out.println("Conexion realizada");
        } catch (SQLException ex) {
            System.out.println("Error en la conexión");
        }
    }
    
    static {
        try {
            Class.forName(controller);
            System.out.println("Controlador Cargado");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador com.mysql.jdbc.Driver");
        }
    }
}