package dao;

import java.util.*;
import java.sql.*;
import models.*;

public class DAO_Product extends DAO_Abstract {

	public ArrayList<Product> getProduct() {
		ArrayList<Product> dataProduct = new ArrayList<>();
		try {
			rs = stm.executeQuery("SELECT * FROM supermercado_psp.producto");
			while(rs.next()) {
				dataProduct.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch(SQLException ex) {
			System.out.println("Error en la consulta SQL");
		}
		return dataProduct;
	}
	
	public Object getCountProduct() {
		Object countProduct = new Object();
		try {
			rs = stm.executeQuery("SELECT COUNT(ID_Producto) FROM supermercado_psp.producto");
			while(rs.next()) {
				countProduct = rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countProduct;
	}
	
	public void updateProduct(int id, int amount) {
		try {
			stm.executeUpdate("UPDATE supermercado_psp.producto SET `Cantidad_Stock` = `Cantidad_Stock` -" + amount +" WHERE `producto`.`ID_Producto` = " + id +";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
