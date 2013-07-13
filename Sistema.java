package com.efrain.gestorpacientes;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.vistas.VentanaLogin;
import com.efrain.gestorpacientes.algoritmos.AlgoritmoBusquedaPersona;

public class Sistema {

	public static void main(String[] args) throws SQLException, JDOMException,
			IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		
		 VentanaLogin vl = new VentanaLogin(new AlgoritmoBusquedaPersona());
		 vl.setVisible(true);
	}
}
