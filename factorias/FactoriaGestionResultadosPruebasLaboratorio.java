package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.PruebaLaboratorio;
import edu.itla.gestorpacientes.entidades.ResultadoPruebaLaboratorio;
import edu.itla.gestorpacientes.utilidades.Almacen;

public class FactoriaGestionResultadosPruebasLaboratorio extends
		FactoriaGestion {

	private ArrayList<ResultadoPruebaLaboratorio> resultados;

	public FactoriaGestionResultadosPruebasLaboratorio() throws JDOMException,
			IOException, SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		ResultadoPruebaLaboratorio resultado = (ResultadoPruebaLaboratorio) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO resultados_pruebas_laboratorio (id_prueba_lab, id_paciente,"
							+ "resultado) VALUES (?,?,?)");
			enunciado.setInt(1, resultado.getPrueba().getId());
			enunciado.setInt(2, resultado.getPaciente().getId());
			enunciado.setString(3, resultado.getResultado());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion
				.prepareStatement("DELETE FROM resultados_pruebas_laboratorio WHERE id = "
						+ id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		resultados = new ArrayList<ResultadoPruebaLaboratorio>();
		try {
			resultado = consulta
					.executeQuery("SELECT * FROM `resultados_pruebas_laboratorio`");
			while (resultado.next()) {
				int id = resultado.getInt("id");
				int idPaciente = resultado.getInt("id_paciente");
				int idPrueba = resultado.getInt("id_prueba_lab");
				String result = resultado.getString("resultado");
				Almacen almacen = new Almacen();
				Paciente paciente = almacen.getItemPaciente(idPaciente);
				PruebaLaboratorio prueba = almacen
						.getItemPruebaLaboratorio(idPrueba);
				ResultadoPruebaLaboratorio resultadoPrueba = new ResultadoPruebaLaboratorio(
						id, paciente, prueba, result);
				resultados.add(resultadoPrueba);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultados;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {

		switch (atributo) {
		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE resultados_pruebas_laboratorio SET id_prueba_lab = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE resultados_pruebas_laboratorio SET id_paciente = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE resultados_pruebas_laboratorio SET resultado = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;
		}

	}
}