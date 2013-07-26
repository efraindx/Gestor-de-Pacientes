package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Medico;

public class FactoriaGestionMedicos extends FactoriaGestion {

	private ArrayList<Medico> medicos;
	private ArrayList<String> telefonos;

	public FactoriaGestionMedicos() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Medico medico = (Medico) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO medicos (id_especialidad, codigo_empleado, nombre, apellido, direccion,"
							+ " cedula) values (?,?,?,?,?,?.?)");
			enunciado.setInt(1, 2);// TODO hacer switches...
			enunciado.setString(2, medico.getCodigoEmpleado());
			enunciado.setString(3, medico.getNombre());
			enunciado.setString(4, medico.getApellido());
			enunciado.setString(5, medico.getDireccion());
			enunciado.setString(6, medico.getCedula());
			enunciado.execute();

			enunciado = conexion
					.prepareStatement("INSERT INTO telefonos (persona_id, telefono) VALUES (?,?)");
			for (String telefono : medico.getTelefonos()) {
				enunciado.setInt(1, medico.getId());
				enunciado.setString(2, telefono);
				enunciado.execute();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void eliminar(int fila) {
		System.out.println("Eliminando Medico...");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		medicos = new ArrayList<Medico>();
		ArrayList<String> telefonos = null;
		try {
			resultado = consulta.executeQuery("SELECT id, `nombre`, `apellido`, `direccion`, `cedula`, `nombre_especialidad` AS especialidad , " +
					"`codigo_empleado` FROM `medicos` m JOIN `especialidades` e WHERE m.`id_especialidad` = e.id_esp");
			while (resultado.next()) {
			Medico medico = new Medico(resultado.getInt("id"), resultado
						.getString("nombre"), resultado.getString("apellido"),
						null, resultado
								.getString("direccion"), resultado
								.getString("cedula"), resultado
								.getString("especialidad"), resultado
								.getString("codigo_empleado"));
				int id = resultado.getInt("id");
				telefonos = getTelefonosPersona(id);
				medico.setTelefonos(telefonos);
				medicos.add(medico);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medicos;
	}

	public ArrayList<String> getTelefonosPersona(int id) throws SQLException {
		telefonos = new ArrayList<String>();
		resultado = consulta
				.executeQuery("SELECT telefono AS telefonos FROM telefonos WHERE persona_id = "
						+ id);
		while (resultado.next()) {
			telefonos.add(resultado.getString("telefonos"));
		}
		return telefonos;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {

	}
}
