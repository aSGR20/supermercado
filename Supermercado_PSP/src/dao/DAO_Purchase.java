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
	
	public ArrayList<Purchase> getPurchase(){
		ArrayList<Purchase> dataPurchase = new ArrayList<>();
		try {
			rs = stm.executeQuery("SELECT * FROM supermercado_psp.compra");
			while (rs.next()) {
				dataPurchase.add(new Purchase(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
			}
		} catch (SQLException ex) {
			System.out.println("Error en la consulta SQL");
		}
		return dataPurchase;
	}
	
	public void insertPurchase(int idProduct, int idEmployee) {
		try {
			stm.executeUpdate("INSERT INTO supermercado_psp.compra (`Fecha`, `ID_Producto`, `ID_Empleado`) VALUES ('" + dateFormat.format(date) + "', " + idProduct + ", " + idEmployee + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Object getCountPurchaseToday() {
		Object countPurchase = new Object();
		try {
			rs = stm.executeQuery("SELECT COUNT(ID_Compra) FROM supermercado_psp.compra WHERE Fecha = '" + dateFormat.format(date) + "';");
			while(rs.next()) {
				countPurchase = rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countPurchase;
	}
	
	public ArrayList<Purchase> getPurchaseToday() {
		ArrayList<Purchase> purchaseToday = new ArrayList<>();
		try {
			rs = stm.executeQuery("SELECT * FROM supermercado_psp.compra WHERE Fecha = '" + dateFormat.format(date) + "';");
			while(rs.next()) {
				purchaseToday.add(new Purchase(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL");
		}
		return purchaseToday;
	}
}