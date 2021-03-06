package edu.itla.gestorpacientes.vistas;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

public abstract class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	protected String titulo;
	protected int anchura;
	protected int altura;
	protected String icono;
	protected JPanel panel;
	
	public final void prepararVentana(String titulo, int anchura, int altura,
			String icono) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {

		this.titulo = titulo;
		this.anchura = anchura;
		this.altura = altura;
		this.icono = icono;

		if (icono != null) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(
					getClass().getResource(icono)));
		}

		setLayout(new FlowLayout());
		setSize(anchura, altura);
		setTitle(titulo);

		if (esSalirAlCerrar()) {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		if (!esDisponibleCambiarTamaņo()) {
			setResizable(false);
		}

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(getContenido());
		setLocationRelativeTo(null);
	}

	public abstract JPanel getContenido() throws ClassNotFoundException,
			SQLException, JDOMException, IOException;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean esSalirAlCerrar() {
		return true;
	}

	public boolean esDisponibleCambiarTamaņo() {
		return true;
	}
}
