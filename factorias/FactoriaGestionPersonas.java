package com.efrain.gestorpacientes.factorias;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import com.efrain.gestorpacientes.entidades.Persona;

public class FactoriaGestionPersonas extends FactoriaGestion {

	private ArrayList<Persona> personas;

	public FactoriaGestionPersonas() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	public void agregar(Object usuario) {
		int tipoPersonaId = 0;
		Persona personaActual = (Persona) usuario;

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
					.prepareStatement("UPDATE personas set tipo_persona_id = ?, nombre_persona = ?, apellido_persona = ?, "
							+ "usuario_persona = ?, contraseña_persona = ?, email = ? WHERE persona_id = ?");
			enunciado.setInt(1, tipoPersonaId);
			enunciado.setString(2, personaActual.getNombre());
			enunciado.setString(3, personaActual.getApellido());
			enunciado.setString(4, personaActual.getUsuario());
			enunciado.setString(5, personaActual.getContrase());
			enunciado.setString(6, personaActual.getCorreo());
			enunciado.setInt(
					7,
					getIdPersona(personaActual.getNombre(),
							personaActual.getApellido()));
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getIdPersona(String nombre, String apellido) throws SQLException {
		resultado = consulta
				.executeQuery("SELECT `persona_id` AS id FROM `personas` WHERE `nombre_persona` "
						+ "LIKE '%"
						+ nombre
						+ "' AND `apellido_persona` LIKE '%" + apellido + "'");
		resultado.next();
		int retorno = resultado.getInt("id");
		return retorno;
	}

	@Override
	public void eliminar(int id) {
		try {
			enunciado = conexion
					.prepareStatement("UPDATE personas SET usuario_persona = '(NULL)', "
							+ "contraseña_persona = '(NULL)', email = '(NULL)' WHERE persona_id = ?");
			enunciado.setInt(1, id);
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {

		switch (atributo) {
		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE personas set nombre_persona = ? where persona_id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 4:
			enunciado = conexion
					.prepareStatement("UPDATE personas set apellido_persona = ? where persona_id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 5:
			switch ((String) valor) {
			case "ADMINISTRADOR":
				valor = 1;
				break;

			case "ASISTENTE":
				valor = 2;
				break;

			case "MEDICO":
				valor = 3;
				break;
			}
			enunciado = conexion
					.prepareStatement("UPDATE personas set tipo_persona_id = ? where persona_id = ?");
			enunciado.setInt(1, (int) valor);
			break;

		case 6:
			enunciado = conexion
					.prepareStatement("UPDATE personas set usuario_persona = ? where persona_id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 7:
			enunciado = conexion
					.prepareStatement("UPDATE personas set contraseña_persona = ? where persona_id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 8:
			enunciado = conexion
					.prepareStatement("UPDATE personas SET email = ? where persona_id = ?");
			enunciado.setString(1, (String) valor);
			break;
		}
		enunciado.setInt(2, id);
		enunciado.execute();
	}

	@Override
	public ArrayList<Persona> getDatos() {
		personas = new ArrayList<Persona>();
		try {
			resultado = consulta
					.executeQuery("SELECT persona_id AS id, tipo AS rol, nombre_persona AS nombre, apellido_persona AS apellido, "
							+ "usuario_persona AS usuario, contraseña_persona AS contraseña, email FROM personas p JOIN tipos_personas t WHERE p.tipo_persona_id = t.id");
			while (resultado.next()) {
				if (!"(NULL)".equals(resultado.getString("usuario"))
						&& resultado.getString("usuario") != null) {
					personas.add(new Persona(resultado.getInt("id"), resultado
							.getString("rol"), resultado.getString("nombre"),
							resultado.getString("apellido"), resultado
									.getString("usuario"), resultado
									.getString("contraseña"), resultado
									.getString("email")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personas;
	}
}
