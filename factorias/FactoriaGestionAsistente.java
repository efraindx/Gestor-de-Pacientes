package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Asistente;
import com.efrain.gestorpacientes.persistencia.Conexion;

public class FactoriaGestionAsistente extends Conexion implements
		FactoriaGestion {

	private ArrayList<Asistente> asistentes;

	public FactoriaGestionAsistente() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {

	}

	@Override
	public void agregar(Object persona, String tabla) {
		Asistente asistente = (Asistente) persona;
		try {
			enunciado = conexion
					.prepareStatement("insert into "
							+ tabla
							+ "(nombre, apellido, telefono, direccion, codigodeempleado, cedula)"
							+ " values (?,?,?,?,?,?)");
			enunciado.setString(1, asistente.getNombre());
			enunciado.setString(2, asistente.getApellido());
			enunciado.setString(3, asistente.getTelefono());
			enunciado.setString(4, asistente.getDireccion());
			enunciado.setString(5, asistente.getCodigoEmpleado());
			enunciado.setString(6, asistente.getCedula());
			enunciado.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Agregando en Asistentes "
				+ tabla);

	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificiar() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList mostrar() {
		try {
			resultado = consulta.executeQuery("select * from asistentes");
			while (resultado.next()) {
				asistentes.add(new Asistente(resultado.getInt("id"), resultado
						.getString("nombre"), resultado.getString("apellido"),
						resultado.getString("telefono"), resultado
								.getString("direccion"), resultado
								.getString("cedula"), resultado
								.getString("codigodeEmpleado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return asistentes;
	}

}
