package com.efrain.gestorpacientes.vistas;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

public class VentanaMedico extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaMedico instancia;
	
	public static synchronized VentanaMedico getInstancia() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		return instancia == null ? instancia = new VentanaMedico() : instancia;
	}

	public VentanaMedico() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.anchura = 400;
		this.altura = 400;
		this.titulo = "Medico";
		prepararVentana(titulo, anchura, altura, null);
	}
	
	@Override
	public JPanel getContenido() {
		JPanel pnlPrincipal = new JPanel();
		JLabel lblTitulo = new JLabel("Ventana Medico");
		pnlPrincipal.add(lblTitulo);
		return pnlPrincipal;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		new VentanaMedico().setVisible(true);
	}
}
