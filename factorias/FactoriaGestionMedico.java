package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.persistencia.Conexion;
import com.efrain.gestorpacientes.factorias.FactoriaGestion;
import com.efrain.gestorpacientes.entidades.Medico;

public class FactoriaGestionMedico extends Conexion implements FactoriaGestion {

	private ArrayList<Medico> medicos;

	public FactoriaGestionMedico() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {

	}

	@Override
	public void agregar(Object persona, String tabla) {
		Medico medico = (Medico) persona;
		try {
			enunciado = conexion
					.prepareStatement("insert into "
							+ tabla
							+ "(nombre, apellido, telefono, direccion, codigodeempleado, especialidad)"
							+ " values (?,?,?,?,?,?)");
			enunciado.setString(1, medico.getNombre());
			enunciado.setString(2, medico.getApellido());
			enunciado.setString(3, medico.getTelefono());
			enunciado.setString(4, medico.getDireccion());
			enunciado.setString(5, medico.getCodigoEmpleado());
			enunciado.setString(6, medico.getEspecialidad());
			enunciado.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Agregando en Medicos " + tabla);
	}

	@Override
	public void eliminar() {
		System.out.println("Eliminando Medico...");
	}

	@Override
	public void modificiar() {
		System.out.println("Modificando Medico...");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList mostrar() {
		try {
			resultado = consulta.executeQuery("select * from medicos");
			while (resultado.next()) {
				medicos.add(new Medico(resultado.getInt("id"), resultado
						.getString("nombre"), resultado.getString("apellido"),
						resultado.getString("telefono"), resultado
								.getString("direccion"), resultado
								.getString("cedula"), resultado
								.getString("codigodeEmpleado"), resultado
								.getString("especialidad")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medicos;
	}

}
