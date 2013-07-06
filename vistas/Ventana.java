package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public abstract class Ventana extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected String titulo;
	protected int anchura;
	protected int altura;
	protected String icono;

	public final void prepararVentana(String titulo, int anchura, int altura, String icono) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		this.titulo = titulo;
		this.anchura = anchura;
		this.altura = altura;
		this.icono = icono;
		
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		SwingUtilities.updateComponentTreeUI(this);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(icono));
		setLayout(new FlowLayout());
		setSize(anchura, altura);
		setTitle(titulo);
		
		if(esSalirAlCerrar()) {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
		if(!esDisponibleCambiarTamaño()) {
			setResizable(false);
		}
		
		add(getContenido().getContentPane());
		setLocationRelativeTo(null);
	}
	
	public abstract JFrame getContenido();

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public boolean esSalirAlCerrar() {
		return true;
	}
	
	public boolean esDisponibleCambiarTamaño() {
		return true;
	}

	
}
