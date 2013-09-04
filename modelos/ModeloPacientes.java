package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.factorias.FactoriaGestion;
import edu.itla.gestorpacientes.factorias.FactoriaGestionPacientes;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloPacientes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloPacientes instancia;
	private ArrayList<Paciente> pacientes;
	private String[] encabezados = { "Nombre", "Apellido", "Sexo", "Teléfono",
			"Dirección", "Cédula", "Fec. Nac.", "Fumador", "Alergias" };
	private Conexion conexion;
	private Paciente pacienteActual;
	private FactoriaGestion factoria;
	private boolean editable = true;

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

	@SuppressWarnings("unchecked")
	public void agregar(Paciente paciente) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		pacientes.add(paciente);
		conexion.agregar(paciente);
		fireTableDataChanged();
		pacientes = conexion.getDatos();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionPacientes());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
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
			retorno = pacienteActual.getAlergias();
			break;
			
		}
		return retorno;
	}

	public void modificar(int id, int atributo, int fila, Object valor)
			throws SQLException {
		pacienteActual = pacientes.get(fila);
		switch (atributo) {

		case 0:
			pacienteActual.setNombre((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 1:
			pacienteActual.setApellido((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 2:
			pacienteActual.setSexo((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 3:
			pacienteActual.setTelefono((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 4:
			pacienteActual.setDireccion((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 5:
			pacienteActual.setCedula((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 6:
			pacienteActual.setFecha_nacimiento((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 7:
			pacienteActual.setFumador((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 8:
			pacienteActual.setFoto((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 9:
			pacienteActual.setAlergias((String) valor);
			conexion.modificar(id, atributo, valor);
			break;
		}
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return editable;
	}

	public FactoriaGestion getFactoria() {
		return factoria;
	}

	public void setEditable(boolean valor) {
		this.editable = valor;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Paciente> getPacientes() throws SQLException {
		return conexion.getDatos();
	}

	public ArrayList<Paciente> getListadoPacientes() throws SQLException {
		return conexion.getListadoPacientes();
	}

	public void setPacientes(ArrayList<Paciente> pacientes) {
		this.pacientes = pacientes;
		fireTableDataChanged();
	}

	public ArrayList<String> getDatos(String columna, String tabla, int id)
			throws SQLException {
		return conexion.getDatos(columna, tabla, id);
	}

}
