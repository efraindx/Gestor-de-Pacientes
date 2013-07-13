package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

public class VentanaAdministrador extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaAdministrador instancia;

	public static synchronized VentanaAdministrador getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		return (VentanaAdministrador) (instancia == null ? instancia = new VentanaAdministrador()
				: instancia);
	}

	public VentanaAdministrador() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		this.titulo = "Adminitrador";
		prepararVentana(titulo, 500, 500, null);
	}

	@Override
	public JPanel getContenido() {
		JPanel pnlPrincipal = new JPanel();
		pnlPrincipal.add(new JLabel("Holaaa"));
		pnlPrincipal.setLayout(new FlowLayout());
		return pnlPrincipal;
	}

	@Override
	public boolean esSalirAlCerrar() {
		return false;
	}

}
