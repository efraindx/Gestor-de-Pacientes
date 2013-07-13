package com.efrain.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.factorias.FactoriaGestion;
import com.efrain.gestorpacientes.entidades.Paciente;
import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloPacientes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloPacientes instancia;
	private ArrayList<Paciente> pacientes;
	private String[] encabezados = { "Nombre", "Apellido", "Teléfono",
			"Dirección", "Cédula", "Fec. Nac.", "Fumador", "Foto", "Alergias" };

	private FactoriaGestion factoria;
	public static synchronized ModeloPacientes getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new ModeloPacientes()
				: instancia;
	}
/*
	public ModeloPacientes() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		pacientes = Conexion.getInstancia().getPacientes();
	}
*/
	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return pacientes.size();
	}
	
	public void eliminarPaciente(int posicion) {
		
	}
	/*
	public void agregarPaciente(Paciente paciente) throws ClassNotFoundException, SQLException, JDOMException, IOException {
		Conexion.getInstancia().agregarPaciente(paciente);
		fireTableDataChanged();
	}*/

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
			retorno = pacienteActual.getFoto();
		break;

		case 8:
			retorno = pacienteActual.getAlergias();
			break;
		}
		return retorno;
	}

	@Override
	public void setValueAt(Object objeto, int fila, int columna) {
		Paciente pacienteActual = pacientes.get(fila);
		String valor = (String)objeto;
		switch (columna) {
		case 0:
			pacienteActual.setNombre(valor);
		break;

		case 1:
			pacienteActual.setApellido(valor);
		break;

		case 2:
			pacienteActual.setTelefono(valor);
		break;
		
		case 3:
			pacienteActual.setDireccion(valor);
		break;
		
		case 4:
			pacienteActual.setCedula(valor);
		break;
		
		case 5:
			pacienteActual.setFecha_nacimiento(valor);
		break;
		
		case 6:
			pacienteActual.setFumador(valor);
		break;
		
		case 7:
			pacienteActual.setFoto(valor);
		break;
		
		case 8:
			pacienteActual.setAlergias(valor);
		break;
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

	public void setFactoria(FactoriaGestion factoria) {
		this.factoria = factoria;
		try {
			Conexion.getInstancia().setFactoria(factoria);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
