package com.efrain.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Medico;
import com.efrain.gestorpacientes.factorias.FactoriaGestionMedicos;
import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloMedicos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloMedicos instancia;
	private ArrayList<Medico> medicos;
	private String[] encabezados = { "Cod.Empleado", "Nombre", "Apellido",
			"Dirección", "Cédula", "Especialidad", };
	private Conexion conexion;

	public static synchronized ModeloMedicos getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloMedicos() : instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloMedicos() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionMedicos());
		medicos = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return medicos.size();
	}

	public void eliminar(int fila) throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		Medico medicoActual = medicos.get(fila);
		conexion.eliminar(medicoActual.getId());
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		medicos.add((Medico) persona);
		conexion.agregar(persona);
	}  

	@Override
	public Object getValueAt(int fila, int columna) {
		Medico medicoActual = medicos.get(fila);
		Object retorno = null;
		switch (columna) {
		case 0:
			retorno = medicoActual.getCodigoEmpleado();
			break;

		case 1:
			retorno = medicoActual.getNombre();
			break;

		case 2:
			retorno = medicoActual.getApellido();
			break;

		case 3:
			retorno = medicoActual.getDireccion();
			break;

		case 4:
			retorno = medicoActual.getCedula();
			break;

		case 5:
			retorno = medicoActual.getEspecialidad();
			break;

		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object objeto, int fila, int columna) {
		Medico medicoActual = medicos.get(fila);
		switch (columna) {
		case 0:
			medicoActual.setNombre((String) objeto);
			break;

		case 1:
			medicoActual.setApellido((String) objeto);
			break;

		case 2:
			medicoActual.setTelefonos((ArrayList<String>) objeto);
			break;

		case 3:
			medicoActual.setDireccion((String) objeto);
			break;

		case 4:
			medicoActual.setCedula((String) objeto);
			break;

		case 5:
			medicoActual.setCodigoEmpleado((String) objeto);
			break;

		case 6:
			medicoActual.setEspecialidad((String) objeto);
			break;
		}
		System.out.println("pullaste1");
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	public JComboBox<String> getEspecialidades() throws SQLException {
		return conexion.getEspecialidades();
	}

	public ArrayList<Medico> getMedicos() {
		return medicos;
	}
}
