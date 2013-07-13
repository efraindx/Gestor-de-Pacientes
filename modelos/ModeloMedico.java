package modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import Factorias.FactoriaGestionMedico;
import entidades.Medico;
import persistencia.Conexion;

public class ModeloMedico extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloMedico instancia;
	private ArrayList<Medico> medicos;
	private String[] encabezados = { "Nombre", "Apellido", "Tel�fono",
			"Direcci�n", "C�dula", "Especialidad", "CodigodeEmpleado" };

	public static synchronized ModeloMedico getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloMedico() : instancia;
	}

	public ModeloMedico() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		Conexion.getInstancia().setFactoria(new FactoriaGestionMedico());
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
	}

	@Override
	public int getRowCount() {
		return medicos.size();  
	}

	public void eliminar() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		Conexion.getInstancia().eliminar();
	}

	public void agregar(Object  persona, String tabla)
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		Conexion.getInstancia().agregar(persona, tabla);
	}
			
	@Override
	public Object getValueAt(int fila, int columna) {
		Medico medicoActual = medicos.get(fila);
		String retorno = null;

		switch (columna) {
		case 0:
			retorno = medicoActual.getNombre();
			break;

		case 1:
			retorno = medicoActual.getApellido();
			break;

		case 2:
			retorno = medicoActual.getTelefono();
			break;

		case 3:
			retorno = medicoActual.getDireccion();
			break;

		case 4:
			retorno = medicoActual.getCedula();
			break;

		case 5:
			retorno = medicoActual.getCodigoEmpleado();
			break;

		case 6:
			retorno = medicoActual.getEspecialidad();
			break;

		}
		return retorno;
	}

	@Override
	public void setValueAt(Object objeto, int fila, int columna) {
		Medico medicoActual = medicos.get(fila);
		String valor = (String) objeto;
		switch (columna) {
		case 0:
			medicoActual.setNombre(valor);
			break;

		case 1:
			medicoActual.setApellido(valor);
			break;

		case 2:
			medicoActual.setTelefono(valor);
			break;

		case 3:
			medicoActual.setDireccion(valor);
			break;

		case 4:
			medicoActual.setCedula(valor);
			break;

		case 5:
			medicoActual.setCodigoEmpleado(valor);
			break;

		case 6:
			medicoActual.setEspecialidad(valor);
			break;
		}
	}

	@Override
	public String getColumnName(int columna) {
		return encabezados[columna];
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}
	
}