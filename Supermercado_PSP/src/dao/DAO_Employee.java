package dao;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import models.*;

public class DAO_Employee extends DAO_Abstract {
	java.util.Date date = new Date();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	int id;
	
	/**
	 * Obtiene todos los empleados de la base de datos
	 * @return
	 */
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
	
	/**
	 * Inserta en la base de datos la ultima conexion
	 */
	public void insertLastSession(int idEmployee) {
		try {
			stm.executeUpdate("UPDATE supermercado_psp.empleado SET Ultima_Sesion = '" + dateFormat.format(date) + "' WHERE ID_Empleado = " + idEmployee +";");
		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL");
		}
	}
	
	/**
	 * Obtiene el empleado que corresponde con la id
	 * @param id
	 * @return
	 */
	public Object getEmployeeById(int id) {
		Object employee = new Object();
		employee = null;
		try {
			rs = stm.executeQuery("SELECT * FROM supermercado_psp.empleado WHERE `ID_Empleado` = " + id);
			while(rs.next()) {
				employee = rs.getObject(1);
			}
		}catch (SQLException ex) {
			System.out.println("Error en la consulta SQL");
		}
		return employee;
	}
}
