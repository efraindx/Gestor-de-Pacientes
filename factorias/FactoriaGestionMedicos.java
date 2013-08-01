package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

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
			enunciado.setInt(1, 2);
			enunciado.setString(2, medico.getCodigoEmpleado());
			enunciado.setString(3, medico.getNombre());
			enunciado.setString(4, medico.getApellido());
			enunciado.setString(5, medico.getDireccion());
			enunciado.setString(6, medico.getCedula());
			enunciado.execute();
			
			resultado = consulta.executeQuery("SELECT persona_id FROM personas WHERE nombre_persona LIKE '%"+medico.getNombre()+"%'" +
					"AND apellido_persona LIKE '%"+medico.getApellido()+"%'");
			resultado.next();
			int id = resultado.getInt("persona_id");
			medico.setId(id);
			enunciado = conexion.prepareStatement("UPDATE medicos SET id = "+id+" WHERE nombre LIKE '%"+medico.getNombre()+"%'" +
					"AND apellido LIKE '%"+medico.getApellido()+"%'");
			enunciado.execute();
			
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
		enunciado = conexion.prepareStatement("DELETE FROM medicos WHERE id = " + id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		medicos = new ArrayList<Medico>();
		try {
			resultado = consulta
					.executeQuery("SELECT id, `nombre`, `apellido`, `direccion`, `cedula`, `nombre_especialidad` AS especialidad , "
							+ "`codigo_empleado` FROM `medicos` m JOIN `especialidades` e WHERE m.`id_especialidad` = e.id_esp");
			while (resultado.next()) {
				int id = resultado.getInt("id");
				String nombre = resultado.getString("nombre");
				String apellido = resultado.getString("apellido");
				String direccion = resultado.getString("direccion");
				String cedula = resultado.getString("cedula");
				String esp = resultado.getString("especialidad");
				String codemp = resultado.getString("codigo_empleado");
				Medico medico = new Medico();
				medico.setId(id);
				medico.setTelefonos(con.getTelefonosPersona(id));
				medico.setNombre(nombre);
				medico.setApellido(apellido);
				medico.setDireccion(direccion);
				medico.setCedula(cedula);
				medico.setEspecialidad(esp);
				medico.setCodigoEmpleado(codemp);
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
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET codigo_empleado = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET nombre = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 4:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET apellido = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 5:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET direccion = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;

		case 6:
			enunciado = conexion
					.prepareStatement("UPDATE medicos SET cedula = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;
		}
		enunciado.setInt(2, id);
		enunciado.execute();
	}
}
