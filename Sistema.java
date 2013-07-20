package com.efrain.gestorpacientes;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UnsupportedLookAndFeelException;
<<<<<<< HEAD

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
=======

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.algoritmos.AlgoritmoBusquedaPersona;
import com.efrain.gestorpacientes.vistas.VentanaLogin;

public class Sistema {
	
	private static Sistema instancia;
	private int numero;
	
	private static synchronized Sistema getInstancia() {
		return instancia == null ? instancia = new Sistema() : instancia;
	}
	
	public Sistema() {
		System.out.println("hollla  "+ numero++);
		numero += numero;
	}

	
	public static void main(String[] args) {
	try {	
			new VentanaLogin(new AlgoritmoBusquedaPersona()).setVisible(true);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sistema.getInstancia();
		Sistema st = new Sistema();
	
		
>>>>>>> cbf5ac6365fb12c22636c0fdeb0abcfb001b716f
	}
}
