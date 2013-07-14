package entidades;

public class Administrador {

	private int id;
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private String cedula;
	private String codigoEmpleado;

	public Administrador(int id, String nombre, String apellido,
			String telefono, String direccion, String cedula,
			String codigoEmpleado) {

	}

	public Administrador(String nombre, String apellido, String telefono,
			String direccion, String cedula, String codigoEmpleado) {

	}

	public Administrador() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

}
