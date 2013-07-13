package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Administrador;
import com.efrain.gestorpacientes.entidades.Medico;
import com.efrain.gestorpacientes.modelos.ModeloAdministradores;
import com.efrain.gestorpacientes.modelos.ModeloMedico;

public class Prueba {

	public static void main(String[] args) {
		
		try {
		ModeloMedico modelo = ModeloMedico.getInstancia();
		//ModeloAsistentes asistentes = ModeloAsistentes.getInstancia();
		ModeloAdministradores administrador = ModeloAdministradores.getInstancia();
			
		Medico medico = new Medico();
		//	Asistente asistente = new Asistente();
			Administrador administradores = new Administrador();
			
			medico.setNombre("angel");
		//	asistente.setNombre("Manuel");
			administradores.setNombre("Efrain");
			
		modelo.agregar(medico, "medicos");
		//	asistentes.agregar(asistente, "asistentes");
		administrador.agregar(administradores,"administradores");
			System.out.println("Test");
			
	
		} catch (ClassNotFoundException | SQLException | JDOMException
				| IOException e) {
			e.printStackTrace();
		}
		
	}

}
