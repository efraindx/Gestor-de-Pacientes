package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Especialidad;
import edu.itla.gestorpacientes.entidades.Medico;
import edu.itla.gestorpacientes.modelos.ModeloBusquedaMedicos;

public class VentanaListadoMedicos extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<Especialidad> cmbEspecialidades;
	private ModeloBusquedaMedicos modelo;
	private static VentanaListadoMedicos instancia;

	public static synchronized VentanaListadoMedicos getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaListadoMedicos()
				: instancia;
	}

	private VentanaListadoMedicos() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "";
		this.anchura = 650;
		this.altura = 400;
		this.icono = "/edu/itla/gestorpacientes/imágenes/med.png";
		modelo = ModeloBusquedaMedicos.getInstancia();
		modelo.setMedicos(new ArrayList<Medico>());
		cmbEspecialidades = modelo.getEspecialidades();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		for (int i = 0; i < cmbEspecialidades.getItemCount(); i++) {
			if (cmbEspecialidades.getItemAt(i).getNombre().equals("Ninguna")) {
				cmbEspecialidades.removeItemAt(i);
			}
		}

		JLabel lblPrimero = new JLabel();
		lblPrimero.setPreferredSize(new Dimension(200, 50));
		JLabel lblTitulo = new JLabel("Listado de Médicos", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/med.png")), 0);
		lblTitulo.setPreferredSize(new Dimension(220, 50));
		lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.ITALIC
				+ Font.BOLD, 14));
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(150, 50));
		JLabel lblEsp = new JLabel("Especialidad:");
		lblEsp.setFont(new Font("Arial Rounded MT Bold", Font.ITALIC, 12));

		JTable tblMedicos = new JTable(modelo);
		tblMedicos.setPreferredScrollableViewportSize(new Dimension(550, 240));

		JButton btnMostrar = new JButton("Mostrar Médicos");
		btnMostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				modelo.setMedicos(new ArrayList<Medico>());
				try {
					ArrayList<Medico> medicos = modelo
							.getMedicos(cmbEspecialidades.getItemAt(
									cmbEspecialidades.getSelectedIndex())
									.getId());
					if (medicos.size() != 0) {
						modelo.setMedicos(medicos);
					} else {
						JOptionPane.showMessageDialog(
								VentanaListadoMedicos.this,
								"No hay médicos de esta especialidad.");
						cmbEspecialidades.setSelectedIndex(0);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(lblPrimero);
		panel.add(lblTitulo);
		panel.add(lblBlanco);
		panel.add(lblEsp);
		panel.add(cmbEspecialidades);
		panel.add(btnMostrar);
		panel.add(new JScrollPane(tblMedicos));
		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}
}
