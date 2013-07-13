package com.efrain.gestorpacientes.entidades;

public class Persona {
	
	private int id;
	private String rol;
	private String nombre;
	private String apellido;
	private String usuario;
	private String contraseña;
	
	public Persona(String usuario, String contrase) {
		this.usuario = usuario;
		this.contraseña = contrase;
	}
	
	public Persona(String rol, String nombre, String apellido, String usuario,
			String contraseña) {
		this.rol = rol;
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.contraseña = contraseña;
	}
	public Persona(int id, String rol, String nombre, String apellido,
			String usuario, String contraseña) {
		this.id = id;
		this.rol = rol;
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.contraseña = contraseña;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
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
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getContraseña() {
		return contraseña;
	}
	
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

}
