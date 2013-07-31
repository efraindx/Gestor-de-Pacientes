package com.efrain.gestorpacientes.entidades;

public class Padecimiento {
	
	private int id;
	private String codigo;
	private String nombre;
	
	public Padecimiento(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}
	public Padecimiento(int id, String codigo, String nombre) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
