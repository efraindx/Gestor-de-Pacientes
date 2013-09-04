package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import net.sf.jasperreports.engine.JRException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.documentación.Reportador;

public class VentanaEstadisticas extends Ventana {

	private static final long serialVersionUID = 1L;
	private Reportador reportador;
	private static VentanaEstadisticas instancia;

	public static synchronized VentanaEstadisticas getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaEstadisticas()
				: instancia;
	}

	private VentanaEstadisticas() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Estadísticas";
		this.anchura = 550;
		this.altura = 300;
		this.icono = "/edu/itla/gestorpacientes/imágenes/est.png";
		reportador = Reportador.getInstancia();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Ventana Estadísticas", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/est.png")), 0);
		lblTitulo.setFont(new Font("Imprint MT Shadow",
				Font.ITALIC + Font.BOLD, 20));
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(550, 100));

		JButton btnMedicos = new JButton("Ver Médicos Más Consultados");
		btnMedicos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					reportador.getTopMedicos();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (JRException e1) {
					e1.printStackTrace();
				}

			}
		});

		JButton btnPadecimientos = new JButton(
				"Ver Padecimientos Más Frecuentes");
		btnPadecimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					reportador.getTopPadecimientos();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}
			};
		});

		panel.add(lblTitulo);
		panel.add(lblBlanco);
		panel.add(btnMedicos);
		panel.add(btnPadecimientos);
		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}
}
