package com.efrain.gestorpacientes;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.vistas.VentanaLogin;
import com.efrain.gestorpacientes.algoritmos.AlgoritmoBusquedaUsuarios;

public class Sistema {
	public static void main(String[] args) {
		try {
			VentanaLogin.getInstancia(new AlgoritmoBusquedaUsuarios())
					.setVisible(true);

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
		}
	}
}