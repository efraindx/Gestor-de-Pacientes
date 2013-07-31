package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.factorias.FactoriaGestion;
import com.efrain.gestorpacientes.factorias.FactoriaGestionPacientes;
import com.efrain.gestorpacientes.factorias.FactoriaGestionRecetas;
import com.efrain.gestorpacientes.entidades.Paciente;
import com.efrain.gestorpacientes.entidades.Receta;
import com.efrain.gestorpacientes.enums.Fumador;
import com.efrain.gestorpacientes.enums.Sexo;

import com.efrain.gestorpacientes.persistencia.Conexion;

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

	public void agregar(Receta receta) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion.agregar(receta);
		recetas.add(receta);
		fireTableDataChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			recetas = conexion.getDatos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		recetaActual = recetas.get(fila);
		String retorno = null;

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
			recetaActual.setIdPaciente((int)valor);
			break;

		case 2:
			recetaActual.setIdPadecimiento((int) valor);
			break;

		case 3:
			recetaActual.setMedicamentos((String) valor);
			break;
		}
		conexion.modificar(id, atributo, valor);
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
	
	public JComboBox<String> getPacientes() throws SQLException {
		return conexion.getPacientes();
	}
	
	public JComboBox<String> getPadecimientos() throws SQLException {
		return conexion.getPadecimientos();
	}
}
