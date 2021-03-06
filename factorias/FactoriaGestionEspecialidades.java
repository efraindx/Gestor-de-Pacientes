package edu.itla.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Especialidad;

public class FactoriaGestionEspecialidades extends FactoriaGestion {

	private ArrayList<Especialidad> especialidades;

	public FactoriaGestionEspecialidades() throws ClassNotFoundException,
			JDOMException, IOException, SQLException {
		iniciarComponentes();
	}

	@Override
	public void agregar(Object persona) {
		Especialidad especialidadActual = (Especialidad) persona;
		try {
			enunciado = conexion
					.prepareStatement("INSERT INTO especialidades (codigo, nombre) VALUES (?,?)");
			enunciado.setString(1, especialidadActual.getCodigo());
			enunciado.setString(2, especialidadActual.getNombre());
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void eliminar(int id) {
		try {
			enunciado = conexion
					.prepareStatement("DELETE FROM especialidades where id = ?");
			enunciado.setInt(1, id);
			enunciado.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {
		switch (atributo) {
		case 0:
			enunciado = conexion
					.prepareStatement("UPDATE especialidades set codigo = ? where id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;

		case 1:
			enunciado = conexion
					.prepareStatement("UPDATE especialidades set nombre = ? where id = ?");
			enunciado.setString(1, (String) valor);
			enunciado.setInt(2, id);
			enunciado.execute();
			break;
		}

	}

	@Override
	public ArrayList<?> getDatos() {
		especialidades = new ArrayList<Especialidad>();
		try {
			resultado = consulta.executeQuery("SELECT * FROM especialidades");
			while (resultado.next()) {
				if (!resultado.getString("nombre").equals("Ninguna")) {
					especialidades.add(new Especialidad(resultado.getInt("id"),
							resultado.getString("codigo"), resultado
									.getString("nombre")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return especialidades;
	}

}
