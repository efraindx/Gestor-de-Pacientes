package com.efrain.gestorpacientes.factorias;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.persistencia.Conexion;
import com.efrain.gestorpacientes.entidades.Persona;

public class FactoriaGestionPersonas extends Conexion implements
		FactoriaGestion {

	private ArrayList<Persona> personas;

	public FactoriaGestionPersonas() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		this.personas = new ArrayList<Persona>();
	}

	public void agregar(Object persona) {
		int tipoPersonaId = 0;
		Persona personaActual = (Persona) persona;

		switch (personaActual.getRol()) {
		case "ADMINISTRADOR":
			tipoPersonaId = 1;
			break;

		case "ASISTENTE":
			tipoPersonaId = 2;
			break;

		case "MEDICO":
			tipoPersonaId = 3;
			break;
		}
		try {
			enunciado = conexion
					.prepareStatement("INSERT into personas (tipo_persona_id, nombre_persona, apellido_persona, "
							+ "usuario_persona, contraseña_persona) values (?,?,?,?,?)");
			enunciado.setInt(1, tipoPersonaId);
			enunciado.setString(2, personaActual.getNombre());
			enunciado.setString(3, personaActual.getApellido());
			enunciado.setString(4, personaActual.getUsuario());
			enunciado.setString(5, personaActual.getContrase());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) {
			try {
			consulta.executeQuery("DELETE from personas where id = " + id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void modificar(int id, int atributo, Object valor) throws SQLException {
			
			switch(atributo) {
			case 0: 
				enunciado = conexion
				.prepareStatement("UPDATE personas set tipo_persona_id = ? where id = ?"); 
				enunciado.setInt(1, (int)valor);
			break;
			  
			case 1: 
				enunciado = conexion
				.prepareStatement("UPDATE personas set nombre_persona = ? where id = ?");
				enunciado.setString(1, (String)valor);
			break;
			  
			case 2: 
				enunciado = conexion
				.prepareStatement("UPDATE personas set apellido_persona = ? where id = ?");
				enunciado.setString(1, (String)valor);
			break;
			  
			case 3: 
				 	enunciado = conexion
				 	.prepareStatement("UPDATE personas set usuario_persona = ? where id = ?");
				 	enunciado.setString(1, (String)valor);
			break;
			 
			case 7: 
					enunciado = conexion
					.prepareStatement("UDPATE pacientes set contraseña_persona = ? where id = ?"); 
					enunciado.setString(1, (String)valor);
			break;
			
			} 
			enunciado.setInt(2, id);
			enunciado.execute(); 
	}

	@Override
	public ArrayList<?> getDatos() {
		try {
			resultado = consulta
					.executeQuery("SELECT persona_id AS id, tipo_persona_id AS rol, nombre_persona as nombre, apellido_persona as apellido, "
							+ "usuario_persona as usuario, contraseña_persona as contraseña FROM personas p JOIN tipos_personas t WHERE p.tipo_persona_id = t.id");
			while (resultado.next()) {
				personas.add(new Persona(resultado.getInt("id"), resultado
						.getString("rol"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado
								.getString("usuario"), resultado
								.getString("contraseña")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personas;
	}

	public ArrayList<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(ArrayList<Persona> personas) {
		this.personas = personas;
	}

}
