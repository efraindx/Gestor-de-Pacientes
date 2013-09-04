package edu.itla.gestorpacientes;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.algoritmos.AlgoritmoBusquedaPersonas;
import edu.itla.gestorpacientes.persistencia.Conexion;
import edu.itla.gestorpacientes.vistas.VentanaLogin;

public class Sistema {
	public static void main(String[] args) {

		try {
			new VentanaLogin(new AlgoritmoBusquedaPersonas()).setVisible(true);
			Conexion.getInstancia().comprobar();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Ha ocurrido un problema con el archivo config.xml,\n error: "
							+ e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(null,
					"Ha ocurrido un problema con el archivo config.xml,\n error: "
							+ e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}