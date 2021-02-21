package dao;

import java.util.*;
import java.sql.*;
import models.*;

public class DAO_Purchase extends DAO_Abstract {
	java.util.Date dt = new java.util.Date();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String lastSession = sdf.format(dt);
	
	public ArrayList<Purchase> getPurchase(){
		ArrayList<Purchase> dataPurchase = new ArrayList<>();
		try {
			rs = stm.executeQuery("SELECT * FROM supermercado_psp.compra");
			while (rs.next()) {
				dataPurchase.add(new Purchase(rs.getInt(1), rs.getString(2), rs.getObject(3), rs.getObject(4)));
			}
		} catch (SQLException ex) {
			System.out.println("Error en la consulta SQL");
		}
		return dataPurchase;
	}
	
	public void insertPurchase() {
		
	}
}