package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.factorias.FactoriaGestion;
import com.efrain.gestorpacientes.factorias.FactoriaGestion_Resultado_Pruebas_Laboratorio;
import com.efrain.gestorpacientes.entidades.Resultado_Prueba_Laboratorio;

import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloResultados_Prueba_Laboratorio extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloResultados_Prueba_Laboratorio instancia;
	private ArrayList<Resultado_Prueba_Laboratorio> resultados;
	private String[] encabezados = { "Paciente", "Prueba", "Resultado" };
	private Conexion conexion;
	private Resultado_Prueba_Laboratorio resultadoActual;
	private FactoriaGestion factoria;

	public static synchronized ModeloResultados_Prueba_Laboratorio getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloResultados_Prueba_Laboratorio()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloResultados_Prueba_Laboratorio()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestion_Resultado_Pruebas_Laboratorio());
		resultados = conexion.getDatos();
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return resultados.size();
	}

	public void eliminar(int fila) throws SQLException {
		resultadoActual = resultados.get(fila);
		conexion.eliminar(resultadoActual.getId());
		resultados.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public void agregar(Resultado_Prueba_Laboratorio resultado) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion.agregar(resultado);
		resultados.add(resultado);
		fireTableDataChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int fila, int columna) {
		try {
			resultados = conexion.getDatos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultadoActual = resultados.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = resultadoActual.getPaciente();
			break;

		case 1:
			retorno = resultadoActual.getPrueba();
			break;

		case 2:
			retorno = resultadoActual.getResultado();
			break;
		}
		return retorno;
	}

	public void modificar(int id, int atributo, int fila, Object valor)
			throws SQLException {
		resultadoActual = resultados.get(fila);
		switch (atributo) {
		case 1:
			resultadoActual.setIdPrueba((int) valor);
			break;

		case 2:
			resultadoActual.setIdPaciente((int) valor);
			break;

		case 3:
			resultadoActual.setResultado((String) valor);
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

	public JComboBox<String> getResultados_Pruebas_Laboratorio() throws SQLException {
		return conexion.getResultados_Pruebas_Laboratorio();
	}
	
	public JComboBox<String> getPacientes() throws SQLException {
		return conexion.getPacientes();
	}
	
	public ArrayList<Resultado_Prueba_Laboratorio>  getResultados() {
		return resultados;
	}
}