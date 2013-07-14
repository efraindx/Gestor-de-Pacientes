package Factorias;

import java.io.IOException;
import java.sql.SQLException;

import org.jdom2.JDOMException;

import entidades.Administrador;
import entidades.Asistente;
import entidades.Medico;
import entidades.Persona;
import modelos.ModeloAdministradores;
import modelos.ModeloAsistentes;
import modelos.ModeloMedico;
import modelos.ModeloPacientes;

public class Prueba {

	public static void main(String[] args) {
		
		try {
		//ModeloMedico modelo = ModeloMedico.getInstancia();
		//ModeloAsistentes asistentes = ModeloAsistentes.getInstancia();
		ModeloAdministradores administrador = ModeloAdministradores.getInstancia();
			
		//Medico medico = new Medico();
		//	Asistente asistente = new Asistente();
			Administrador administradores = new Administrador();
			
		//		medico.setNombre("Naruto");
		//	asistente.setNombre("Manuel");
			administradores.setNombre("Efrain");
			
		//	modelo.agregar(medico, "medicos");
		//	asistentes.agregar(asistente, "asistentes");
		administrador.agregar(administradores,"administradores");
			
			
	
		} catch (ClassNotFoundException | SQLException | JDOMException
				| IOException e) {
			e.printStackTrace();
		}
		
	}

}
