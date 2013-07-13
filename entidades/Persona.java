package entidades;

public class Persona {

	private int id;
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private String cedula;
	private String especialidad;
	private String codigoEmpleado;
	private String fecha_nacimiento;
	private String fumador;
	private String alergias;
	private String foto;

	public Persona(int id, String nombre, String apellido, String telefono,
			String direccion, String cedula, String especialidad,
			String codigoEmpleado) {

		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula = cedula;
		this.especialidad = especialidad;
		this.codigoEmpleado = codigoEmpleado;
	}

	public Persona(int id, String nombre, String apellido, String telefono,
			String direccion, String cedula, String codigoEmpleado) {

		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula = cedula;

		this.codigoEmpleado = codigoEmpleado;
	}

	public Persona(int id, String nombre, String apellido, String telefono,
			String direccion, String cedula, String fecha_nacimiento,
			String fumador, String foto, String alergias) {

		this.setId(id);
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula = cedula;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fumador = fumador;
		this.alergias = alergias;
		this.foto = foto;
	}

	public Persona(String nombre, String apellido, String telefono,
			String direccion, String cedula, String especialidad,
			String codigoEmpleado) {

		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula = cedula;
		this.especialidad = especialidad;
		this.codigoEmpleado = codigoEmpleado;
	}

	public Persona(String nombre, String apellido, String telefono,
			String direccion, String cedula, String codigoEmpleado) {

		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula = cedula;

		this.codigoEmpleado = codigoEmpleado;
	}

	public Persona(String nombre, String apellido, String telefono,
			String direccion, String cedula, String fecha_nacimiento,
			String fumador, String foto, String alergias) {

		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula = cedula;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fumador = fumador;
		this.alergias = alergias;
		this.foto = foto;
	}

	public Persona() {
		// TODO Auto-generated constructor stub
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getFumador() {
		return fumador;
	}

	public void setFumador(String fumador) {
		this.fumador = fumador;
	}

	public String getAlergias() {
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
