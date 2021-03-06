package dao;

import java.util.*;
import java.sql.*;
import models.*;

public class DAO_Product extends DAO_Abstract {

	/**
	 * Obtiene todos los productos de la base de datos
	 * @return
	 */
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
	
	/**
	 * Obtiene la cantidad de productos que existen en la base de datos
	 * @return
	 */
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
	
	/**
	 * Obtiene el numero de stocks de la base de datos de un producto  
	 * @param idProduct
	 * @return
	 */
	public int getStockProduct(int idProduct) {
		int stockProduct = 0;
		try {
			rs = stm.executeQuery("SELECT Cantidad_Stock FROM supermercado_psp.producto WHERE ID_Producto = " + idProduct + ";");
			while(rs.next()) {
				stockProduct = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stockProduct;
	}
	
	/**
	 * Obtiene el nombre del producto mediante su id 
	 * @param idProduct
	 * @return
	 */
	public String getNameProduct(int idProduct) {
		String nameProduct = null;
		try {
			rs = stm.executeQuery("SELECT Nombre_Producto FROM supermercado_psp.producto WHERE ID_Producto = " + idProduct + ";");
			while(rs.next()) {
				nameProduct = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameProduct;
	}
	
	/**
	 * Obtiene el precio del proveedor mediante su id
	 * @param idProduct
	 * @return
	 */
	public int getPriceSupplierProduct(int idProduct) {
		int priceSupplier = 0;
		try {
			rs = stm.executeQuery("SELECT Precio_Proveedor FROM supermercado_psp.producto WHERE ID_Producto = " + idProduct + ";");
			while(rs.next()) {
				priceSupplier = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return priceSupplier;
	}
	
	/**
	 * Actualiza la cantidad de stock de un producto
	 * @param id
	 * @param amount
	 */
	public void updateProduct(int id, int amount) {
		try {
			stm.executeUpdate("UPDATE supermercado_psp.producto SET Cantidad_Stock = Cantidad_Stock -" + amount +" WHERE ID_Producto = " + id +";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el nombre y los precios mediante la id de un producto
	 * @param id
	 * @return
	 */
	public Product getNameAndDifferenceById(int id) {
		Product nameAndDifference = null;
		try {
			rs = stm.executeQuery("SELECT Nombre_Producto, Precio_Venta, Precio_Proveedor FROM supermercado_psp.producto WHERE `ID_Producto` = " + id + ";");
			while(rs.next()) {
				nameAndDifference = (new Product(rs.getString(1), rs.getInt(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL");
		}
		return nameAndDifference;
	}
}