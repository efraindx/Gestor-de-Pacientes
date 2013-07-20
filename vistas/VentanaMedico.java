package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

public class VentanaMedico extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaMedico instancia;
	 
	public static synchronized VentanaMedico getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
	    return instancia == null ? instancia = new VentanaMedico() : instancia;
	}
	
	public VentanaMedico() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
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

}
