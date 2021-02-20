package models;

public class Product {
	int _id;
	String _nombreProducto;
	int _precioVenta;
	int _precioProveedor;
	int _cantidadStock;
	
	public Product(int id, String nombreProducto, int precioVenta, int precioProveedor, int cantidadStock) {
		_id = id;
		_nombreProducto = nombreProducto;
		_precioVenta = precioVenta;
		_precioProveedor = precioProveedor;
		_cantidadStock = cantidadStock;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_nombreProducto() {
		return _nombreProducto;
	}

	public void set_nombreProducto(String _nombreProducto) {
		this._nombreProducto = _nombreProducto;
	}

	public int get_precioVenta() {
		return _precioVenta;
	}

	public void set_precioVenta(int _precioVenta) {
		this._precioVenta = _precioVenta;
	}

	public int get_precioProveedor() {
		return _precioProveedor;
	}

	public void set_precioProveedor(int _precioProveedor) {
		this._precioProveedor = _precioProveedor;
	}

	public int get_cantidadStock() {
		return _cantidadStock;
	}

	public void set_cantidadStock(int _cantidadStock) {
		this._cantidadStock = _cantidadStock;
	}
}