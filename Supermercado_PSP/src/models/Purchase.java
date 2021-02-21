package models;

public class Purchase {
	int _id;
	String _fecha;
	int _idProduct;
	int _idEmployee;
	
	public Purchase(int id, String fecha, int product, int employee) {
		_id = id;
		_fecha = fecha;
		_idProduct = product;
		_idEmployee = employee;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_fecha() {
		return _fecha;
	}

	public void set_fecha(String _fecha) {
		this._fecha = _fecha;
	}

	public int get_idProduct() {
		return _idProduct;
	}

	public void set_idProduct(int _product) {
		this._idProduct = _product;
	}

	public int get_idEmployee() {
		return _idEmployee;
	}

	public void set_idEmployee(int _employee) {
		this._idEmployee = _employee;
	}
	
	public String toString() {
		return "ID Compra: " + _id + "\tID Producto: " + _idProduct + "\tID Empleado: " + _idEmployee;
	}
}