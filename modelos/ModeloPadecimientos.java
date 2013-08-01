package edu.itla.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.entidades.Padecimiento;
import edu.itla.gestorpacientes.factorias.FactoriaGestionPadecimientos;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloPadecimientos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloPadecimientos instancia;
	private ArrayList<Padecimiento> padecimientos;
	private String[] encabezados = { "Código", "Nombre" };
	private Conexion conexion;
	private Padecimiento padecimientoActual;

	public static synchronized ModeloPadecimientos getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloPadecimientos()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloPadecimientos() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionPadecimientos());
		padecimientos = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}
	
	 @Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	@Override
	public int getRowCount() {
		return padecimientos.size();
	}

	public void eliminar(int fila) throws SQLException {
		padecimientoActual = padecimientos.get(fila);
		conexion.eliminar(padecimientoActual.getId());
		padecimientos.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		padecimientos.add((Padecimiento) persona);
		conexion.agregar(persona);
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object valor, int fila, int columna) {
		padecimientoActual = padecimientos.get(fila);
		switch (columna) {

		case 0:
			padecimientoActual.setCodigo((String) valor);
			try {
				conexion.modificar(padecimientoActual.getId(), 1, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case 1:
			padecimientoActual.setNombre((String) valor);
			try {
				conexion.modificar(padecimientoActual.getId(), 2, valor);
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
			padecimientos = conexion.getDatos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		padecimientoActual = padecimientos.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = padecimientoActual.getCodigo();
			break;

		case 1:
			retorno = padecimientoActual.getNombre();
			break;
		}
		return retorno;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
}