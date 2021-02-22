package dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.sql.*;
import models.*;

public class DAO_Purchase extends DAO_Abstract {
	java.util.Date date = new Date();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Obtiene todas las compras de la base de datos
	 * @return
	 */
	public ArrayList<Purchase> getPurchase(){
		ArrayList<Purchase> dataPurchase = new ArrayList<>();
		try {
			rs = stm.executeQuery("SELECT * FROM supermercado_psp.compra");
			while (rs.next()) {
				dataPurchase.add(new Purchase(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException ex) {
			System.out.println("Error en la consulta SQL");
		}
		return dataPurchase;
	}
	
	/**
	 * Guarda la compra en la base de datos 
	 * @param idProduct
	 * @param amountProduct
	 * @param idEmployee
	 */
	public void insertPurchase(int idProduct, int amountProduct, int idEmployee) {
		try {
			stm.executeUpdate("INSERT INTO supermercado_psp.compra (`Fecha`, `ID_Producto`, `Cantidad_Producto`, `ID_Empleado`) VALUES ('" + dateFormat.format(date) + "', " + idProduct + ", " + amountProduct + ", " + idEmployee + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtiene la cantidad de compras de hoy
	 * @return
	 */
	public int getCountPurchaseToday() {
		int countPurchase = 0;
		try {
			rs = stm.executeQuery("SELECT COUNT(ID_Producto) FROM `compra` WHERE `Fecha` = '" + dateFormat.format(date) + "';");
			while(rs.next()) {
				countPurchase = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countPurchase;
	}
	
	/**
	 * Obtiene las compras de hoy
	 * @return
	 */
	public ArrayList<Purchase> getPurchaseToday() {
		ArrayList<Purchase> purchaseToday = new ArrayList<>();
		try {
			rs = stm.executeQuery("SELECT * FROM supermercado_psp.compra WHERE Fecha = '" + dateFormat.format(date) + "';");
			while(rs.next()) {
				purchaseToday.add(new Purchase(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL");
		}
		return purchaseToday;
	}
}