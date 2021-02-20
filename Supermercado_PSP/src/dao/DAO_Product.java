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
}
