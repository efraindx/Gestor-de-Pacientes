package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.persistencia.Conexion;

public abstract class FactoriaGestion {
	
	protected Conexion con;
	protected ResultSet resultado;
	protected PreparedStatement enunciado;
	protected Connection conexion;
	protected Statement consulta;
	
	public abstract void agregar(Object persona);
	public abstract void eliminar(int id); 
	public abstract void modificar(int id, int atributo, Object valor) throws SQLException;
	public abstract ArrayList<?> getDatos();
	
	public void iniciarComponentes() throws ClassNotFoundException, JDOMException, IOException, SQLException {
		con = Conexion.getInstancia();
		conexion = con.getConexion();
		consulta = con.getConsulta();
	}
	public Conexion getConexion() {
		return con;
	}
	public void setConexion(Conexion con) {
		this.con = con;
	}
}
