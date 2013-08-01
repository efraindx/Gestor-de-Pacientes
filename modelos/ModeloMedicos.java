package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.entidades.Medico;
import edu.itla.gestorpacientes.factorias.FactoriaGestionMedicos;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloMedicos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloMedicos instancia;
	private ArrayList<Medico> medicos;
	private String[] encabezados = { "Cod.Empleado", "Nombre", "Apellido",
			"Dirección", "Cédula", "Especialidad" };
	private Conexion conexion;
	private Medico medicoActual;

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
			medicoActual.setEspecialidad(getEspecialidadMedico((int) valor));
			valor = (int) valor + 1;
			break;

		case 2:
			medicoActual.setCodigoEmpleado((String) valor);
			break;

		case 3:
			medicoActual.setNombre((String) valor);
			break;

		case 4:
			medicoActual.setApellido((String) valor);
			break;

		case 5:
			medicoActual.setDireccion((String) valor);
			break;

		case 6:
			medicoActual.setCedula((String) valor);
			break;
		}
		conexion.modificar(id, atributo, valor);
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	public JComboBox<String> getEspecialidades() throws SQLException {
		return conexion.getEspecialidades();
	}

	public void setTelefonosPersona(int id, ArrayList<String> telefonos)
			throws SQLException {
		conexion.setTelefonosPersona(id, telefonos);
	}

	public ArrayList<Medico> getMedicos() {
		return medicos;
	}

	public String getEspecialidadMedico(int pos) throws SQLException {
		return conexion.getEspecialidades().getItemAt(pos);
	}
}
