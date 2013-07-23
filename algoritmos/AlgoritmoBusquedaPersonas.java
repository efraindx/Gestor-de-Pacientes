package com.efrain.gestorpacientes.algoritmos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.encriptación.Encriptadora;
import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.modelos.ModeloPersonas;

public class AlgoritmoBusquedaPersonas implements AlgoritmoBusqueda {

	private ArrayList<Persona> personas;
	private Object retorno;

	public AlgoritmoBusquedaPersonas() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		personas = ModeloPersonas.getInstancia().getUsuarios();
	}

	@Override
	public Object buscar(Persona persona) {
		retorno = null;
		for (Persona p : personas) {
			if (p.getUsuario().equals(persona.getUsuario())
					& Encriptadora.desencriptar(p.getContrase()).equals(
							persona.getContrase())) {
				retorno = persona;
				persona.setRol(p.getRol());
			} else if (p.getUsuario().equals(persona.getUsuario())) {
				retorno = p;
			}
		}
		return retorno;
	}
}
