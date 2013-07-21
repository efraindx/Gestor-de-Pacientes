package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.factorias.FactoriaGestion;
import com.efrain.gestorpacientes.entidades.Medico;

public class FactoriaGestionMedico extends FactoriaGestion {

	private ArrayList<Medico> medicos;

	public FactoriaGestionMedico() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {
		
	}

	@Override
	public void agregar(Object persona) {
		Medico medico = (Medico) persona;
		try {
			enunciado = conexion
					.prepareStatement("insert into (nombre, apellido, telefono, direccion, codigodeempleado," +
							" especialidad) values (?,?,?,?,?,?)");
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
	}

	@Override
	public void eliminar(int fila) {
		System.out.println("Eliminando Medico...");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
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

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
