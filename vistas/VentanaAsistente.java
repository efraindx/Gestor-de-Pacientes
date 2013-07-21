package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

public class VentanaAsistente extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaAsistente instancia;

	public static synchronized VentanaAsistente getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		try {
			return instancia == null ? instancia = new VentanaAsistente()
					: instancia;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instancia;
	}
	
	@Override
	public boolean esSalirAlCerrar() {
		return false;
	}

	public VentanaAsistente() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		this.titulo = "Ventana Asistente";
		this.anchura = 550;
		this.altura = 550;
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() {
		this.panel = new JPanel(new FlowLayout());
		JLabel lblTitulo = new JLabel();
		panel.add(lblTitulo);
		return panel;
	}
	
	public static void main(String[] args) {
		try {
			new VentanaAsistente().setVisible(true);
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
	}

}
