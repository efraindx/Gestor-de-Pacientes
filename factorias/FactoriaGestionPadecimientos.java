package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Padecimiento;

public class FactoriaGestionPadecimientos extends FactoriaGestion {

	private ArrayList<Padecimiento> padecimientos;

	public FactoriaGestionPadecimientos() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Padecimiento padecimiento = (Padecimiento) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO padecimientos (codigo, nombre) "
							+ "values (?,?)");
			enunciado.setString(1, padecimiento.getCodigo());
			enunciado.setString(2, padecimiento.getNombre());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion
				.prepareStatement("DELETE FROM padecimientos WHERE id = " + id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		padecimientos = new ArrayList<Padecimiento>();
		try {
			resultado = consulta.executeQuery("SELECT * FROM padecimientos");
			while (resultado.next()) {
				Padecimiento padecimiento = new Padecimiento(
						resultado.getInt("id"), resultado.getString("codigo"),
						resultado.getString("nombre"));
				padecimientos.add(padecimiento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return padecimientos;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {

		switch (atributo) {
		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE padecimientos SET codigo = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE padecimientos SET nombre = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;
		}
		
	}
}
