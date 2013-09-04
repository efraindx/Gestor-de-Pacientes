package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.PruebaLaboratorio;
import edu.itla.gestorpacientes.factorias.FactoriaGestionPruebasLaboratorio;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloPruebasLaboratorio extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloPruebasLaboratorio instancia;
	private ArrayList<PruebaLaboratorio> pruebas;
	private String[] encabezados = { "Código", "Nombre" };
	private Conexion conexion;
	private PruebaLaboratorio pruebaActual;

	public static synchronized ModeloPruebasLaboratorio getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloPruebasLaboratorio()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloPruebasLaboratorio() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionPruebasLaboratorio());
		pruebas = conexion.getDatos();
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
		return pruebas.size();
	}

	public void eliminar(int fila) throws SQLException {
		pruebaActual = pruebas.get(fila);
		conexion.eliminar(pruebaActual.getId());
		pruebas.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	@SuppressWarnings("unchecked")
	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		pruebas.add((PruebaLaboratorio) persona);
		conexion.agregar(persona);
		fireTableDataChanged();
		pruebas = conexion.getDatos();
	}

	@Override
	public void setValueAt(Object valor, int fila, int columna) {
		pruebaActual = pruebas.get(fila);
		switch (columna) {

		case 0:
			pruebaActual.setCodigo((String) valor);
			try {
				conexion.modificar(pruebaActual.getId(), 1, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case 1:
			pruebaActual.setNombre((String) valor);
			try {
				conexion.modificar(pruebaActual.getId(), 2, valor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionPruebasLaboratorio());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
		pruebaActual = pruebas.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = pruebaActual.getCodigo();
			break;

		case 1:
			retorno = pruebaActual.getNombre();
			break;
		}
		return retorno;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public JComboBox<PruebaLaboratorio> getPruebas_Laboratorio()
			throws SQLException {
		return conexion.getPruebas_Laboratorio();
	}
}