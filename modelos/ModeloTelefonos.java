package com.efrain.gestorpacientes.modelos;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ModeloTelefonos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> telefonos;
	private String[] encabezado = { "Teléfonos:" };
	private String telefonoActual;

	public ModeloTelefonos() {
		this.telefonos = new ArrayList<String>();
	}

	public void agregar(String telefono) {
		telefonos.add(telefono);
		fireTableDataChanged();
	}

	public void eliminar(int fila) {
		telefonos.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		telefonoActual = telefonos.get(fila);
		String retorno = null;
		switch (columna) {
		case 0:
			retorno = telefonoActual;
			break;
		}
		return retorno;
	}

	@Override
	public void setValueAt(Object valor, int fila, int columna) {
		String valor2 = (String) valor;
		switch (columna) {
		case 0:
			if (valor2.length() == 12) {
				if (telefonoEsValido(valor2)) {
					telefonos.set(fila, valor2);
				} else {
					JOptionPane
							.showMessageDialog(null,
									"Inserte un número correcto:\n*NNN-NNN-NNN ó \n*NNNNNNNNNN");
				}
			} else if (valor2.length() == 10) {
				if	(telefonoEsValido(valor2)) {
				String cod = valor2.substring(0, 3);
				String cod2 = valor2.substring(3, 6);
				String cod3 = valor2.substring(6, 10);
				valor2 = cod + "-" + cod2 + "-" + cod3;
				telefonos.set(fila, valor2);
				} else {
					JOptionPane
					.showMessageDialog(null,
									"Inserte un número correcto:\n*NNN-NNN-NNN ó \n*NNNNNNNNNN");
				}
			} else {
				JOptionPane
						.showMessageDialog(null,
								"Inserte un número correcto:\n*NNN-NNN-NNN ó \n*NNNNNNNNNN");
			}
			break;
		}
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

	public boolean telefonoEsValido(String telefono) {
		boolean retorno = false;
		if (telefono.length() == 12) {
			String cod = telefono.substring(3, 4);
			String cod2 = telefono.substring(7, 8);
			if (cod.equals("-") && cod2.equals("-")) {
				retorno = true;
			} else {
				retorno = false;
			}
		} else if (telefono.length() == 10) {
			String cod = telefono.substring(0, 3);
			String cod2 = telefono.substring(3, 6);
			String cod3 = telefono.substring(6, 10);
			try {
				Integer.parseInt(cod);
				Integer.parseInt(cod2);
				Integer.parseInt(cod3);
				retorno = true;
			} catch (Exception ex) {
				retorno = false;
			}
		} else {
			retorno = false;
		}
		return retorno;
	}
}
