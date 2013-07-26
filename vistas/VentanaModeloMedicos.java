package com.efrain.gestorpacientes.vistas;

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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Medico;
import com.efrain.gestorpacientes.modelos.ModeloMedicos;
import com.efrain.gestorpacientes.modelos.ModeloTelefonos;

public class VentanaModeloMedicos extends Ventana {

	private JTextField txtCod;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDi;
	private JTextField txtTelefono;
	private JTextField txtCe;
	private JTable tblMedicos;
	private JTable tblTelefonos;
	private JComboBox<String> comboEspecialidades;
	private JScrollPane scrollTelefonos;
	private ModeloMedicos modeloM;
	private ModeloTelefonos modeloT;
	private DefaultTableModel modeloTelefonos;

	private static final long serialVersionUID = 1L;

	public VentanaModeloMedicos() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Medicos";
		this.anchura = 715;
		this.altura = 550;
		this.modeloM = ModeloMedicos.getInstancia();
		this.modeloT = ModeloTelefonos.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Médicos");
		lblTitulo.setFont(new Font("Monotype Coursiva",
				Font.BOLD + Font.ITALIC, 16));
		lblTitulo.setPreferredSize(new Dimension(280, 50));
		JLabel lblCod = new JLabel("Código de empleado:");
		JLabel lblNombre = new JLabel("    Nombre:");
		JLabel lblApellido = new JLabel("Apellido:");
		JLabel lblDi = new JLabel("    Dirección:");
		JLabel lblCe = new JLabel("Cédula:");
		JLabel lblTel = new JLabel("Teléfono:");
		JLabel lblEsp = new JLabel("    Especialidad:");

		tblMedicos = new JTable(modeloM);
		tblMedicos.setPreferredScrollableViewportSize(new Dimension(595, 350));
		tblMedicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Medico medicoActual = modeloM.getMedicos().get(
						tblMedicos.getSelectedRow());
				txtNombre.setText(medicoActual.getNombre());
				txtApellido.setText(medicoActual.getApellido());
				comboEspecialidades.setSelectedItem(medicoActual
						.getEspecialidad());
			}
		});
		tblTelefonos = new JTable(modeloT);
		tblTelefonos.setPreferredScrollableViewportSize(new Dimension(100, 50));

		txtCod = new JTextField(10);
		txtNombre = new JTextField(10);
		txtApellido = new JTextField(10);
		txtDi = new JTextField(10);
		txtCe = new JTextField(10);
		txtTelefono = new JTextField(10);

		comboEspecialidades = modeloM.getEspecialidades();

		JButton btnAgregarM = new JButton("Agregar Medico");
		btnAgregarM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtCod.getText())
						|| !"".equals(txtNombre.getText())
						|| !"".equals(txtApellido.getText())
						|| !"".equals(txtDi.getText())
						|| !"".equals(txtCe.getText())) {
					String nombre = txtNombre.getText();
					String apellido = txtApellido.getText();
					// ArrrayList<String> telefonos =
					String direccion = txtDi.getText();
					String cedula = txtCe.getText();
					String especialidad = comboEspecialidades
							.getItemAt(comboEspecialidades.getSelectedIndex());

					int resp = JOptionPane
							.showConfirmDialog(
									null,
									"ATENCION:Está a punto de registrar un"
											+ " usuario\n que no tiene correo, es recomendable que tenga\n correo para posteriormente"
											+ " enviarle su contraseña\n en caso de que la olvide. Agregar de todas formas?");
					/*
					 * if (resp == JOptionPane.YES_OPTION) {
					 * modeloUsuarios.agregar(objeto); } else {
					 * modeloUsuarios.agregar(objeto); }
					 */
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}

			}
		});

		JButton btnEliminarM = new JButton("Eliminar Medico");
		btnEliminarM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tblMedicos.getSelectedRow() != -1) {
					try {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"Está seguro que desea eliminar este usuario?\n\nNOTA: Se eliminará permanentemente.");
						if (respuesta == JOptionPane.YES_OPTION) {
							modeloM.eliminar(tblMedicos.getSelectedRow());
						}
					} catch (ClassNotFoundException | SQLException
							| JDOMException | IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar un usuario.");
				}
			}
		});
		JButton btnCambios = new JButton("Guardar Cambios");
		btnCambios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tblMedicos.clearSelection();
			}
		});
		JButton btnAgregarT = new JButton("Agregar Telefono");
		btnAgregarT.setEnabled(false);
		JButton btnEliminarT = new JButton("Eliminar Telefono");
		btnEliminarT.setEnabled(false);

		JPanel pnlGrid = new JPanel(new GridLayout(3, 4));
		pnlGrid.add(lblCod);
		pnlGrid.add(txtCod);
		pnlGrid.add(lblNombre);
		pnlGrid.add(txtNombre);
		pnlGrid.add(lblApellido);
		pnlGrid.add(txtApellido);
		pnlGrid.add(lblDi);
		pnlGrid.add(txtDi);
		pnlGrid.add(lblCe);
		pnlGrid.add(txtCe);
		pnlGrid.add(lblEsp);
		pnlGrid.add(comboEspecialidades);

		JPanel pnlGridTel = new JPanel(new GridLayout(2, 1));
		pnlGridTel.add(btnAgregarT);
		pnlGridTel.add(btnEliminarT);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		scrollTelefonos = new JScrollPane(tblTelefonos);
		scrollTelefonos.setWheelScrollingEnabled(false);
		panel.add(scrollTelefonos);
		panel.add(pnlGridTel);
		panel.add(btnAgregarM);
		panel.add(btnEliminarM);
		panel.add(btnCambios);
		JButton btnPrueba = new JButton("S");
		btnPrueba.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, tblMedicos.getSelectedRow());
			}
		});
		panel.add(btnPrueba);
		panel.add(lblTel);
		panel.add(txtTelefono);
		panel.add(new JScrollPane(tblMedicos));
		return panel;
	}

	public static void main(String[] args) {
		try {
			new VentanaModeloMedicos().setVisible(true);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException
				| SQLException | JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

}
