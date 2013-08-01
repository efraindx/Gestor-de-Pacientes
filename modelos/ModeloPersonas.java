package edu.itla.gestorpacientes.modelos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.encriptación.Encriptadora;
import edu.itla.gestorpacientes.entidades.Persona;
import edu.itla.gestorpacientes.factorias.FactoriaGestionPersonas;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class ModeloPersonas extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Persona> personas;
	private static ModeloPersonas instancia;
	private String[] encabezados = { "Nombre", "Apellido", "Rol", "Usuario",
			"Contraseña", "E-mail" };
	private Conexion conexion;

	public static synchronized ModeloPersonas getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloPersonas() : instancia;
	}

	@SuppressWarnings("unchecked")
	private ModeloPersonas() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		conexion.setFactoria(new FactoriaGestionPersonas());
		personas = conexion.getDatos();
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
			
		case 5:
			retorno = personaActual.getCorreo();
			break;
		}
		return retorno;
	}

	public void agregar(Persona persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		conexion.agregar(persona);
		personas.add(persona);
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
				personaActual.setContrase(Encriptadora.encriptar(valor));
				conexion.modificar(personaActual.getId(), 7, Encriptadora.encriptar(valor));
				break;
				
			case 5:
				personaActual.setCorreo(valor);
				conexion.modificar(personaActual.getId(), 8, valor);
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

	public JComboBox<String> getRoles() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		return conexion.getRoles();
	}
	
	public JComboBox<String> getPersonas() throws SQLException {
		return conexion.getPersonas();
	}
	
	public boolean usuarioExiste(String usuario) {
		boolean retorno = false;
		for (Persona p: personas) {
			if(p.getUsuario().equals(usuario)) {
				return true;
			}
		}
		return retorno;
	}
}
