package com.efrain.gestorpacientes.modelos;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.factorias.FactoriaGestionPersonas;
import com.efrain.gestorpacientes.persistencia.Conexion;

public class ModeloPersonas extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Persona> personas;
	private static ModeloPersonas instancia;
	private String[] encabezados = { "Nombre", "Apellido", "Rol", "Usuario",
			"Contraseña" };

	public static synchronized ModeloPersonas getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new ModeloPersonas() : instancia;
	}

	@SuppressWarnings("unchecked")
	public ModeloPersonas() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		Conexion.getInstancia().setFactoria(new FactoriaGestionPersonas());
		personas = Conexion.getInstancia().getDatos();
	}

	public ArrayList<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(ArrayList<Persona> personas) {
		this.personas = personas;
	}

	@Override
	public int getColumnCount() {
		return encabezados.length;
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
			retorno = personaActual.getContrase();
			break;
		}
		return retorno;
	}

	public void agregar(Object persona) throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		Persona personaActual = (Persona) persona;
		Conexion.getInstancia().agregar(persona);
		personas.add(personaActual);
		fireTableDataChanged();
	}

	public void eliminar(int fila) throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		Persona personaActual = personas.get(fila);
		Conexion.getInstancia().eliminar(personaActual.getId());
		personas.remove(fila);
	}

	@Override
	public void setValueAt(Object objeto, int fila, int columna) {
		Persona personaActual = personas.get(fila);
		String valor = (String) objeto;
		switch(columna) {
		case 0:
			personaActual.setNombre(valor);
			//Conexion.getInstancia().modificiar(per, atributo, valor)
		break;
		
		case 1:
			personaActual.setApellido(valor);
		break;
		
		case 2:
			personaActual.setRol(valor);
		break;
		
		case 3:
			personaActual.setUsuario(valor);
		break;
		
		case 4:
			personaActual.setContrase(valor);
		break;
		}
	}

	public ArrayList<?> getDatos() {
		return null;
	}
}
