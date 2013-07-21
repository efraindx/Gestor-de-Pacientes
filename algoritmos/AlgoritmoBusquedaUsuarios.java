package com.efrain.gestorpacientes.algoritmos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.modelos.ModeloPersonas;

public class AlgoritmoBusquedaUsuarios implements AlgoritmoBusqueda {
	
	private ArrayList<Persona> usuarios;
	private Persona retorno;
	
	public AlgoritmoBusquedaUsuarios() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		usuarios = ModeloPersonas.getInstancia().getUsuarios();
	} 

	@Override
	public Object buscar(Persona usuario) {
		for(Persona u: usuarios) {
			if(u.getUsuario().equals(usuario.getUsuario()) & u.getContrase().equals(usuario.getContrase())) {
				retorno = usuario;
				usuario.setRol(u.getRol());
			}
		}
		return retorno;
	}
}
