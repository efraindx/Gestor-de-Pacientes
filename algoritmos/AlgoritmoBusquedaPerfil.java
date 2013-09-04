package edu.itla.gestorpacientes.algoritmos;

import edu.itla.gestorpacientes.entidades.Persona;
import edu.itla.gestorpacientes.enums.Rol;

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