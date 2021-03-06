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

public class ModeloMedicos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloMedicos instancia;
	private ArrayList<Medico> medicos;
	private String[] encabezados = { "Cod.Empleado", "Nombre", "Apellido",
			"Direcci�n", "C�dula", "Especialidad" };
	private Conexion conexion;
	private Medico medicoActual;

	public static synchronized ModeloMedicos getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloMedicos() : instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloMedicos() throws ClassNotFoundException, SQLException,
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
		medicoActual = medicos.get(fila);
		conexion.eliminar(medicoActual.getId());
		medicos.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		medicos.add((Medico) persona);
		conexion.agregar(persona);
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionMedicos());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
		medicoActual = medicos.get(fila);
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

	public void modificar(int id, int atributo, int fila, Object valor)
			throws SQLException {
		medicoActual = medicos.get(fila);
		switch (atributo) {
		case 1:
			medicoActual.setEspecialidad(conexion
					.getItemEspecialidad((int) valor));
			conexion.modificar(id, atributo, valor);
			break;

		case 2:
			medicoActual.setCodigoEmpleado((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 3:
			medicoActual.setNombre((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 4:
			medicoActual.setApellido((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 5:
			medicoActual.setDireccion((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 6:
			medicoActual.setCedula((String) valor);
			conexion.modificar(id, atributo, valor);
			break;
		}
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	public void setTelefonosPersona(int id, ArrayList<String> telefonos)
			throws SQLException {
		conexion.setTelefonosPersona(id, telefonos);
	}

	public ArrayList<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(ArrayList<Medico> medicos) {
		this.medicos = medicos;
		fireTableDataChanged();
	}

	public JComboBox<Especialidad> getEspecialidades() throws SQLException {
		return conexion.getEspecialidades();
	}
}
