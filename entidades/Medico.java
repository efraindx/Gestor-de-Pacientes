package com.efrain.gestorpacientes.entidades;

import java.util.ArrayList;

public class Medico  {

	private int id;
	private String codigoEmpleado;
	private String nombre;
	private String apellido;
	private ArrayList<String> telefonos;
	private String direccion;
	private String cedula;
	private String especialidad;
	
	public Medico(int id, String nombre, String apellido, ArrayList<String> telefonos,
			String direccion, String cedula, String especialidad, String codigoEmpleado) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefonos = telefonos;
		this.direccion = direccion;
		this.cedula = cedula;
		this.especialidad = especialidad;
		this.codigoEmpleado = codigoEmpleado;
	}

	public Medico(String nombre, String apellido, ArrayList<String> telefonos,
			String direccion, String cedula, String especialidad,
			String codigoEmpleado) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefonos = telefonos;
		this.direccion = direccion;
		this.cedula = cedula;
		this.especialidad = especialidad;
	}

	public Medico() {
		//
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

	public ArrayList<String> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(ArrayList<String> telefonos) {
		this.telefonos = telefonos;
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
}
