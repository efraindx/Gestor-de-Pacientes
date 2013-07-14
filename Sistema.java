package com.efrain.gestorpacientes;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UnsupportedLookAndFeelException;

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
	
		
	}
}
