package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Asistente;
import edu.itla.gestorpacientes.factorias.FactoriaGestionAsistentes;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloAsistentes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloAsistentes instancia;
	private ArrayList<Asistente> asistentes;
	private String[] encabezados = { "Cod.Empleado", "Nombre", "Apellido",
			"Dirección", "Cédula", };
	private Conexion conexion;
	private Asistente asistenteActual;

	public static synchronized ModeloAsistentes getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloAsistentes()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloAsistentes() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionAsistentes());
		asistentes = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return asistentes.size();
	}

	public void eliminar(int fila) throws SQLException {
		asistenteActual = asistentes.get(fila);
		conexion.eliminar(asistenteActual.getId());
		asistentes.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		asistentes.add((Asistente) persona);
		conexion.agregar(persona);
		fireTableDataChanged();
	}

	public void modificar(int id, int atributo, int fila, Object valor)
			throws SQLException {
		asistenteActual = asistentes.get(fila);
		switch (atributo) {

		case 1:
			asistenteActual.setCodigoEmpleado((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 2:
			asistenteActual.setNombre((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 3:
			asistenteActual.setApellido((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 4:
			asistenteActual.setDireccion((String) valor);
			conexion.modificar(id, atributo, valor);
			break;

		case 5:
			asistenteActual.setCedula((String) valor);
			conexion.modificar(id, atributo, valor);
			break;
		}
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionAsistentes());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
		asistenteActual = asistentes.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = asistenteActual.getCodigoEmpleado();
			break;

		case 1:
			retorno = asistenteActual.getNombre();
			break;

		case 2:
			retorno = asistenteActual.getApellido();
			break;

		case 3:
			retorno = asistenteActual.getDireccion();
			break;

		case 4:
			retorno = asistenteActual.getCedula();
			break;
		}
		return retorno;
	}

	public void setTelefonosPersona(int id, ArrayList<String> telefonos)
			throws SQLException {
		conexion.setTelefonosPersona(id, telefonos);
	}

	public ArrayList<Asistente> getAsistentes() {
		return asistentes;
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}
}
