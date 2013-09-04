package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Especialidad;
import edu.itla.gestorpacientes.factorias.FactoriaGestionEspecialidades;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloEspecialidades extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static ModeloEspecialidades instancia;
	private ArrayList<Especialidad> especialidades;
	private Especialidad especialidadActual;
	private String[] encabezados = { "Código", "Nombre" };
	private Conexion conexion;

	public static synchronized ModeloEspecialidades getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloEspecialidades()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloEspecialidades() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionEspecialidades());
		especialidades = conexion.getDatos();
	}

	@SuppressWarnings("unchecked")
	public void agregar(Especialidad especialidad) throws SQLException {
		especialidades.add(especialidad);
		conexion.agregar(especialidad);
		fireTableDataChanged();
		especialidades = conexion.getDatos();
	}

	public void eliminar(int fila) throws SQLException {
		especialidadActual = especialidades.get(fila);
		especialidades.remove(fila);
		conexion.eliminar(especialidadActual.getId());
		fireTableRowsDeleted(fila, fila);
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public String getColumnName(int column) {
		return encabezados[column];
	}

	@Override
	public int getRowCount() {
		return especialidades.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionEspecialidades());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
		especialidadActual = especialidades.get(fila);
		String retorno = null;
		switch (columna) {
		case 0:
			retorno = especialidadActual.getCodigo();
			break;

		case 1:
			retorno = especialidadActual.getNombre();
			break;
		}
		return retorno;
	}

	@Override
	public void setValueAt(Object valor, int fila, int columna) {
		especialidadActual = especialidades.get(fila);
		try {
			switch (columna) {
			case 0:
				especialidadActual.setCodigo((String) valor);
				conexion.modificar(especialidadActual.getId(), 0, valor);
				break;

			case 1:
				especialidadActual.setNombre((String) valor);
				conexion.modificar(especialidadActual.getId(), 1, valor);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
}
