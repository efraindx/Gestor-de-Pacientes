package com.efrain.gestorpacientes.modelos;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.efrain.gestorpacientes.entidades.Paciente;
import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloPacientes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloPacientes instancia;
	private ArrayList<Paciente> pacientes;
	private String[] encabezados = { "Nombre", "Apellido", "Teléfono",
			"Dirección", "Cédula", "Fec. Nac.", "Fumador", "Alergias" };

	public static synchronized ModeloPacientes getInstancia()
			throws ClassNotFoundException, SQLException {
		return instancia == null ? instancia = new ModeloPacientes()
				: instancia;
	}

	public ModeloPacientes() throws ClassNotFoundException, SQLException {
		pacientes = Conexion.getInstancia().getPacientes();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return pacientes.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Paciente pacienteActual = pacientes.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = pacienteActual.getNombre();
			break;

		case 1:
			retorno = pacienteActual.getApellido();
			break;

		case 2:
			retorno = pacienteActual.getTelefono();
			break;

		case 3:
			retorno = pacienteActual.getDireccion();
			break;

		case 4:
			retorno = pacienteActual.getCedula();
			break;

		case 5:
			retorno = pacienteActual.getFecha_nacimiento();
			break;

		case 6:
			retorno = pacienteActual.getFumador();
			break;

		case 7:
			retorno = pacienteActual.getAlergias();
			break;
		}
		return retorno;
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}
}
