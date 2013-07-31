package com.efrain.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

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

import com.efrain.gestorpacientes.entidades.Receta;
import com.efrain.gestorpacientes.modelos.ModeloRecetas;

public class VentanaModeloRecetas extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> cmbPacientes;
	private JComboBox<String> cmbPadecimientos;
	private JTextArea txtMedicamentos;
	private ModeloRecetas modelo;
	private JTable tblRecetas;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnCambios;
	private Receta recetaActual;

	public VentanaModeloRecetas() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "";
		this.anchura = 505;
		this.altura = 500;
		modelo = ModeloRecetas.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Recetas");
		lblTitulo.setPreferredSize(new Dimension(355, 100));
		JLabel lblPaciente = new JLabel("Paciente:");
		JLabel lblPadecimiento = new JLabel("Padecimiento:");
		JLabel lblMedicamentos = new JLabel("Medicamentos:");
		JLabel lblBlanco = new JLabel();
		JLabel lblBlanco2 = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(57, 50));
		lblBlanco2.setPreferredSize(new Dimension(160, 50));

		tblRecetas = new JTable(ModeloRecetas.getInstancia());
		tblRecetas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				recetaActual = modelo.getRecetas().get(
						tblRecetas.getSelectedRow());
				cmbPacientes.setSelectedIndex(recetaActual.getIdPaciente());
				cmbPadecimientos.setSelectedIndex(recetaActual.getIdPadecimiento());
				txtMedicamentos.setText(recetaActual.getMedicamentos());
				btnCambios.setEnabled(true);
				btnEliminar.setEnabled(true);
				btnAgregar.setEnabled(false);
			}
		});

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(txtMedicamentos.getText())
						&& cmbPacientes.getSelectedIndex() != 0
						&& cmbPadecimientos.getSelectedIndex() != 0) {

					int paciente = cmbPacientes.getSelectedIndex();
					int padecimiento = cmbPadecimientos.getSelectedIndex();
					String medicamentos = txtMedicamentos.getText();

					Receta receta = new Receta(paciente, padecimiento,
							medicamentos );
					try {
						modelo.agregar(receta);
						cmbPacientes.setSelectedIndex(0);
						cmbPadecimientos.setSelectedIndex(0);
						txtMedicamentos.setText("");
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

		btnEliminar = new JButton("Eliminar");
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
						
						int paciente = cmbPacientes.getSelectedIndex();
						modelo.modificar(id, 1, fila, paciente);
						int padecimiento = cmbPadecimientos.getSelectedIndex();
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
				txtMedicamentos.setText("");
			}
		});

		cmbPacientes = modelo.getPacientes();
		cmbPadecimientos = modelo.getPadecimientos();

		txtMedicamentos = new JTextArea(5, 20);

		JPanel pnlGrid = new JPanel(new GridLayout(1, 6));
		pnlGrid.add(lblPaciente);
		pnlGrid.add(cmbPacientes);
		pnlGrid.add(new JLabel());
		pnlGrid.add(new JLabel());
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
		panel.add(new JScrollPane(tblRecetas));

		return panel;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		new VentanaModeloRecetas().setVisible(true);
	}

}
