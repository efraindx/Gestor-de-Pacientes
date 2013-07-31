package com.efrain.gestorpacientes.entidades;

public class Paciente  {

	private int id;
	private String nombre;
	private String apellido;
	private String sexo;
	private String telefono;
	private String direccion;
	private String cedula;
	private String fecha_nacimiento;
	private String fumador;
	private String alergias;
	private String foto;

	public Paciente(int id, String nombre, String apellido, String sexo, String telefono,
			String direccion, String cedula, String fecha_nacimiento,
			String fumador, String foto, String alergias) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.setSexo(sexo);
		this.direccion = direccion;
		this.cedula = cedula;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fumador = fumador;
		this.foto = foto;
		this.alergias = alergias;
	}

	public Paciente(String nombre, String apellido, String sexo, String telefono,
			String direccion, String cedula, String fecha_nacimiento,
			String fumador, String foto, String alergias) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.setSexo(sexo);
		this.telefono = telefono;
		this.direccion = direccion;
		this.cedula = cedula;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fumador = fumador;
		this.foto = foto;
		this.alergias = alergias;
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

	public String getFumador() {
		return fumador;
	}

	public void setFumador(String fumador) {
		this.fumador = fumador;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
