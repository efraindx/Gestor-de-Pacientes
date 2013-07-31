package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Prueba_Laboratorio;
import com.efrain.gestorpacientes.persistencia.Conexion;
import com.efrain.gestorpacientes.factorias.FactoriaGestionPruebas_Laboratorio;

public class ModeloPruebas_Laboratorio extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloPruebas_Laboratorio instancia;
	private ArrayList<Prueba_Laboratorio> pruebas;
	private String[] encabezados = { "C�digo", "Nombre" };
	private Conexion conexion;
	private Prueba_Laboratorio pruebaActual;

	public static synchronized ModeloPruebas_Laboratorio getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloPruebas_Laboratorio()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloPruebas_Laboratorio() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionPruebas_Laboratorio());
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

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		pruebas.add((Prueba_Laboratorio) persona);
		conexion.agregar(persona);
		fireTableDataChanged();
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

	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			pruebas = conexion.getDatos();
		} catch (SQLException e) {
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
}