package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

public class VentanaAsistente extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaAsistente instancia;

	public static synchronized VentanaAsistente getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		return instancia == null ? instancia = new VentanaAsistente()
				: instancia;
	}

	public VentanaAsistente() {
		this.titulo = "Ventana Asistente";
		this.anchura = 550;
		this.altura = 550;
	}

	@Override
	public JPanel getContenido() {
		this.panel = new JPanel(new FlowLayout());
		JLabel lblTitulo = new JLabel();
		panel.add(lblTitulo);
		return panel;
	}

}
