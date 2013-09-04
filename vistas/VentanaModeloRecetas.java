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
import java.util.ArrayList;

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

import net.sf.jasperreports.engine.JRException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.documentación.Reportador;
import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.Padecimiento;
import edu.itla.gestorpacientes.entidades.Receta;
import edu.itla.gestorpacientes.modelos.ModeloRecetas;

public class VentanaModeloRecetas extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<Paciente> cmbPacientes;
	private JComboBox<Padecimiento> cmbPadecimientos;
	private JTextArea txtMedicamentos;
	private ModeloRecetas modelo;
	private JTable tblRecetas;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnCambios;
	private JButton btnImprimir;
	private Receta recetaActual;
	private ArrayList<Receta> recetas;

	public VentanaModeloRecetas() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Recetas";
		this.anchura = 570;
		this.altura = 515;
		this.icono = "/edu/itla/gestorpacientes/imágenes/receta.png";
		modelo = ModeloRecetas.getInstancia();
		recetas = modelo.getRecetas();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Recetas",
				new ImageIcon(getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/receta.png")), 0);
		lblTitulo.setFont(new Font("Segoe UI Semibold",
				Font.ITALIC + Font.BOLD, 18));
		lblTitulo.setPreferredSize(new Dimension(300, 100));
		JLabel lblPaciente = new JLabel("  Paciente:");
		lblPaciente.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		JLabel lblPadecimiento = new JLabel("    Padecimiento:");
		lblPadecimiento.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		JLabel lblMedicamentos = new JLabel("Medicamentos:");
		lblMedicamentos.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		JLabel lblBlanco = new JLabel();
		JLabel lblBlanco2 = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(57, 50));
		lblBlanco2.setPreferredSize(new Dimension(120, 50));

		tblRecetas = new JTable(ModeloRecetas.getInstancia());
		tblRecetas.setPreferredScrollableViewportSize(new Dimension(500, 155));
		tblRecetas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				recetaActual = modelo.getRecetas().get(
						tblRecetas.getSelectedRow());
				cmbPacientes.setSelectedItem(getItemPaciente(recetaActual
						.getPaciente().getId()));
				cmbPadecimientos
						.setSelectedItem(getItemPadecimiento(recetaActual
								.getPadecimiento().getId()));
				txtMedicamentos.setText(recetaActual.getMedicamentos());
				btnCambios.setEnabled(true);
				btnEliminar.setEnabled(true);
				btnAgregar.setEnabled(false);
				btnImprimir.setEnabled(true);

			}
		});

		btnAgregar = new JButton("Agregar Receta");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(txtMedicamentos.getText())
						&& cmbPacientes.getSelectedIndex() != 0
						&& cmbPadecimientos.getSelectedIndex() != 0) {
					Paciente paciente = cmbPacientes.getItemAt(cmbPacientes
							.getSelectedIndex());
					Padecimiento padecimiento = cmbPadecimientos
							.getItemAt(cmbPadecimientos.getSelectedIndex());
					String medicamentos = txtMedicamentos.getText();

					Receta receta = new Receta(paciente, padecimiento,
							medicamentos);
						try {
							modelo.agregar(receta);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						cmbPacientes.setSelectedIndex(0);
						cmbPadecimientos.setSelectedIndex(0);
						txtMedicamentos.setText("");

				} else {
					JOptionPane.showMessageDialog(null,
							"Todos los campos son obligatorios.");
				}
			}
		});

		btnEliminar = new JButton("Eliminar Receta");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblRecetas.getSelectedRow() != -1) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Está seguro que desea eliminar esta receta?\n\nNOTA: Se eliminará permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblRecetas.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar una receta.");
				}
				btnCambios.setEnabled(false);
				btnAgregar.setEnabled(true);
				btnEliminar.setEnabled(false);
				tblRecetas.clearSelection();
				cmbPacientes.setSelectedIndex(0);
				cmbPadecimientos.setSelectedIndex(0);
				btnImprimir.setEnabled(false);
				txtMedicamentos.setText("");
			}
		});

		btnCambios = new JButton("Guardar Cambios");
		btnCambios.setEnabled(false);
		btnCambios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				recetaActual = modelo.getRecetas().get(
						tblRecetas.getSelectedRow());
				if (!"".equals(txtMedicamentos.getText())
						&& cmbPacientes.getSelectedIndex() != 0
						&& cmbPadecimientos.getSelectedIndex() != 0) {
					try {
						int id = recetaActual.getId();
						int fila = tblRecetas.getSelectedRow();
						Paciente paciente = cmbPacientes.getItemAt(cmbPacientes
								.getSelectedIndex());
						modelo.modificar(id, 1, fila, paciente);
						Padecimiento padecimiento = cmbPadecimientos
								.getItemAt(cmbPadecimientos.getSelectedIndex());
						modelo.modificar(id, 2, fila, padecimiento);
						String medicamentos = txtMedicamentos.getText();
						modelo.modificar(id, 3, fila, medicamentos);
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
				tblRecetas.clearSelection();
				cmbPacientes.setSelectedIndex(0);
				cmbPadecimientos.setSelectedIndex(0);
				btnImprimir.setEnabled(false);
				txtMedicamentos.setText("");
			}
		});

		btnImprimir = new JButton("Imprimir Receta");
		btnImprimir.setEnabled(false);
		btnImprimir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Reportador re = Reportador.getInstancia();
					recetas = modelo.getRecetas();
					Receta recetaActual = recetas.get(tblRecetas
							.getSelectedRow());
					re.generarReceta(recetaActual.getId());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (JDOMException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JRException e1) {
					e1.printStackTrace();
				}
				btnImprimir.setEnabled(false);
				btnCambios.setEnabled(false);
				btnAgregar.setEnabled(true);
				btnEliminar.setEnabled(false);
				tblRecetas.clearSelection();
				cmbPacientes.setSelectedIndex(0);
				cmbPadecimientos.setSelectedIndex(0);
				txtMedicamentos.setText("");
				tblRecetas.clearSelection();
			}
		});

		cmbPacientes = modelo.getPacientes();
		cmbPadecimientos = modelo.getPadecimientos();

		txtMedicamentos = new JTextArea(5, 20);

		JPanel pnlGrid = new JPanel(new GridLayout(1, 4));
		pnlGrid.setPreferredSize(new Dimension(477, 25));
		pnlGrid.add(lblPaciente);
		pnlGrid.add(cmbPacientes);
		pnlGrid.add(lblPadecimiento);
		pnlGrid.add(cmbPadecimientos);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		panel.add(lblBlanco);
		panel.add(lblMedicamentos);
		panel.add(new JScrollPane(txtMedicamentos));
		panel.add(lblBlanco2);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(btnCambios);
		panel.add(btnImprimir);
		panel.add(new JScrollPane(tblRecetas));

		return panel;
	}

	public Paciente getItemPaciente(int id) {
		Paciente retorno = null;
		for (int i = 0; i < cmbPacientes.getItemCount(); i++) {
			if (cmbPacientes.getItemAt(i).getId() == id) {
				retorno = cmbPacientes.getItemAt(i);
			}
		}
		return retorno;
	}

	public Padecimiento getItemPadecimiento(int id) {
		Padecimiento retorno = null;
		for (int i = 0; i < cmbPadecimientos.getItemCount(); i++) {
			if (cmbPadecimientos.getItemAt(i).getId() == id) {
				retorno = cmbPadecimientos.getItemAt(i);
			}
		}
		return retorno;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}
}
