package edu.itla.gestorpacientes.utilidades;


import javax.swing.JDialog;
import javax.swing.JFrame;

public class Dialogo {
	
	private static JDialog instancia;
	
	public static synchronized JDialog getInstancia(JFrame ventana){
				if(instancia == null){
					instancia = new JDialog(ventana, true);
				}
				return instancia;
	}
}
