package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Especialidad;
import edu.itla.gestorpacientes.entidades.Medico;

public class FactoriaGestionMedicos extends FactoriaGestion {

	private ArrayList<Medico> medicos;

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
							+ " cedula) values (?,?,?,?,?,?)");
			enunciado.setInt(1, medico.getIdEspecialidad());
			enunciado.setString(2, medico.getCodigoEmpleado());
			enunciado.setString(3, medico.getNombre());
			enunciado.setString(4, medico.getApellido());
			enunciado.setString(5, medico.getDireccion());
			enunciado.setString(6, medico.getCedula());
			enunciado.execute();

			resultado = consulta
					.executeQuery("SELECT id FROM personas WHERE nombre = '"
							+ medico.getNombre() + "' AND apellido = '"
							+ medico.getApellido() + "'");
			resultado.next();
			int id = resultado.getInt("id");
			medico.setId(id);
			enunciado = conexion
					.prepareStatement("INSERT INTO telefonos (persona_id, telefono) VALUES (?,?)");
			for (String telefono : medico.getTelefonos()) {
				enunciado.setInt(1, id);
				enunciado.setString(2, telefono);
				enunciado.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion.prepareStatement("DELETE FROM medicos WHERE id = "
				+ id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		medicos = new ArrayList<Medico>();
		try {
			resultado = consulta
					.executeQuery("SELECT m.id, m.nombre, m.apellido, `direccion`, `cedula`, e.`id` AS id_especialidad ,"
							+ "`codigo_empleado` FROM `medicos` m JOIN `especialidades` e WHERE m.`id_especialidad` = e.id");
			while (resultado.next()) {
				int id = resultado.getInt("id");
				int idEsp = resultado.getInt("id_especialidad");
				ArrayList<String> telefonos = con.getTelefonosPersona(id);
				Especialidad especialidad = con.getItemEspecialidad(idEsp);
				String nombre = resultado.getString("nombre");
				String apellido = resultado.getString("apellido");
				String direccion = resultado.getString("direccion");
				String cedula = resultado.getString("cedula");
				String codemp = resultado.getString("codigo_empleado");
				Medico medico = new Medico(id, nombre, apellido, telefonos,
						direccion, cedula, especialidad, codemp);
				medicos.add(medico);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medicos;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {

		switch (atributo) {
		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET id_especialidad = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET codigo_empleado = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET nombre = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();

			enunciado = conexion
					.prepareStatement("UPDATE personas SET nombre = ? where id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 4:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET apellido = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();

			enunciado = conexion
					.prepareStatement("UPDATE personas SET apellido = ? where id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 5:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET direccion = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 6:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET cedula = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;
		}

	}
}
