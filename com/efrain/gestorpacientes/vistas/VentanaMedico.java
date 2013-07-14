package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

public class VentanaMedico extends Ventana {

	public VentanaMedico() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.anchura = 400;
		this.altura = 400;
		this.titulo = "Medico";
		prepararVentana(titulo, anchura, altura, null);
	}
	
	@Override
	public JPanel getContenido() {
		JPanel pnlPrincipal = new JPanel(new FlowLayout());
		JLabel lblTitulo = new JLabel("Ventana Medico");
		pnlPrincipal.add(lblTitulo);
		return pnlPrincipal;
	}
	
	public static void main(String[] args) {
		try {
			new VentanaMedico().setVisible(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
