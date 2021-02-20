package models;

public class Purchase {
	int _id;
	String _fecha;
	Product _product;
	Employee _employee;
	
	public Purchase(int id, String fecha, Object product, Object employee) {
		_id = id;
		_fecha = fecha;
		_product = (Product) product;
		_employee = (Employee) employee;
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

	public Product get_product() {
		return _product;
	}

	public void set_product(Product _product) {
		this._product = _product;
	}

	public Employee get_employee() {
		return _employee;
	}

	public void set_employee(Employee _employee) {
		this._employee = _employee;
	}
}