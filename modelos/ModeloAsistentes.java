package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Asistente;
import com.efrain.gestorpacientes.persistencia.Conexion;
import com.efrain.gestorpacientes.factorias.FactoriaGestionAsistentes;

public class ModeloAsistentes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloAsistentes instancia;
	private ArrayList<Asistente> asistentes;
	private String[] encabezados = { "Cod.Empleado", "Nombre", "Apellido",
			"Dirección", "Cédula", };
	private Conexion conexion;
	private Asistente asistenteActual;

	public static synchronized ModeloAsistentes getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloAsistentes()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloAsistentes() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionAsistentes());
		asistentes = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return asistentes.size();
	}

	public void eliminar(int fila) throws SQLException {
		asistenteActual = asistentes.get(fila);
		conexion.eliminar(asistenteActual.getId());
		asistentes.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		asistentes.add((Asistente) persona);
		conexion.agregar(persona);
		fireTableDataChanged();
	}

	public void modificar(int id, int atributo, int fila, Object valor)
			throws SQLException {
		asistenteActual = asistentes.get(fila);
		switch (atributo) {

		case 1:
			asistenteActual.setCodigoEmpleado((String) valor);
			break;

		case 2:
			asistenteActual.setNombre((String) valor);
			break;

		case 3:
			asistenteActual.setApellido((String) valor);
			break;

		case 4:
			asistenteActual.setDireccion((String) valor);
			break;

		case 5:
			asistenteActual.setCedula((String) valor);
			break;
		}
		conexion.modificar(id, atributo, valor);
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Asistente asistenteActual = asistentes.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = asistenteActual.getCodigoEmpleado();
			break;

		case 1:
			retorno = asistenteActual.getNombre();
			break;

		case 2:
			retorno = asistenteActual.getApellido();
			break;

		case 3:
			retorno = asistenteActual.getDireccion();
			break;

		case 4:
			retorno = asistenteActual.getCedula();
			break;
		}
		return retorno;
	}
	
	public void setTelefonosPersona(int id, ArrayList<String> telefonos)
			throws SQLException {
		conexion.setTelefonosPersona(id, telefonos);
	}

	public ArrayList<Asistente> getAsistentes() {
		return asistentes;
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
