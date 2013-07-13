package com.efrain.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.persistencia.Conexion;
import com.efrain.gestorpacientes.entidades.Persona;

public class ModeloPersonas {
	
	private ArrayList<Persona> personas;
	private static ModeloPersonas instancia;
	
	public static synchronized ModeloPersonas getInstancia() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new ModeloPersonas() : instancia;
	}
	
	public ModeloPersonas() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		personas = Conexion.getInstancia().getPersonas();
	}

	public ArrayList<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(ArrayList<Persona> personas) {
		this.personas = personas;
	}
}
