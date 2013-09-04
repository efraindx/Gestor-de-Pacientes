package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.factorias.FactoriaGestionPacientes;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloBusquedaPacientes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Paciente> pacientesBusqueda;
	private String[] encabezados = { "Nombre", "Apellido", "Sexo", "Teléfono",
			"Dirección", "Cédula", "Fec. Nac.", "Fumador", "Alergias" };
	private Conexion conexion;
	private static ModeloBusquedaPacientes instancia;

	public static synchronized ModeloBusquedaPacientes getInstancia()
			throws SQLException, ClassNotFoundException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloBusquedaPacientes()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloBusquedaPacientes() throws SQLException,
			ClassNotFoundException, JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionPacientes());
		pacientesBusqueda = conexion.getDatos();
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
		return pacientesBusqueda.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionPacientes());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
		Paciente pacienteActual = pacientesBusqueda.get(fila);
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

	public ArrayList<Paciente> getPacientesBusqueda() {
		return pacientesBusqueda;
	}

	public void setPacientesBusqueda(ArrayList<Paciente> pacientesBusqueda) {
		this.pacientesBusqueda = pacientesBusqueda;
		fireTableDataChanged();
	}
	
	public ArrayList<Paciente> getPacientes(String palabra) throws SQLException {
		return conexion.getPacientes(palabra);
	}

	public ArrayList<String> getDatos(String columna, String tabla, int id)
			throws SQLException {
		return conexion.getDatos(columna, tabla, id);
	}
}
