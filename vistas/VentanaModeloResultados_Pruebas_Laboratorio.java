package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.PruebaLaboratorio;
import edu.itla.gestorpacientes.entidades.Resultado_Prueba_Laboratorio;
import edu.itla.gestorpacientes.modelos.ModeloPruebas_Laboratorio;
import edu.itla.gestorpacientes.modelos.ModeloResultados_Prueba_Laboratorio;

public class VentanaModeloResultados_Pruebas_Laboratorio extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<Paciente> cmbPacientes;
	private JComboBox<PruebaLaboratorio> cmbPruebas_Laboratorio;
	private JTextArea txtResultado;
	private ModeloResultados_Prueba_Laboratorio modelo;
	private ModeloPruebas_Laboratorio modeloP;
	private JTable tblResultados;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnCambios;
	private Resultado_Prueba_Laboratorio resultadoActual;

	public VentanaModeloResultados_Pruebas_Laboratorio()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		this.titulo = "Resultados de Pruebas de Laboratorio";
		this.anchura = 560;
		this.altura = 520;
		this.icono = "/edu/itla/gestorpacientes/imágenes/result.png";
		modelo = ModeloResultados_Prueba_Laboratorio.getInstancia();
		modeloP = ModeloPruebas_Laboratorio.getInstancia();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel(
				"Mantenimiento de Resultados de Prueba de Laboratorio",
				new ImageIcon(getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/result.png")), 0);
		lblTitulo.setPreferredSize(new Dimension(500, 100));
		lblTitulo.setFont(new Font("Segoe UI Semibold",
				Font.BOLD + Font.ITALIC, 15));
		JLabel lblPrueba = new JLabel("Prueba de Laboratorio:");
		lblPrueba.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 12));
		JLabel lblPaciente = new JLabel("                Paciente:");
		lblPaciente.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 12));
		JLabel lblResultado = new JLabel("Resultados:");
		lblResultado.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 12));
		JLabel lblBlanco = new JLabel();
		JLabel lblBlanco2 = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(57, 50));
		lblBlanco2.setPreferredSize(new Dimension(160, 50));

		tblResultados = new JTable(
				ModeloResultados_Prueba_Laboratorio.getInstancia());
		tblResultados
				.setPreferredScrollableViewportSize(new Dimension(470, 185));
		tblResultados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resultadoActual = modelo.getResultados().get(
						tblResultados.getSelectedRow());
				cmbPacientes.setSelectedItem(resultadoActual.getPaciente());
				cmbPruebas_Laboratorio.setSelectedItem(resultadoActual
						.getPrueba());
				txtResultado.setText(resultadoActual.getResultado());
				btnCambios.setEnabled(true);
				btnEliminar.setEnabled(true);
				btnAgregar.setEnabled(false);
			}
		});

		btnAgregar = new JButton("Agregar Resultado de Prueba");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(txtResultado.getText())) {

					Paciente paciente = cmbPacientes.getItemAt(cmbPacientes.getSelectedIndex());
					PruebaLaboratorio prueba = cmbPruebas_Laboratorio.getItemAt(cmbPruebas_Laboratorio.getSelectedIndex());
					String resultado = txtResultado.getText();

					Resultado_Prueba_Laboratorio result = new Resultado_Prueba_Laboratorio(
							paciente.getId(), prueba.getId(), resultado);
					try {
						modelo.agregar(result);
						cmbPacientes.setSelectedIndex(0);
						cmbPruebas_Laboratorio.setSelectedIndex(0);
						txtResultado.setText("");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JDOMException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Todos los campos son obligatorios.");
				}
			}
		});

		btnEliminar = new JButton("Eliminar Resultado de Prueba");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblResultados.getSelectedRow() != -1) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Está seguro que desea eliminar este resultado de prueba?\n\nNOTA: Se eliminará permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblResultados.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar una receta.");
				}
			}
		});

		btnCambios = new JButton("Guardar Cambios");
		btnCambios.setEnabled(false);
		btnCambios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resultadoActual = modelo.getResultados().get(
						tblResultados.getSelectedRow());
				if (!"".equals(txtResultado.getText())) {
					try {
						int id = resultadoActual.getId();
						int fila = tblResultados.getSelectedRow();

						Paciente paciente = cmbPacientes.getItemAt(cmbPacientes.getSelectedIndex());
						PruebaLaboratorio prueba = cmbPruebas_Laboratorio.getItemAt(cmbPruebas_Laboratorio.getSelectedIndex());
						
						int idPrueba = prueba.getId();
						modelo.modificar(id, 1, fila, idPrueba);
						int idPaciente = paciente.getId();
						modelo.modificar(id, 2, fila, idPaciente);
						String resultado = txtResultado.getText();
						modelo.modificar(id, 3, fila, resultado);

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
				btnCambios.setEnabled(false);
				btnAgregar.setEnabled(true);
				btnEliminar.setEnabled(false);
				tblResultados.clearSelection();
				cmbPacientes.setSelectedIndex(0);
				cmbPruebas_Laboratorio.setSelectedIndex(0);
				txtResultado.setText("");
			}
		});

		cmbPacientes = modelo.getPacientes();
		cmbPruebas_Laboratorio = modeloP.getPruebas_Laboratorio();

		txtResultado = new JTextArea(5, 20);

		JPanel pnlGrid = new JPanel(new GridLayout(1, 4));
		pnlGrid.setPreferredSize(new Dimension(530, 25));
		pnlGrid.add(lblPrueba);
		pnlGrid.add(cmbPruebas_Laboratorio);
		pnlGrid.add(lblPaciente);
		pnlGrid.add(cmbPacientes);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		panel.add(lblBlanco);
		panel.add(lblResultado);
		panel.add(new JScrollPane(txtResultado));
		panel.add(lblBlanco2);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(btnCambios);
		panel.add(new JScrollPane(tblResultados));

		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}
}