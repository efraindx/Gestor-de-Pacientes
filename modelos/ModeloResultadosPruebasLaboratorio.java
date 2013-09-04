package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.PruebaLaboratorio;
import edu.itla.gestorpacientes.entidades.ResultadoPruebaLaboratorio;
import edu.itla.gestorpacientes.factorias.FactoriaGestion;
import edu.itla.gestorpacientes.factorias.FactoriaGestionResultadosPruebasLaboratorio;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloResultadosPruebasLaboratorio extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloResultadosPruebasLaboratorio instancia;
	private ArrayList<ResultadoPruebaLaboratorio> resultados;
	private String[] encabezados = { "Paciente", "Prueba", "Resultado" };
	private Conexion conexion;
	private ResultadoPruebaLaboratorio resultadoActual;
	private FactoriaGestion factoria;

	public static synchronized ModeloResultadosPruebasLaboratorio getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloResultadosPruebasLaboratorio()
				: instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloResultadosPruebasLaboratorio() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionResultadosPruebasLaboratorio());
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

	@SuppressWarnings("unchecked")
	public void agregar(ResultadoPruebaLaboratorio resultado)
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		resultados.add(resultado);
		conexion.agregar(resultado);
		fireTableDataChanged();
		resultados = conexion.getDatos();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		//Es necesario este setter
		try {
			conexion.setFactoria(new FactoriaGestionResultadosPruebasLaboratorio());
		} catch (ClassNotFoundException | JDOMException | IOException
				| SQLException e) {
			e.printStackTrace();
		}
		resultadoActual = resultados.get(fila);
		Object retorno = null;

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
			resultadoActual.setPrueba((PruebaLaboratorio) valor);
			conexion.modificar(id, atributo, resultadoActual.getPrueba()
					.getId());
			break;

		case 2:
			resultadoActual.setPaciente((Paciente) valor);
			conexion.modificar(id, atributo, resultadoActual.getPaciente()
					.getId());
			break;

		case 3:
			resultadoActual.setResultado((String) valor);
			conexion.modificar(id, atributo, valor);
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

	public JComboBox<String> getResultados_Pruebas_Laboratorio()
			throws SQLException {
		return conexion.getResultados_Pruebas_Laboratorio();
	}

	public JComboBox<Paciente> getPacientes() throws SQLException {
		return conexion.getPacientes();
	}

	public ArrayList<ResultadoPruebaLaboratorio> getResultados() {
		return resultados;
	}
}