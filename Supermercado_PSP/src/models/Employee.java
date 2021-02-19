package models;

public class Employee {
	String _lastSession;
	int _id;
	
	public Employee(int id, String lastSession) {
		_id = id;
		_lastSession = lastSession;
	}
	
	public Employee(int id) {
		_id = id;
	}

	public String get_lastSession() {
		return _lastSession;
	}

	public void set_lastSession(String _lastSession) {
		this._lastSession = _lastSession;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
}
