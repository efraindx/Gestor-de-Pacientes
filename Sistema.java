package com.efrain.gestorpacientes;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.vistas.VentanaLogin;
import com.efrain.gestorpacientes.algoritmos.AlgoritmoBusquedaPersona;

public class Sistema {
	public static void main(String[] args) {
		try {
			VentanaLogin.getInstancia(new AlgoritmoBusquedaPersona()).setVisible(true);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
