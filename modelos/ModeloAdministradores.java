package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Administrador;
import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloAdministradores extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloAdministradores instancia;
	private ArrayList<Administrador> administradores;
	private String[] encabezados = { "Nombre", "Apellido", "Teléfono",
			"Dirección", "Cédula", "Especialidad", "CodigodeEmpleado" };

	public static synchronized ModeloAdministradores getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloAdministradores()
				: instancia;
	}

	public ModeloAdministradores() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		// administradores = Conexion.getInstancia(factoria);
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return administradores.size();
	}

	public void eliminarPaciente(int posicion) {

	}

	public void agregar(Object persona)
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		Conexion.getInstancia().agregar(persona);
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Administrador administradorActual = administradores.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = administradorActual.getNombre();
			break;

		case 1:
			retorno = administradorActual.getApellido();
			break;

		case 2:
			retorno = administradorActual.getTelefono();
			break;

		case 3:
			retorno = administradorActual.getDireccion();
			break;

		case 4:
			retorno = administradorActual.getCedula();
			break;

		case 5:
			retorno = administradorActual.getCodigoEmpleado();
			break;

		}
		return retorno;
	}

	@Override
	public void setValueAt(Object objeto, int fila, int columna) {
		Administrador administradorActual = administradores.get(fila);
		String valor = (String) objeto;
		switch (columna) {
		case 0:
			administradorActual.setNombre(valor);
			break;

		case 1:
			administradorActual.setApellido(valor);
			break;

		case 2:
			administradorActual.setTelefono(valor);
			break;

		case 3:
			administradorActual.setDireccion(valor);
			break;

		case 4:
			administradorActual.setCedula(valor);
			break;

		case 5:
			administradorActual.setCodigoEmpleado(valor);
			break;

		}
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}
}
