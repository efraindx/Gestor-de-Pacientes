package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Cita;

import edu.itla.gestorpacientes.utilidades.Almacen;

public class FactoriaGestionCitas extends FactoriaGestion {

	private ArrayList<Cita> citas;

	public FactoriaGestionCitas() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Cita cita = (Cita) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO citas (id_paciente, id_medico, fecha, hora, causa, observaciones) "
							+ "VALUES (?,?,?,?,?,?)");
			enunciado.setInt(1, cita.getPaciente().getId());
			enunciado.setInt(2, cita.getMedico().getId());
			enunciado.setString(3, cita.getFecha());
			enunciado.setString(4, cita.getHora());
			enunciado.setString(5, cita.getCausa());
			enunciado.setString(6, cita.getObservacion());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion.prepareStatement("DELETE FROM citas WHERE id = "
				+ id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		citas = new ArrayList<Cita>();
		try {
			consulta = conexion.createStatement();
			resultado = consulta.executeQuery("SELECT * FROM citas");
			while (resultado.next()) {
				int id = resultado.getInt("id");
				int idPaciente = resultado.getInt("id_paciente");
				int idMedico = resultado.getInt("id_medico");
				String fecha = resultado.getString("fecha");
				String hora = resultado.getString("hora");
				String causa = resultado.getString("causa");
				String obs = resultado.getString("observaciones");
				Cita cita = new Cita();
				cita.setId(id);
				Almacen almacen = new Almacen();
				cita.setPaciente(almacen.getItemPaciente(idPaciente));
				cita.setMedico(almacen.getItemMedico(idMedico));
				cita.setFecha(fecha);
				cita.setHora(hora);
				cita.setHora(hora);
				cita.setCausa(causa);
				cita.setObservacion(obs);
				citas.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return citas;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {
		switch (atributo) {
		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE citas SET id_paciente = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE citas SET id_medico = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE citas SET fecha = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 4:
			enunciado = conexion
					.prepareStatement("UPDATE citas SET hora = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 5:
			enunciado = conexion
					.prepareStatement("UPDATE citas SET causa = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 6:
			enunciado = conexion
					.prepareStatement("UPDATE citas SET observaciones = ? where id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;
		}
	}
}