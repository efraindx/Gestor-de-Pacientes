package com.efrain.gestorpacientes.modelos;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeloTelefonos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTelefonos instancia;
	private ArrayList<String> telefonos;
	private String[] encabezado = {"Telefono"};
	
	public static synchronized ModeloTelefonos getInstancia() {
		return instancia == null ? instancia = new ModeloTelefonos()
				: instancia;
	}

	public ModeloTelefonos() {
		this.telefonos = new ArrayList<String>();
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		String telefono = telefonos.get(fila);
		String retorno = null;
		switch (columna) {
		case 0:
			retorno = telefono;
			break;
		}
		return retorno;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public String getColumnName(int column) {
		return encabezado[column];
	}

	@Override
	public int getRowCount() {
		return telefonos.size();
	}

	public ArrayList<String> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(ArrayList<String> telefonos) {
		this.telefonos = telefonos;
		fireTableDataChanged();
	}
}
