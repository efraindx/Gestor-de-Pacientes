package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Especialidad;
import edu.itla.gestorpacientes.entidades.Medico;
import edu.itla.gestorpacientes.factorias.FactoriaGestionMedicos;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloBusquedaMedicos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Medico> medicos;
	private String[] encabezados = { "Cod.Empleado", "Nombre", "Apellido",
			"Dirección", "Cédula", "Especialidad" };
	private Conexion conexion;
	private static ModeloBusquedaMedicos instancia;

	public static synchronized ModeloBusquedaMedicos getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloBusquedaMedicos()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloBusquedaMedicos() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionMedicos());
		medicos = conexion.getDatos();
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
		return medicos.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionMedicos());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
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

	public JComboBox<Especialidad> getEspecialidades() throws SQLException {
		return conexion.getEspecialidades();
	}

	public ArrayList<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(ArrayList<Medico> medicos) {
		this.medicos = medicos;
		fireTableDataChanged();
	}

	public ArrayList<Medico> getMedicos(int idEsp) throws SQLException {
		return conexion.getMedicos(idEsp);
	}
}
