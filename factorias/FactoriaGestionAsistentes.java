package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Asistente;

public class FactoriaGestionAsistentes extends FactoriaGestion {

	private ArrayList<Asistente> asistentes;

	public FactoriaGestionAsistentes() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Asistente asistente = (Asistente) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO asistentes (codigo_empleado, nombre, apellido, direccion,"
							+ " cedula) values (?,?,?,?,?)");
			enunciado.setString(1, asistente.getCodigoEmpleado());
			enunciado.setString(2, asistente.getNombre());
			enunciado.setString(3, asistente.getApellido());
			enunciado.setString(4, asistente.getDireccion());
			enunciado.setString(5, asistente.getCedula());
			enunciado.execute();
			
			resultado = consulta
					.executeQuery("SELECT id FROM personas WHERE nombre = '"
							+ asistente.getNombre()
							+ "' AND apellido = '"
							+ asistente.getApellido() + "'");
			resultado.next();
			int id = resultado.getInt("id");
			asistente.setId(id);
			enunciado = conexion
					.prepareStatement("INSERT INTO telefonos (persona_id, telefono) VALUES (?,?)");
			for (String telefono : asistente.getTelefonos()) {
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
		enunciado = conexion
				.prepareStatement("DELETE FROM asistentes WHERE id = " + id);
		enunciado.execute();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList getDatos() {
		asistentes = new ArrayList<Asistente>();
		try {
			resultado = consulta.executeQuery("SELECT * FROM asistentes");
			while (resultado.next()) {
				int id = resultado.getInt("id");
				String nombre = resultado.getString("nombre");
				String apellido = resultado.getString("apellido");
				String direccion = resultado.getString("direccion");
				String cedula = resultado.getString("cedula");
				String codemp = resultado.getString("codigo_empleado");
				Asistente asistente = new Asistente();
				asistente.setId(id);
				asistente.setNombre(nombre);
				asistente.setApellido(apellido);
				asistente.setTelefonos(con.getTelefonosPersona(id));
				asistente.setDireccion(direccion);
				asistente.setCedula(cedula);
				asistente.setCodigoEmpleado(codemp);
				asistentes.add(asistente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return asistentes;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {
		switch (atributo) {
		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE asistentes SET codigo_empleado = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE asistentes SET nombre = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE asistentes SET apellido = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 4:
			enunciado = conexion
					.prepareStatement("UPDATE asistentes SET direccion = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 5:
			enunciado = conexion
					.prepareStatement("UPDATE asistentes SET cedula = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;
		}
		
	}
}
