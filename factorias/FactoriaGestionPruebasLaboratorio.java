package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.PruebaLaboratorio;

public class FactoriaGestionPruebasLaboratorio extends FactoriaGestion {

	private ArrayList<PruebaLaboratorio> pruebas;

	public FactoriaGestionPruebasLaboratorio() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		PruebaLaboratorio prueba = (PruebaLaboratorio) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO pruebas_laboratorio (codigo, nombre) "
							+ "values (?,?)");
			enunciado.setString(1, prueba.getCodigo());
			enunciado.setString(2, prueba.getNombre());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion
				.prepareStatement("DELETE FROM pruebas_laboratorio WHERE id = "
						+ id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		pruebas = new ArrayList<PruebaLaboratorio>();
		try {
			resultado = consulta.executeQuery("SELECT * FROM pruebas_laboratorio");
			while (resultado.next()) {
				PruebaLaboratorio cita = new PruebaLaboratorio(resultado.getInt("id"),
						resultado.getString("codigo"), resultado.getString("nombre"));
				pruebas.add(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pruebas;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {

		switch (atributo) {
		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE pruebas_laboratorio SET codigo = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE pruebas_laboratorio SET nombre = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;
			
		case 3:
			break;
		}
		
	}
}