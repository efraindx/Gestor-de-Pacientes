package edu.itla.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.entidades.Cita;
import edu.itla.gestorpacientes.factorias.FactoriaGestionCitas;
import edu.itla.gestorpacientes.persistencia.Conexion;

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

	public void modificar(int id, int atributo, int fila, Object valor)
			throws SQLException {
		citaActual = citas.get(fila);
		switch (atributo) {
		case 1:
			citaActual.setIdPaciente((int) valor);
			break;

		case 2:
			citaActual.setMedico((String) valor);
			break;

		case 3:
			citaActual.setFecha((String) valor);
			break;

		case 4:
			citaActual.setHora((String) valor);
			break;

		case 5:
			citaActual.setCausa((String) valor);
			break;
		}
		conexion.modificar(id, atributo, valor);
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

	public ArrayList<Cita> getCitas() {
		return citas;
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	public JComboBox<String> getPacientes() throws SQLException {
		return conexion.getPacientes();
	}

	public JComboBox<String> getMedicos() throws SQLException {
		return conexion.getMedicos();
	}
}