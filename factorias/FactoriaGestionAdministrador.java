package com.efrain.gestorpacientes.factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import com.efrain.gestorpacientes.entidades.Administrador;

public class FactoriaGestionAdministrador extends FactoriaGestion {
	private ArrayList<Administrador> administradores;

	public FactoriaGestionAdministrador() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {

	}

	@Override
	public void agregar(Object persona) {
		Administrador administrador = (Administrador) persona;
		try {
			enunciado = conexion
					.prepareStatement("insert into administradores(nombre, apellido, telefono, direccion, "
							+ "codigodeempleado, cedula) values (?,?,?,?,?,?)");
			enunciado.setString(1, administrador.getNombre());
			enunciado.setString(2, administrador.getApellido());
			enunciado.setString(3, administrador.getTelefono());
			enunciado.setString(4, administrador.getDireccion());
			enunciado.setString(5, administrador.getCodigoEmpleado());
			enunciado.setString(6, administrador.getCedula());
			enunciado.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void eliminar(int fila) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList getDatos() {
		try {
			resultado = consulta.executeQuery("select * from administradores");
			while (resultado.next()) {
				administradores.add(new Administrador(resultado.getInt("id"),
						resultado.getString("nombre"), resultado
								.getString("apellido"), resultado
								.getString("telefono"), resultado
								.getString("direccion"), resultado
								.getString("cedula"), resultado
								.getString("codigodeEmpleado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administradores;
	}

	@Override
	public void modificar(int id, int atributo, Object valor)
			throws SQLException {
	}

}
