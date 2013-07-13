package com.efrain.gestorpacientes.entidades;

public class Usuario {

	private int id;
	private String cod_empleado;
	private String nombre;
	private String apellido;
	private String rol;
	private String direccion;
	private String cedula;
	private String especialidad;
	private String telefonos;

	public Usuario(int id, String cod_empleado, String nombre, String apellido,
			String rol, String direccion, String cedula, String especialidad, 
			String telefonos) {
		this.id = id;
		this.cod_empleado = cod_empleado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.rol = rol;
		this.direccion = direccion;
		this.cedula = cedula;
		this.especialidad = especialidad;
		this.telefonos = telefonos;
	}

	public Usuario(String nombre, String apellido, String rol, String direccion,
			String cedula, String especialidad, String telefonos) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.rol = rol;
		this.direccion = direccion;
		this.cedula = cedula;
		this.especialidad = especialidad;
		this.telefonos = telefonos;
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getCod_empleado() {
		return cod_empleado;
	}

	public void setCod_empleado(String cod_empleado) {
		this.cod_empleado = cod_empleado;
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

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}
}
