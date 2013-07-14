package Factorias;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import persistencia.Conexion;
import entidades.Administrador;

public class FactoriaGestionAdministrador extends Conexion implements
		FactoriaGestion {
	private ArrayList<Administrador> administradores;

	public FactoriaGestionAdministrador() throws JDOMException, IOException,
			SQLException, ClassNotFoundException {

	}

	@Override
	public void agregar(Object persona, String tabla) {
		Administrador administrador = (Administrador) persona;
		try {
			enunciado = conexion
					.prepareStatement("insert into "
							+ tabla
							+ "(nombre, apellido, telefono, direccion, codigodeempleado, cedula)"
							+ " values (?,?,?,?,?,?)");
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
		System.out.println("Agregando en Administradores "
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

}
