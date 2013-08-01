package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Receta;

public class FactoriaGestionRecetas extends FactoriaGestion {

	private ArrayList<Receta> recetas;

	public FactoriaGestionRecetas() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Receta receta = (Receta) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO recetas (id_paciente, id_padecimiento,"
							+ "medicamentos) VALUES (?,?,?)");
			enunciado.setInt(1, receta.getIdPaciente());
			enunciado.setInt(2, receta.getIdPadecimiento());
			enunciado.setString(3, receta.getMedicamentos());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion.prepareStatement("DELETE FROM recetas WHERE id = "
				+ id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		recetas = new ArrayList<Receta>();
		try {
			consulta = conexion.createStatement();
			resultado = consulta.executeQuery("SELECT * FROM recetas");
			while (resultado.next()) {
				int id = resultado.getInt("id");
				int idPaciente = resultado.getInt("id_paciente");
				int idPadecimiento = resultado.getInt("id_padecimiento");
				String medicamentos = resultado.getString("medicamentos");
				Receta receta = new Receta();
				receta.setIdPaciente(idPaciente);
				receta.setIdPadecimiento(idPadecimiento);
				receta.setId(id);
				JComboBox<String> pacientes = con.getPacientes();
				receta.setPaciente(pacientes.getItemAt(idPaciente));
				receta.setPadecimiento(con.getPadecimientos().getItemAt(idPadecimiento));
				receta.setMedicamentos(medicamentos);
				recetas.add(receta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recetas;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {

		switch (atributo) {
		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE recetas SET id_paciente = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE recetas SET id_padecimiento = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE recetas SET medicamentos = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;
		}
		enunciado.setInt(2, id);
		enunciado.execute();
	}
}