package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;

public class FactoriaGestionPacientes extends FactoriaGestion {

	private ArrayList<Paciente> pacientes;

	public FactoriaGestionPacientes() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Paciente paciente = (Paciente) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO pacientes (nombre, apellido, sexo, telefono, "
							+ "direccion, cedula, fecha_nacimiento, fumador, foto, "
							+ " alergias) VALUES (?,?,?,?,?,?,?,?,?,?)");
			enunciado.setString(1, paciente.getNombre());
			enunciado.setString(2, paciente.getApellido());
			enunciado.setString(3, paciente.getSexo());
			enunciado.setString(4, paciente.getTelefono());
			enunciado.setString(5, paciente.getDireccion());
			enunciado.setString(6, paciente.getCedula());
			enunciado.setString(7, paciente.getFecha_nacimiento());
			enunciado.setString(8, paciente.getFumador());
			enunciado.setString(9, paciente.getFoto());
			enunciado.setString(10, paciente.getAlergias());
			enunciado.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion
				.prepareStatement("DELETE FROM pacientes WHERE id = " + id);
		enunciado.execute();
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {
		switch (atributo) {
		case 0:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET nombre = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET apellido = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET sexo = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET telefono = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 4:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET direccion = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 5:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET cedula = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 6:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET fecha_nacimiento = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 7:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET fumador = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 8:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes SET foto = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 9:
			enunciado = conexion
					.prepareStatement("UPDATE asistentes SET alergias = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;
		}
		enunciado.setInt(2, id);
		enunciado.execute();
	}

	@Override
	public ArrayList<?> getDatos() {
		pacientes = new ArrayList<Paciente>();
		try {
			resultado = consulta.executeQuery("SELECT * FROM pacientes");
			while (resultado.next()) {
				Paciente paciente = new Paciente(resultado.getInt("id"),
						resultado.getString("nombre"),
						resultado.getString("apellido"),
						resultado.getString("sexo"),
						resultado.getString("telefono"),
						resultado.getString("direccion"),
						resultado.getString("cedula"),
						resultado.getString("fecha_nacimiento"),
						resultado.getString("fumador"),
						resultado.getString("foto"),
						resultado.getString("alergias"));
				pacientes.add(paciente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pacientes;
	}

	public ArrayList<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(ArrayList<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
}
