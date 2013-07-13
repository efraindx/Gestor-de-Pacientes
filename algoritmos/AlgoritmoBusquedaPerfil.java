package com.efrain.gestorpacientes.algoritmos;

import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.entidades.Rol;

public class AlgoritmoBusquedaPerfil implements AlgoritmoBusqueda {
	
	private String perfilRetorno;
	
	@Override
	public Object buscar(Persona persona) {
		if (persona.getRol().equals(Rol.ADMINISTRADOR.name())) {
			perfilRetorno = Rol.ADMINISTRADOR.name();
		} else if (persona.getRol().equals(Rol.MEDICO.name())) {
			perfilRetorno = Rol.MEDICO.name();
		} else {
			perfilRetorno = Rol.ASISTENTE.name();
		}
		return perfilRetorno;
	}

}
