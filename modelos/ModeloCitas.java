package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Cita;
import com.efrain.gestorpacientes.persistencia.Conexion;
import com.efrain.gestorpacientes.factorias.FactoriaGestionCitas;

public class ModeloCitas extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloCitas instancia;
	private ArrayList<Cita> citas;
	private String[] encabezados = { "Paciente", "Medico", "Fecha", "Hora",
			"Causa", };
	private Conexion conexion;
	private Cita citaActual;

	public static synchronized ModeloCitas getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloCitas() : instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloCitas() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionCitas());
		citas = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return citas.size();
	}

	public void eliminar(int fila) throws SQLException {
		citaActual = citas.get(fila);
		conexion.eliminar(citaActual.getId());
		citas.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		citas.add((Cita) persona);
		conexion.agregar(persona);
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object valor, int fila, int columna) {
		citaActual = citas.get(fila);
		switch (columna) {

		case 0:
			citaActual.setPaciente((String) valor);
			try {
				conexion.modificar(citaActual.getId(), 1, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case 1:
			citaActual.setMedico((String) valor);
			try {
				conexion.modificar(citaActual.getId(), 2, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case 2:
			citaActual.setFecha((String) valor);
			try {
				conexion.modificar(citaActual.getId(), 3, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case 3:
			citaActual.setHora((String) valor);
			try {
				conexion.modificar(citaActual.getId(), 4, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case 4:
			citaActual.setCausa((String) valor);
			try {
				conexion.modificar(citaActual.getId(), 5, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			citas = conexion.getDatos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		citaActual = citas.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = citaActual.getPaciente();
			break;

		case 1:
			retorno = citaActual.getMedico();
			break;

		case 2:
			retorno = citaActual.getFecha();
			break;

		case 3:
			retorno = citaActual.getHora();
			break;

		case 4:
			retorno = citaActual.getCausa();
			break;
		}
		return retorno;
	}

	public void setTelefonosPersona(int id, ArrayList<String> telefonos)
			throws SQLException {
		conexion.setTelefonosPersona(id, telefonos);
	}

	public ArrayList<Cita> getAsistentes() {
		return citas;
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

	public JComboBox<String> getPacientes() throws SQLException {
		return conexion.getPacientes();
	}

	public JComboBox<String> getMedicos() throws SQLException {
		return conexion.getMedicos();
	}
}