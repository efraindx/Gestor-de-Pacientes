package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.Padecimiento;
import edu.itla.gestorpacientes.entidades.Receta;
import edu.itla.gestorpacientes.factorias.FactoriaGestion;
import edu.itla.gestorpacientes.factorias.FactoriaGestionRecetas;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloRecetas extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloRecetas instancia;
	private ArrayList<Receta> recetas;
	private String[] encabezados = { "Paciente", "Padecimiento", "Medicamentos" };
	private Conexion conexion;
	private Receta recetaActual;
	private FactoriaGestion factoria;

	public static synchronized ModeloRecetas getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloRecetas() : instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloRecetas() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionRecetas());
		recetas = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return recetas.size();
	}

	public void eliminar(int fila) throws SQLException {
		recetaActual = recetas.get(fila);
		conexion.eliminar(recetaActual.getId());
		recetas.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	@SuppressWarnings("unchecked")
	public void agregar(Receta receta) throws SQLException {
		recetas.add(receta);
		conexion.agregar(receta);
		fireTableDataChanged();
		recetas = conexion.getDatos();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			conexion.setFactoria(new FactoriaGestionRecetas());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
		recetaActual = recetas.get(fila);
		Object retorno = null;

		switch (columna) {
		case 0:
			retorno = recetaActual.getPaciente();
			break;

		case 1:
			retorno = recetaActual.getPadecimiento();
			break;

		case 2:
			retorno = recetaActual.getMedicamentos();
			break;
		}
		return retorno;
	}

	public void modificar(int id, int atributo, int fila, Object valor)
			throws SQLException {
		recetaActual = recetas.get(fila);
		switch (atributo) {
		case 1:
			recetaActual.setPaciente((Paciente) valor);
			conexion.modificar(id, atributo, recetaActual.getPaciente().getId());
			break;

		case 2:
			recetaActual.setPadecimiento((Padecimiento) valor);
			conexion.modificar(id, atributo, recetaActual.getPadecimiento()
					.getId());
			break;

		case 3:
			recetaActual.setMedicamentos((String) valor);
			conexion.modificar(id, atributo, (String) valor);
			break;
		}
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	public FactoriaGestion getFactoria() {
		return factoria;
	}

	public ArrayList<Receta> getRecetas() {
		return recetas;
	}

	public JComboBox<Paciente> getPacientes() throws SQLException {
		return conexion.getPacientes();
	}

	public JComboBox<Padecimiento> getPadecimientos() throws SQLException {
		return conexion.getPadecimientos();
	}
}
