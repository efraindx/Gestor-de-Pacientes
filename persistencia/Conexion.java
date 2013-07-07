package com.efrain.gestorpacientes.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.efrain.gestorpacientes.entidades.Paciente;

public class Conexion {

	private ArrayList<Paciente> pacientes;
	private ResultSet resultado;
	private PreparedStatement enunciado;
	private Connection conexion;
	private Statement consulta;
	private static Conexion instancia;

	public static synchronized Conexion getInstancia()
			throws ClassNotFoundException, SQLException {
		return instancia == null ? instancia = new Conexion() : instancia;
	}

	public Conexion() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conexion = DriverManager
				.getConnection("jdbc:mysql://localhost/gpacientes?user=root&password=itla");
		consulta = conexion.createStatement();
		pacientes = new ArrayList<Paciente>();
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
			pacientes
					.add(new Paciente(resultado.getString("nombre"), resultado
							.getString("apellido"), resultado
							.getString("telefono"), resultado
							.getString("direccion"), resultado
							.getString("cedula"), resultado
							.getString("fecha_nacimiento"), resultado
							.getString("fumador"), resultado
							.getString("alergias"), resultado.getString("foto")));
		}
		return pacientes;
	}

}
