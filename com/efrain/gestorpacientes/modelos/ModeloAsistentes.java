package com.efrain.gestorpacientes.modelos;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Asistente;
import com.efrain.gestorpacientes.factorias.FactoriaGestionAsistente;
import com.efrain.gestorpacientes.persistencia.Conexion;



		public class ModeloAsistentes extends AbstractTableModel {

			private static final long serialVersionUID = 1L;
			private static ModeloAsistentes instancia;
			private ArrayList<Asistente> asistente;
			private String[] encabezados = { "Nombre", "Apellido", "Teléfono",
					"Dirección", "Cédula", "Especialidad", "CodigodeEmpleado"};

			public static synchronized ModeloAsistentes getInstancia()
					throws ClassNotFoundException, SQLException, JDOMException, IOException {
				return instancia == null ? instancia = new ModeloAsistentes()
						: instancia;
			}

			public ModeloAsistentes() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		Conexion.getInstancia().setFactoria(new FactoriaGestionAsistente());
	}

			@Override
			public int getColumnCount() {
				return encabezados.length;
			}

			@Override
			public int getRowCount() {
				return asistente.size();
			}
			
			public void eliminarPaciente(int posicion) {
				
			}
			
			public void agregar(Object  persona, String tabla)
					throws ClassNotFoundException, SQLException, JDOMException,
					IOException {
				Conexion.getInstancia().agregar(persona, tabla);
			}

			@Override
			public Object getValueAt(int fila, int columna) {
				Asistente asistenteActual = asistente.get(fila);
				String retorno = null;

				switch (columna) {
				case 0:
					retorno = asistenteActual.getNombre();
					break;

				case 1:
					retorno = asistenteActual.getApellido();
					break;

				case 2:
					retorno = asistenteActual.getTelefono();
					break;

				case 3:
					retorno = asistenteActual.getDireccion();
					break;

				case 4:
					retorno = asistenteActual.getCedula();
					break;

				case 5:
					retorno = asistenteActual.getCodigoEmpleado();
					break;

			
				
					
				}
				return retorno;
			}

			@Override
			public void setValueAt(Object objeto, int fila, int columna) {
				Asistente asistenteActual = asistente.get(fila);
				String valor = (String)objeto;
				switch (columna) {
				case 0:
					asistenteActual.setNombre(valor);
				break;

				case 1:
					asistenteActual.setApellido(valor);
				break;

				case 2:
					asistenteActual.setTelefono(valor);
				break;
				
				case 3:
					asistenteActual.setDireccion(valor);
				break;
				
				case 4:
					asistenteActual.setCedula(valor);
				break;
				
				case 5:
					asistenteActual.setCodigoEmpleado(valor);
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





