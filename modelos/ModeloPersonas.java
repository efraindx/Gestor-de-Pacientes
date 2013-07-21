package com.efrain.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.encriptación.Encriptadora;
import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.factorias.FactoriaGestionPersonas;
import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloPersonas extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Persona> personas;
	private static ModeloPersonas instancia;
	private String[] encabezados = { "Nombre", "Apellido", "Rol", "Usuario",
			"Contraseña" };
	private Conexion conexion;

	public static synchronized ModeloPersonas getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloPersonas() : instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloPersonas() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionPersonas());
		personas = Conexion.getInstancia().getDatos();

	}

	public ArrayList<Persona> getUsuarios() {
		return personas;
	}

	public void setPersonas(ArrayList<Persona> usuarios) {
		this.personas = usuarios;
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
		return personas.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		String retorno = null;
		Persona personaActual = personas.get(fila);

		switch (columna) {
		case 0:
			retorno = personaActual.getNombre();
			break;

		case 1:
			retorno = personaActual.getApellido();
			break;

		case 2:
			retorno = personaActual.getRol();
			break;

		case 3:
			retorno = personaActual.getUsuario();
			break;

		case 4:
			retorno = Encriptadora.desencriptar(personaActual.getContrase());
			break;
		}
		return retorno;
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		Persona personaActual = (Persona) persona;
		conexion.agregar(persona);
		personas.add(personaActual);
		fireTableDataChanged();
	}

	public void eliminar(int fila) throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		Persona usuarioActual = personas.get(fila);
		conexion.eliminar(usuarioActual.getId());
		personas.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	@Override
	public void setValueAt(Object objeto, int fila, int columna) {
		try {
			Persona personaActual = personas.get(fila);
			String valor = (String) objeto;
			switch (columna) {
			case 0:
				personaActual.setNombre(valor);
				conexion.modificar(personaActual.getId(), 3, valor);
				break;

			case 1:
				personaActual.setApellido(valor);
				conexion.modificar(personaActual.getId(), 4, valor);
				break;

			case 2:
				personaActual.setRol(valor);
				if("ADMINISTRADOR".equals(valor) || "MEDICO".equals(valor) || "ASISTENTE".equals(valor)) {
					conexion.modificar(personaActual.getId(), 5, valor);
				} else {
					JOptionPane.showMessageDialog(null, "Debe de ser uno de los siguientes valores:\n-ADMINISTRADOR\n-MEDICO\n-ASISTENTE\nTodo en mayúscula.");
				}
				break;

			case 3:
				personaActual.setUsuario(valor);
				conexion.modificar(personaActual.getId(), 6, valor);
				break;

			case 4:
				personaActual.setContrase(valor);
				conexion.modificar(personaActual.getId(), 7, Encriptadora.encriptar(valor));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

	public ArrayList<String> getRoles() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		return Conexion.getInstancia().getRoles();
	}
}
