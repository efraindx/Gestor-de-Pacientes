package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

public class VentanaAsistente extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaAsistente instancia;
	
	public static synchronized VentanaAsistente getInstancia() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		return instancia == null ? instancia = new VentanaAsistente() : instancia;
	}
	
	public VentanaAsistente() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.titulo = "Ventana Asistente";
		this.anchura = 550;
		this.altura = 550;
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() {
		panel = new JPanel(new FlowLayout());
		JLabel lblAsistente = new JLabel("Asistente");
		panel.add(lblAsistente);
		return panel;
	}

	@Override
	public boolean esSalirAlCerrar() {
		return false;
	}
}
