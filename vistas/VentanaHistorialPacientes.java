package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.modelos.ModeloPacientes;
import edu.itla.gestorpacientes.utilidades.AjustadorFotos;

public class VentanaHistorialPacientes extends Ventana {

	private static final long serialVersionUID = 1L;
	private JTextArea txtFechas;
	private JTextArea txtCausas;
	private JTextArea txtObs;
	private JTextArea txtRecetas;
	private JTextArea txtResultados;
	private Paciente paciente;
	private ModeloPacientes modelo;

	public VentanaHistorialPacientes(Paciente paciente)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		this.titulo = "Historiales";
		this.anchura = 800;
		this.altura = 450;
		this.paciente = paciente;
		this.icono = "/edu/itla/gestorpacientes/imágenes/his.png";
		modelo = ModeloPacientes.getInstancia();
		UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
		SwingUtilities.updateComponentTreeUI(this);
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Historial de Pacientes", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/his.png")), 0);
		lblTitulo.setFont(new Font("Franklin Gothic Heavy", Font.ITALIC
				+ Font.BOLD, 16));
		JLabel lblPaciente = new JLabel("Paciente: ");
		lblPaciente.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		JLabel lblNPaciente = new JLabel(paciente.getNombre() + " "
				+ paciente.getApellido());
		lblNPaciente.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(150, 50));
		JLabel lblBlanco2 = new JLabel();
		lblBlanco2.setPreferredSize(new Dimension(50, 50));
		JLabel lblFe = new JLabel("Fechas de Visitas: ");
		lblFe.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		JLabel lblCausas = new JLabel("   Causas de las visistas: ");
		lblCausas.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		JLabel lblObs = new JLabel("Observaciones:");
		lblObs.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		JLabel lblRecetas = new JLabel("   Recetas: ");
		lblRecetas.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		JLabel lblRes = new JLabel("Res. de Pruebas de Lab.: ");
		lblRes.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		JLabel lblFoto = new JLabel();
		ImageIcon foto = null;
		if (paciente.getFoto().substring(0, 1).equals("/")) {
			foto = AjustadorFotos.ajustarImagen(Toolkit.getDefaultToolkit()
					.getImage(getClass().getResource(paciente.getFoto())));
		} else {
			foto = AjustadorFotos.ajustarImagen(Toolkit.getDefaultToolkit()
					.getImage(paciente.getFoto()));
		}
		lblFoto.setIcon(foto);

		txtFechas = new JTextArea(5, 10);
		txtFechas.setEditable(false);
		ArrayList<String> datos = modelo.getDatos("fecha", "citas",
				paciente.getId());
		StringBuilder constructor = getBuilder();
		for (String d : datos) {
			constructor.append("-" + d + "\n\n");
		}
		txtFechas.setText(constructor.toString());

		txtCausas = new JTextArea(5, 15);
		txtCausas.setEditable(false);
		datos = modelo.getDatos("causa", "citas", paciente.getId());
		constructor = getBuilder();
		for (String d : datos) {
			constructor.append("-" + d + "\n\n");
		}
		txtCausas.setText(constructor.toString());

		txtObs = new JTextArea(5, 14);
		txtObs.setEditable(false);
		datos = modelo.getDatos("observaciones", "citas", paciente.getId());
		constructor = getBuilder();
		for (String d : datos) {
			constructor.append("-" + d + "\n\n");
		}
		txtObs.setText(constructor.toString());

		txtRecetas = new JTextArea(5, 20);
		txtRecetas.setEditable(false);
		datos = modelo.getDatos("medicamentos", "recetas", paciente.getId());
		constructor = getBuilder();
		for (String d : datos) {
			constructor.append("-" + d + "\n\n");
		}
		txtRecetas.setText(constructor.toString());

		txtResultados = new JTextArea(5, 20);
		txtResultados.setEditable(false);
		for (String d : datos) {
			constructor.append("-" + d + "\n\n");
		}
		txtResultados.setText(constructor.toString());

		JPanel pnlGrid = new JPanel(new GridLayout(2, 4));
		pnlGrid.setPreferredSize(new Dimension(750, 200));
		pnlGrid.add(lblFe);
		pnlGrid.add(new JScrollPane(txtFechas));
		pnlGrid.add(lblCausas);
		pnlGrid.add(new JScrollPane(txtCausas));
		pnlGrid.add(lblObs);
		pnlGrid.add(new JScrollPane(txtObs));
		pnlGrid.add(lblRecetas);
		pnlGrid.add(new JScrollPane(txtRecetas));

		panel.add(lblTitulo);
		panel.add(lblBlanco);
		panel.add(lblPaciente);
		panel.add(lblNPaciente);
		panel.add(lblFoto);
		panel.add(pnlGrid);
		panel.add(lblRes);
		panel.add(new JScrollPane(txtResultados));
		return panel;
	}

	@Override
	public boolean esSalirAlCerrar() {
		return false;
	}

	public StringBuilder getBuilder() {
		return new StringBuilder();
	}
}
