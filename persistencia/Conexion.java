package edu.itla.gestorpacientes.persistencia;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.factorias.FactoriaGestion;
import edu.itla.gestorpacientes.xml.GestorXml;

public class Conexion {

	protected ResultSet resultado;
	protected PreparedStatement enunciado;
	protected Connection conexion;
	protected Statement consulta;
	protected static Conexion instancia;
	protected FactoriaGestion factoria;
	private JComboBox<String> retorno;
	private ArrayList<String> telefonos;

	public static synchronized Conexion getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new Conexion() : instancia;
	}

	public Conexion() throws SQLException, JDOMException, IOException, ClassNotFoundException {
		try {
			GestorXml gestorXml = GestorXml.getInstancia();
			Class.forName(gestorXml.getClaseDriver());
			conexion = DriverManager.getConnection(gestorXml.getDirecciónBD());
			consulta = conexion.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void agregar(Object persona) {
		factoria.agregar(persona);
	}

	public void eliminar(int id) throws SQLException {
		factoria.eliminar(id);
	}

	public void modificar(int id, int atributo, Object valor)
			throws SQLException {
		factoria.modificar(id, atributo, valor);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getDatos() throws SQLException {
		return factoria.getDatos();
	}

	public JComboBox<String> getRoles() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta.executeQuery("SELECT tipo FROM tipos_personas");
		while (resultado.next()) {
			retorno.addItem(resultado.getString("tipo"));
		}
		return retorno;
	}

	public JComboBox<String> getEspecialidades() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta
				.executeQuery("SELECT nombre_especialidad AS nombre FROM especialidades");
		while (resultado.next()) {
			retorno.addItem(resultado.getString("nombre"));
		}
		return retorno;
	}

	public JComboBox<String> getPersonas() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta
				.executeQuery("SELECT nombre_persona AS nombre, apellido_persona "
						+ "AS apellido FROM personas p JOIN tipos_personas t WHERE p.tipo_persona_id = t.id");
		while (resultado.next()) {
			retorno.addItem(resultado.getString("nombre") + " "
					+ resultado.getString("apellido"));
		}
		return retorno;
	}

	public int getIdUsuario(String nombre, String apellido) {

		return 0;
	}

	public ArrayList<String> getTelefonosPersona(int id) throws SQLException {
		telefonos = new ArrayList<String>();
		Statement consulta2 = conexion.createStatement();
		resultado = consulta2
				.executeQuery("SELECT telefono AS telefonos FROM telefonos WHERE persona_id = "
						+ id);
		while (resultado.next()) {
			telefonos.add(resultado.getString("telefonos"));
		}
		return telefonos;
	}

	public void setTelefonosPersona(int id, ArrayList<String> telefonos)
			throws SQLException {
		enunciado = conexion
				.prepareStatement("DELETE FROM telefonos where persona_id = "
						+ id);
		enunciado.execute();
		enunciado = conexion
				.prepareStatement("INSERT INTO telefonos (persona_id, telefono) VALUES (?,?)");
		for (String t : telefonos) {
			enunciado.setInt(1, id);
			enunciado.setString(2, t);
			enunciado.execute();
		}
	}
	
	public JComboBox<String> getPacientes() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta.executeQuery("SELECT `nombre`, `apellido` FROM pacientes");
		retorno.addItem("Selecciona");
		while(resultado.next()) {
			retorno.addItem(resultado.getString("nombre") + " " + resultado.getString("apellido"));
		}
		return retorno;
	}
	
	public JComboBox<String> getMedicos() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta.executeQuery("SELECT `nombre`, `apellido` FROM medicos");
		retorno.addItem("Selecciona");
		while(resultado.next()) {
			retorno.addItem(resultado.getString("nombre") + " " + resultado.getString("apellido"));
		}
		return retorno;
	}
	
	public JComboBox<String> getPadecimientos() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta.executeQuery("SELECT nombre FROM padecimientos");
		retorno.addItem("Selecciona");
		while (resultado.next()) {
			retorno.addItem(resultado.getString("nombre"));
		}
		return retorno;
	}
	
	public JComboBox<String> getPruebas_Laboratorio() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta.executeQuery("SELECT nombre FROM pruebas_laboratorio");
		retorno.addItem("Selecciona");
		while (resultado.next()) {
			retorno.addItem(resultado.getString("nombre"));
		}
		return retorno;
	}
	
	public JComboBox<String> getResultados_Pruebas_Laboratorio() throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta.executeQuery("SELECT resultado FROM `resultados_pruebas_laboratorio`");
		retorno.addItem("Selecciona");
		while (resultado.next()) {
			retorno.addItem(resultado.getString("resultado"));
		}
		return retorno;
	}

	/*
	 * public ArrayList<Asistente> getAsistente() throws SQLException {
	 * resultado = consulta.executeQuery("select * from asistentes"); while
	 * (resultado.next()) { asistente.add(new Asistente(resultado.getInt("id"),
	 * resultado .getString("nombre"), resultado.getString("apellido"),
	 * resultado.getString("telefono"), resultado .getString("direccion"),
	 * resultado .getString("cedula"), resultado .getString("codigodeEmpleado")
	 * 
	 * )); } return asistente; }
	 * 
	 * public void agregarPaciente(Paciente paciente) throws SQLException {
	 * enunciado = conexion
	 * .prepareStatement("insert into pacientes values (?,?,?,?,?,?,?,?,?)");
	 * enunciado.setString(1, paciente.getNombre()); enunciado.setString(2,
	 * paciente.getApellido()); enunciado.setString(3, paciente.getTelefono());
	 * enunciado.setString(4, paciente.getDireccion()); enunciado.setString(5,
	 * paciente.getCedula()); enunciado.setString(6,
	 * paciente.getFecha_nacimiento()); enunciado.setString(7,
	 * paciente.getFumador()); enunciado.setString(8, paciente.getAlergias());
	 * enunciado.setString(9, paciente.getFoto()); enunciado.execute(); }
	 * 
	 * public ArrayList<Persona> getPacientes() throws SQLException { resultado
	 * = consulta.executeQuery("select * from pacientes"); while
	 * (resultado.next()) { pacientes.add(new Paciente(resultado.getInt("id"),
	 * resultado .getString("nombre"), resultado.getString("apellido"),
	 * resultado.getString("telefono"), resultado .getString("direccion"),
	 * resultado .getString("cedula"), resultado .getString("fecha_nacimiento"),
	 * resultado .getString("fumador"), resultado.getString("foto"),
	 * resultado.getString("alergias"))); } return pacientes; }
	 */
	/*
	 * public void modificarPaciente(int id, int posicion, String valor) throws
	 * SQLException { switch (posicion) {
	 * 
	 * case 0: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set nombre = ? where id = ?"); break;
	 * 
	 * case 1: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set apellido = ? where id = ?");
	 * break;
	 * 
	 * case 2: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set telefono = ? where id = ?");
	 * break;
	 * 
	 * case 3: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set direccion = ? where id = ?");
	 * break;
	 * 
	 * case 4: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set cedula = ? where id = ?"); break;
	 * 
	 * case 5: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set fecha_nacimiento = ? where id = ?"
	 * ); break;
	 * 
	 * case 6: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set fumador = ? where id = ?");
	 * break;
	 * 
	 * case 7: enunciado = conexion
	 * .prepareStatement("UDPATE pacientes set foto = ? where id = ?"); break;
	 * 
	 * case 8: enunciado = conexion
	 * .prepareStatement("UPDATE pacientes set alergias = ? where id = ?");
	 * break; } enunciado.setString(1, valor); enunciado.setInt(2, id);
	 * enunciado.execute(); }
	 * 
	 * public void eliminarPaciente(int id) throws SQLException {
	 * consulta.executeQuery("DELETE from pacientes where id = " + id); }
	 */

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public FactoriaGestion getFactoria() {
		return factoria;
	}

	public void setFactoria(FactoriaGestion factoria) {
		this.factoria = factoria;
	}

	public ResultSet getResultado() {
		return resultado;
	}

	public void setResultado(ResultSet resultado) {
		this.resultado = resultado;
	}

	public PreparedStatement getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(PreparedStatement enunciado) {
		this.enunciado = enunciado;
	}

	public Statement getConsulta() {
		return consulta;
	}

	public void setConsulta(Statement consulta) {
		this.consulta = consulta;
	}

	public Connection getConnection() {
		return conexion;
	}

}
