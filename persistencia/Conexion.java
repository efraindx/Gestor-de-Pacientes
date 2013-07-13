package com.efrain.gestorpacientes.persistencia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Paciente;
import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.entidades.Usuario;
import com.efrain.gestorpacientes.xml.GestorXml;

public class Conexion {

	private ArrayList<Paciente> pacientes;
	private ArrayList<Persona> personas;
	private ResultSet resultado;
	private PreparedStatement enunciado;
	private Connection conexion;
	private Statement consulta;
	private static Conexion instancia;

	public static synchronized Conexion getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new Conexion() : instancia;
	}

	public Conexion() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		GestorXml gestorXml = new GestorXml();
		Class.forName(gestorXml.getClaseDriver());
		conexion = DriverManager.getConnection(gestorXml.getDirecci�nBD());
		consulta = conexion.createStatement();
		pacientes = new ArrayList<Paciente>();
		personas = new ArrayList<Persona>();
	}

	public void agregarPaciente(Paciente paciente) throws SQLException {
		enunciado = conexion
				.prepareStatement("insert into pacientes values (?,?,?,?,?,?,?,?,?)");
		enunciado.setString(1, paciente.getNombre());
		enunciado.setString(2, paciente.getApellido());
		enunciado.setString(3, paciente.getTelefono());
		enunciado.setString(4, paciente.getDireccion());
		enunciado.setString(5, paciente.getCedula());
		enunciado.setString(6, paciente.getFecha_nacimiento());
		enunciado.setString(7, paciente.getFumador());
		enunciado.setString(8, paciente.getAlergias());
		enunciado.setString(9, paciente.getFoto());
		enunciado.execute();
	}

	public ArrayList<Paciente> getPacientes() throws SQLException {
		resultado = consulta.executeQuery("select * from pacientes");
		while (resultado.next()) {
			pacientes.add(new Paciente(resultado.getInt("id"), resultado
					.getString("nombre"), resultado.getString("apellido"),
					resultado.getString("telefono"), resultado
							.getString("direccion"), resultado
							.getString("cedula"), resultado
							.getString("fecha_nacimiento"), resultado
							.getString("fumador"), resultado.getString("foto"),
					resultado.getString("alergias")));
		}
		return pacientes;
	}

	public void modificarPaciente(int id, int posicion, String valor)
			throws SQLException {
		switch (posicion) {

		case 0:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set nombre = ? where id = ?");
			break;

		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set apellido = ? where id = ?");
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set telefono = ? where id = ?");
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set direccion = ? where id = ?");
			break;

		case 4:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set cedula = ? where id = ?");
			break;

		case 5:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set fecha_nacimiento = ? where id = ?");
			break;

		case 6:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set fumador = ? where id = ?");
			break;

		case 7:
			enunciado = conexion
					.prepareStatement("UDPATE pacientes set foto = ? where id = ?");
			break;

		case 8:
			enunciado = conexion
					.prepareStatement("UPDATE pacientes set alergias = ? where id = ?");
			break;
		}
		enunciado.setString(1, valor);
		enunciado.setInt(2, id);
		enunciado.execute();
	}

	public void eliminarPaciente(int id) throws SQLException {
		consulta.executeQuery("DELETE from pacientes where id = " + id);
	}

	public void agregarUsuario(Usuario usuario) throws SQLException {
		enunciado = conexion
				.prepareStatement("INSERT into usuarios (cod_empleado, nombre, apellido"
						+ "rol, direccion, cedula, especialidad, telefonos) values (?, ?, ?, ?, ?, ?, ?, ?)");
		enunciado.setString(1, usuario.getCod_empleado());
		enunciado.setString(2, usuario.getNombre());
		enunciado.setString(3, usuario.getApellido());
		enunciado.setString(4, usuario.getRol());
		enunciado.setString(5, usuario.getDireccion());
		enunciado.setString(6, usuario.getCedula());
		enunciado.setString(7, usuario.getEspecialidad());
		enunciado.setString(8, usuario.getTelefonos());
		enunciado.execute();
	}

	public void eliminarUsuario(int id) throws SQLException {
		consulta.executeQuery("DELETE from usuario where id = " + id);
	}

	public ArrayList<Persona> getPersonas() throws SQLException {
		resultado = consulta
				.executeQuery("SELECT persona_id AS id, tipo AS rol, nombre, apellido, usuario, "
						+ "contrase�a FROM personas p JOIN tipos_personas t WHERE p.tipo_persona_id = t.id");
		while (resultado.next()) {
			personas.add(new Persona(resultado.getInt("id"), resultado
					.getString("rol"), resultado.getString("nombre"), resultado
					.getString("apellido"), resultado.getString("usuario"),
					resultado.getString("contrase�a")));
		}
		return personas;
	}

	public void modificarUsuario(int id, int posicion, String valor) {
		switch (posicion) {
		case 0:
			break;
		}
	}
}
