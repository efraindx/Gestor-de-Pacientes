package edu.itla.gestorpacientes.vistas;

import java.awt.FlowLayout;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

public class VentanaAdministrador extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaAdministrador instancia;

	public static synchronized VentanaAdministrador getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		return (VentanaAdministrador) (instancia == null ? instancia = new VentanaAdministrador()
				: instancia);
	}

	public VentanaAdministrador() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
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
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		new VentanaAdministrador().setVisible(true);
	}

}
