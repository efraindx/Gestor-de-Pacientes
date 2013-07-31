package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Resultado_Prueba_Laboratorio;

public class FactoriaGestion_Resultado_Pruebas_Laboratorio extends FactoriaGestion {

	private ArrayList<Resultado_Prueba_Laboratorio> resultados;

	public FactoriaGestion_Resultado_Pruebas_Laboratorio() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Resultado_Prueba_Laboratorio prueba = (Resultado_Prueba_Laboratorio) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO resultados_pruebas_laboratorio (id_prueba_lab, id_paciente,"
							+ "resultado) VALUES (?,?,?)");
			enunciado.setInt(1, prueba.getIdPrueba());
			enunciado.setInt(2, prueba.getIdPaciente());
			enunciado.setString(3, prueba.getResultado());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) throws SQLException {
		enunciado = conexion.prepareStatement("DELETE FROM resultados_pruebas_laboratorio WHERE id = "
				+ id);
		enunciado.execute();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		resultados = new ArrayList<Resultado_Prueba_Laboratorio>();
		try {
			consulta = conexion.createStatement();
			resultado = consulta.executeQuery("SELECT * FROM resultados_pruebas_laboratorio");
			while (resultado.next()) {
				int id = resultado.getInt("id");
				int id_prueba_lab = resultado.getInt("id_prueba_lab");
				int id_paciente = resultado.getInt("id_paciente");
				String result = resultado.getString("resultado");
				Resultado_Prueba_Laboratorio resultadoP = new Resultado_Prueba_Laboratorio();
				resultadoP.setIdPrueba(id_prueba_lab);
				resultadoP.setIdPaciente(id_paciente);
				resultadoP.setId(id);
				resultadoP.setPaciente(con.getPacientes().getItemAt(id_paciente));
				resultadoP.setPrueba(con.getPruebas_Laboratorio().getItemAt(id_prueba_lab));
				resultadoP.setResultado(result);
				resultados.add(resultadoP);
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
			break;

		case 2:
			enunciado = conexion
					.prepareStatement("UPDATE resultados_pruebas_laboratorio SET id_paciente = ? WHERE id = ?");
			enunciado.setInt(1, (int) valor);
			break;
			
		case 3:
			enunciado = conexion
					.prepareStatement("UPDATE resultados_pruebas_laboratorio SET resultado = ? WHERE id = ?");
			enunciado.setString(1, (String) valor);
			break;
		}
		enunciado.setInt(2, id);
		enunciado.execute();
	}
}