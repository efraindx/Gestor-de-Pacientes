package com.efrain.gestorpacientes.algoritmos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.modelos.ModeloPersonas;

public class AlgoritmoBusquedaPersona implements AlgoritmoBusqueda {
	
	private ArrayList<Persona> personas;
	private Persona retorno;
	
	public AlgoritmoBusquedaPersona() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		personas = ModeloPersonas.getInstancia().getPersonas();
	} 

	@Override
	public Object buscar(Persona persona) {
		for(Persona p: personas) {
			if(p.getUsuario().equals(persona.getUsuario()) & p.getContrase().equals(persona.getContrase())) {
				retorno = persona;
				persona.setRol(p.getRol());
			}
		}
		return retorno;
	}
}
