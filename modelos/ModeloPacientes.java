package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.factorias.FactoriaGestion;
import com.efrain.gestorpacientes.factorias.FactoriaGestionPacientes;
import com.efrain.gestorpacientes.entidades.Paciente;
import com.efrain.gestorpacientes.enums.Fumador;
import com.efrain.gestorpacientes.enums.Sexo;

import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloPacientes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloPacientes instancia;
	private ArrayList<Paciente> pacientes;
	private String[] encabezados = { "Nombre", "Apellido", "Sexo", "Teléfono",
			"Dirección", "Cédula", "Fec. Nac.", "Fumador", "Foto", "Alergias" };
	private Conexion conexion;
	private Paciente pacienteActual;
	private FactoriaGestion factoria;

	public static synchronized ModeloPacientes getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloPacientes()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloPacientes() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionPacientes());
		pacientes = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return pacientes.size();
	}

	public void eliminar(int fila) throws SQLException {
		pacienteActual = pacientes.get(fila);
		conexion.eliminar(pacienteActual.getId());
		pacientes.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public void agregar(Paciente paciente) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion.agregar(paciente);
		pacientes.add(paciente);
		fireTableDataChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			pacientes = conexion.getDatos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pacienteActual = pacientes.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = pacienteActual.getNombre();
			break;

		case 1:
			retorno = pacienteActual.getApellido();
			break;

		case 2:
			retorno = pacienteActual.getSexo();
			break;

		case 3:
			retorno = pacienteActual.getTelefono();
			break;

		case 4:
			retorno = pacienteActual.getDireccion();
			break;

		case 5:
			retorno = pacienteActual.getCedula();
			break;

		case 6:
			retorno = pacienteActual.getFecha_nacimiento();
			break;

		case 7:
			retorno = pacienteActual.getFumador();
			break;

		case 8:
			retorno = pacienteActual.getFoto();
			break;

		case 9:
			retorno = pacienteActual.getAlergias();
			break;
		}
		return retorno;
	}

	@Override
	public void setValueAt(Object objeto, int fila, int columna) {
		Paciente pacienteActual = pacientes.get(fila);
		String valor = (String) objeto;
		try {
			switch (columna) {

			case 0:
				if (!"".equals(valor)) {
				pacienteActual.setNombre(valor);
				conexion.modificar(pacienteActual.getId(), 0, valor);
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio.");
				}
				break;

			case 1:
				if (!"".equals(valor)) {
				pacienteActual.setApellido(valor);
				conexion.modificar(pacienteActual.getId(), 1, valor);
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio.");
				}
				break;

			case 2:
				if (!"".equals(valor)) {
					if (valor.equals(Sexo.FEMENINO.name()) || valor.equals(Sexo.MASCULINO.name())) { 
						pacienteActual.setSexo(valor);
						conexion.modificar(pacienteActual.getId(), 2, valor);
					} else {
						JOptionPane.showMessageDialog(null, "Debe ser uno de estos:\n-MASCULINO\n-FEMENINO");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio.");
				}
				break;

			case 3:
				if (!"".equals(valor)) {
				pacienteActual.setTelefono(valor);
				conexion.modificar(pacienteActual.getId(), 3, valor);
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio");
				}
				break;

			case 4:
				if (!"".equals(valor)) {
				pacienteActual.setDireccion(valor);
				conexion.modificar(pacienteActual.getId(), 4, valor);
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio.");
				}
				break;

			case 5:
				if (!"".equals(valor)) {
				pacienteActual.setCedula(valor);
				conexion.modificar(pacienteActual.getId(), 5, valor);
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio.");
				}
				break;

			case 6:
				if (!"".equals(valor)) {
				pacienteActual.setFecha_nacimiento(valor);
				conexion.modificar(pacienteActual.getId(), 6, valor);
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio.");
				}
				break;

			case 7:
				if (!"".equals(valor)) {
					if (valor.equals(Fumador.SI.name()) || valor.equals(Fumador.NO.name())) {
						pacienteActual.setFumador(valor);
						conexion.modificar(pacienteActual.getId(), 7, valor);
					} else {
						JOptionPane.showMessageDialog(null, "Debe de ser uno de los siguientes:\n-SI\n-NO");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Este campo es obligatorio.");
				}
				break;

			case 8:
				if ("".equals(valor)) {
					if (pacienteActual.getSexo().equals(Sexo.FEMENINO.name())) {
						valor = "/com/efrain/gestorpacientes/imágenes/woman.png";
					} else {
						valor = "/com/efrain/gestorpacientes/imágenes/man.png";
					}
				} 
				pacienteActual.setFoto(valor);
				conexion.modificar(pacienteActual.getId(), 8, valor);
				break;

			case 9:
				pacienteActual.setAlergias(valor);
				conexion.modificar(pacienteActual.getId(), 9, valor);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	public FactoriaGestion getFactoria() {
		return factoria;
	}
}
