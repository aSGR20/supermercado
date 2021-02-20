package dao;

import java.util.*;
import java.sql.*;
import models.*;

public class DAO_Employee extends DAO_Abstract {
	java.util.Date dt = new java.util.Date();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	int id;
	String lastSession = sdf.format(dt);
	
	public ArrayList<Employee> getEmployee(){
		ArrayList<Employee> dataEmployee= new ArrayList<>();
        try {
            rs = stm.executeQuery("SELECT * FROM supermercado_psp.empleado");
            while(rs.next()) {
                dataEmployee.add(new Employee(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la consulta SQL");
        }
        return dataEmployee;
	}
}
