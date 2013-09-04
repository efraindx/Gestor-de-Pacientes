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

import edu.itla.gestorpacientes.entidades.Especialidad;
import edu.itla.gestorpacientes.entidades.Medico;
import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.Padecimiento;
import edu.itla.gestorpacientes.entidades.Persona;
import edu.itla.gestorpacientes.entidades.PruebaLaboratorio;
import edu.itla.gestorpacientes.entidades.Receta;
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

	// Singleton para una sola conexión
	public static synchronized Conexion getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new Conexion() : instancia;
	}

	private Conexion() throws SQLException, JDOMException, IOException,
			ClassNotFoundException {
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

	public JComboBox<Especialidad> getEspecialidades() throws SQLException {
		JComboBox<Especialidad> retorno = new JComboBox<Especialidad>();
		Statement consultaEspecialidades = conexion.createStatement();
		resultado = consultaEspecialidades
				.executeQuery("SELECT * FROM especialidades");
		while (resultado.next()) {
			retorno.addItem(new Especialidad(resultado.getInt("id"), resultado
					.getString("codigo"), resultado.getString("nombre")));
		}
		return retorno;
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

	public JComboBox<Paciente> getPacientes() throws SQLException {
		JComboBox<Paciente> retorno = new JComboBox<Paciente>();
		Statement consultaPacientes = conexion.createStatement();
		resultado = consultaPacientes.executeQuery("SELECT * FROM `pacientes`");
		retorno.addItem(new Paciente("Selecciona", ""));
		while (resultado.next()) {
			retorno.addItem(new Paciente(resultado.getInt("id"), resultado
					.getString("nombre"), resultado.getString("apellido"),
					resultado.getString("sexo"), resultado
							.getString("telefono"), resultado
							.getString("direccion"), resultado
							.getString("cedula"), resultado
							.getString("fecha_nacimiento"), resultado
							.getString("fumador"), resultado.getString("foto"),
					resultado.getString("alergias")));
		}
		return retorno;
	}

	public JComboBox<Medico> getMedicos() throws SQLException {
		JComboBox<Medico> retorno = new JComboBox<Medico>();
		resultado = consulta
				.executeQuery("SELECT id, `nombre`, `apellido` FROM medicos");
		retorno.addItem(new Medico("Selecciona", ""));
		while (resultado.next()) {
			retorno.addItem(new Medico(resultado.getInt("id"), resultado
					.getString("nombre"), resultado.getString("apellido")));
		}
		return retorno;
	}

	public ArrayList<Medico> getMedicos(int idEspecialidad) throws SQLException {
		ArrayList<Medico> medicos = new ArrayList<Medico>();
		ResultSet resultado = consulta
				.executeQuery("SELECT `codigo_empleado`, m.`nombre`, m.`apellido`, `direccion`, `cedula`, e.`id` AS "
						+ "idEspecialidad FROM medicos m JOIN `especialidades` e WHERE m.`id_especialidad` = e.`id`  AND "
						+ "id_especialidad = " + idEspecialidad);
		while (resultado.next()) {
			medicos.add(new Medico(resultado.getString("nombre"), resultado
					.getString("apellido"), resultado.getString("direccion"),
					resultado.getString("cedula"), resultado
							.getString("codigo_empleado"),
					getItemEspecialidad(resultado.getInt("idEspecialidad"))));
		}
		return medicos;
	}

	public JComboBox<Padecimiento> getPadecimientos() throws SQLException {
		JComboBox<Padecimiento> retorno = new JComboBox<Padecimiento>();
		resultado = consulta.executeQuery("SELECT * FROM padecimientos");
		retorno.addItem(new Padecimiento("Selecciona"));
		while (resultado.next()) {
			Padecimiento padecimiento = new Padecimiento(
					resultado.getInt("id"), resultado.getString("codigo"),
					resultado.getString("nombre"));
			retorno.addItem(padecimiento);
		}
		return retorno;
	}

	public JComboBox<PruebaLaboratorio> getPruebas_Laboratorio()
			throws SQLException {
		JComboBox<PruebaLaboratorio> retorno = new JComboBox<PruebaLaboratorio>();
		Statement consultaPruebas = conexion.createStatement();
		resultado = consultaPruebas
				.executeQuery("SELECT * FROM pruebas_laboratorio");
		retorno.addItem(new PruebaLaboratorio("Selecciona"));
		while (resultado.next()) {
			PruebaLaboratorio prueba = new PruebaLaboratorio(
					resultado.getInt("id"), resultado.getString("codigo"),
					resultado.getString("nombre"));
			retorno.addItem(prueba);
		}
		return retorno;
	}

	public JComboBox<String> getResultados_Pruebas_Laboratorio()
			throws SQLException {
		retorno = new JComboBox<String>();
		resultado = consulta
				.executeQuery("SELECT resultado FROM `resultados_pruebas_laboratorio`");
		retorno.addItem("Selecciona");
		while (resultado.next()) {
			retorno.addItem(resultado.getString("resultado"));
		}
		return retorno;
	}

	public ArrayList<Paciente> getPacientes(String palabra) throws SQLException {
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		resultado = consulta
				.executeQuery("SELECT * FROM `pacientes` WHERE nombre LIKE '%"
						+ palabra + "%' OR apellido " + "LIKE '%" + palabra
						+ "%' OR cedula LIKE '%" + palabra + "%'");
		while (resultado.next()) {
			pacientes.add(new Paciente(resultado.getInt("id"), resultado
					.getString("nombre"), resultado.getString("apellido"),
					resultado.getString("sexo"), resultado
							.getString("telefono"), resultado
							.getString("direccion"), resultado
							.getString("cedula"), resultado
							.getString("fecha_nacimiento"), resultado
							.getString("fumador"), resultado.getString("foto"),
					resultado.getString("alergias")));
		}
		return pacientes;
	}

	public ArrayList<String> getDatos(String columna, String tabla, int id)
			throws SQLException {
		ArrayList<String> retorno = new ArrayList<String>();
		resultado = consulta.executeQuery("SELECT " + columna + " FROM "
				+ tabla + " WHERE id_paciente = " + id);
		while (resultado.next()) {
			retorno.add(resultado.getString(columna));
		}
		return retorno;
	}

	public ArrayList<Paciente> getListadoPacientes() throws SQLException {
		ArrayList<Paciente> retorno = new ArrayList<Paciente>();
		resultado = consulta.executeQuery("SELECT * FROM pacientes");
		while (resultado.next()) {
			retorno.add(new Paciente(resultado.getString("nombre"), resultado
					.getString("apellido"), resultado.getString("sexo"),
					resultado.getString("telefono"), resultado
							.getString("direccion"), resultado
							.getString("cedula"), resultado
							.getString("fecha_nacimiento"), resultado
							.getString("fumador"), resultado.getString("foto"),
					resultado.getString("alergias")));
		}
		return retorno;
	}

	public ArrayList<Persona> getPersonas() throws SQLException {
		ArrayList<Persona> personas = new ArrayList<Persona>();
		resultado = consulta
				.executeQuery("SELECT p.id AS id, tipo AS rol, nombre, apellido, usuario,contraseña AS contraseña, email "
						+ "FROM personas p JOIN tipos_personas t WHERE p.tipo_persona_id = t.id");
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
		return personas;
	}

	public JComboBox<Persona> getComboPersonas() throws SQLException {
		JComboBox<Persona> personas = new JComboBox<Persona>();
		resultado = consulta
				.executeQuery("SELECT p.id AS id, tipo AS rol, nombre, apellido, usuario AS usuario, contraseña, email "
						+ "FROM personas p JOIN tipos_personas t WHERE p.tipo_persona_id = t.id");
		while (resultado.next()) {
			personas.addItem(new Persona(resultado.getInt("id"), resultado
					.getString("rol"), resultado.getString("nombre"), resultado
					.getString("apellido"), resultado.getString("usuario"),
					resultado.getString("contraseña"), resultado
							.getString("email")));
		}
		return personas;
	}

	public Receta getRecetaById(int id) {
		Receta retorno = new Receta();
		try {
			ResultSet resultado = consulta
					.executeQuery("SELECT CONCAT(p.nombre, ' ', p.apellido) AS paciente, pd.nombre AS padecimiento, "
							+ "r.medicamentos FROM pacientes p JOIN padecimientos pd JOIN recetas r  WHERE r.id = "
							+ id
							+ " AND r.`id_padecimiento` = pd.id AND r.`id_paciente` = p.`id`");
			resultado.next();
			retorno.setPaciente(new Paciente(resultado.getString("paciente"),
					""));
			retorno.setPadecimiento(new Padecimiento(resultado
					.getString("padecimiento")));
			retorno.setMedicamentos(resultado.getString("medicamentos"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public Especialidad getItemEspecialidad(int id) {
		Especialidad retorno = null;
		try {
			for (int i = 0; i < getEspecialidades().getItemCount(); i++) {
				if (getEspecialidades().getItemAt(i).getId() == id) {
					retorno = getEspecialidades().getItemAt(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public void comprobar() {
		try {
			boolean encontrado = false;

			ResultSet resultado = consulta
					.executeQuery("SELECT nombre FROM especialidades");
			while (resultado.next()) {
				if (resultado.getString("nombre").equals("Ninguna")) {
					encontrado = true;
				}
			}
			if (!encontrado) {
				enunciado = conexion
						.prepareStatement("INSERT INTO especialidades (codigo, nombre) VALUES (?,?)");
				enunciado.setString(1, "5555");
				enunciado.setString(2, "Ninguna");
				enunciado.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
}
