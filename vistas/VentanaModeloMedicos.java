package com.efrain.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Medico;
import com.efrain.gestorpacientes.modelos.ModeloMedicos;
import com.efrain.gestorpacientes.modelos.ModeloTelefonos;
import com.efrain.gestorpacientes.utilidades.Validador;

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
	private JButton btnCambios;
	private JButton btnAgregarT;
	private JButton btnEliminarT;
	private JButton btnEliminarM;
	private JButton btnAgregarM;
	private Medico medicoActual;
	private static VentanaModeloMedicos instancia;

	private static final long serialVersionUID = 1L;
	
	public static synchronized VentanaModeloMedicos getInstancia() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloMedicos() : instancia;
	}

	public VentanaModeloMedicos() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Medicos";
		this.anchura = 705;
		this.altura = 585;
		this.modeloM = ModeloMedicos.getInstancia();
		this.modeloT = new ModeloTelefonos();
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
		JLabel lblCod = new JLabel("*Código de empleado:");
		JLabel lblNombre = new JLabel("   *Nombre:");
		JLabel lblApellido = new JLabel("*Apellido:");
		JLabel lblDi = new JLabel("   Dirección:");
		JLabel lblCe = new JLabel("*Cédula:");
		JLabel lblTel = new JLabel("*Teléfono:");
		JLabel lblEsp = new JLabel("   Especialidad:");

		txtCod = new JTextField(10);
		txtNombre = new JTextField(10);
		txtApellido = new JTextField(10);
		txtDi = new JTextField(10);
		txtCe = new JTextField(10);
		txtCe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtCe.getText().length() == 13) {
					e.consume();
				}
			}
		});
		txtTelefono = new JTextField(10);
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!"".equals(txtTelefono.getText())) {
					btnAgregarT.setEnabled(true);
				} else {
					btnAgregarT.setEnabled(false);
				}
			}
		});
		txtTelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtTelefono.getText().length() == 12) {
					String telefono = txtTelefono.getText();
					if (Validador.telefonoEsValido(telefono)) {
						modeloT.agregar(telefono);
						txtTelefono.setText("");
						btnAgregarT.setEnabled(false);
					} else {
						JOptionPane
								.showMessageDialog(null,
										"Inserte un número correcto:\n*NNN-NNN-NNN ó \n*NNNNNNNNNN");
						txtTelefono.setText("");
						btnEliminarT.setEnabled(false);
					}
				} else if (txtTelefono.getText().length() == 10) {
					String telefono = txtTelefono.getText();
					String cod = telefono.substring(0, 3);
					String cod2 = telefono.substring(3, 6);
					String cod3 = telefono.substring(6, 10);
					telefono = cod + "-" + cod2 + "-" + cod3;
					modeloT.agregar(telefono);
					txtTelefono.setText("");
					btnAgregarT.setEnabled(false);
				} else {
					JOptionPane
							.showMessageDialog(null,
									"Inserte un número correcto:\n*NNN-NNN-NNN ó \n*NNNNNNNNNN");
					txtTelefono.setText("");
					btnEliminarT.setEnabled(false);
				}
			}
		});

		comboEspecialidades = modeloM.getEspecialidades();

		btnAgregarM = new JButton("Agregar Medico");
		btnAgregarM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtCod.getText())
						&& !"".equals(txtCod.getText())
						&& !"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtCe.getText())
						&& modeloT.getTelefonos().size() != 0) {

					String especialidad = comboEspecialidades
							.getItemAt(comboEspecialidades.getSelectedIndex());
					String codemp = txtCod.getText();
					String nombre = txtNombre.getText();
					String apellido = txtApellido.getText();
					String direccion = txtDi.getText();
					String cedula = txtCe.getText();
					if (Validador.cedulaEsValida(cedula)) {
					ArrayList<String> telefonos = modeloT.getTelefonos();
					Medico medico = new Medico(nombre, apellido, telefonos,
							direccion, cedula, especialidad, codemp);
					try {
						modeloM.agregar(medico);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JDOMException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					actualizar();
					} else {
						JOptionPane.showMessageDialog(null, "Inserte una cédula correcta.\nNNN-NNNNNNN-N");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
			}
		});

		btnEliminarM = new JButton("Eliminar Medico");
		btnEliminarM.setEnabled(false);
		btnEliminarM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tblMedicos.getSelectedRow() != -1) {
					try {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"Está seguro que desea eliminar este medico?\n\nNOTA: Se eliminará permanentemente.");
						if (respuesta == JOptionPane.YES_OPTION) {
							modeloM.eliminar(tblMedicos.getSelectedRow());
							btnEliminarM.setEnabled(false);
							actualizar();
						}
					} catch (ClassNotFoundException | SQLException
							| JDOMException | IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar un medico.");
				}
			}
		});

		btnAgregarT = new JButton("Agregar Telefono");
		btnAgregarT.setEnabled(false);
		btnAgregarT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtTelefono.getText().length() == 12) {
					String telefono = txtTelefono.getText();
					if (Validador.telefonoEsValido(telefono)) {
						modeloT.agregar(telefono);
						txtTelefono.setText("");
						btnAgregarT.setEnabled(false);
					} else {
						JOptionPane
								.showMessageDialog(null,
										"Inserte un número correcto:\n*NNN-NNN-NNN ó \n*NNNNNNNNNN");
						txtTelefono.setText("");
						btnAgregarT.setEnabled(false);
					}
				} else if (txtTelefono.getText().length() == 10) {
					String telefono = txtTelefono.getText();
					String cod = telefono.substring(0, 3);
					String cod2 = telefono.substring(3, 6);
					String cod3 = telefono.substring(6, 10);
					telefono = cod + "-" + cod2 + "-" + cod3;
					modeloT.agregar(telefono);
					txtTelefono.setText("");
					btnAgregarT.setEnabled(false);
				} else {
					JOptionPane
							.showMessageDialog(null,
									"Inserte un número correcto:\n*NNN-NNN-NNN ó \n*NNNNNNNNNN");
					txtTelefono.setText("");
					btnAgregarT.setEnabled(false);
				}
			}
		});
		btnEliminarT = new JButton("Eliminar Telefono");
		btnEliminarT.setEnabled(false);
		btnEliminarT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int respuesta = JOptionPane
						.showConfirmDialog(
								null,
								"Está seguro que desea eliminar este telefono?\n\nNOTA: Se eliminará permanentemente.");
				if (respuesta == JOptionPane.YES_OPTION) {
					modeloT.eliminar(tblTelefonos.getSelectedRow());
				}
			}
		});

		tblMedicos = new JTable(modeloM);
		tblMedicos.setPreferredScrollableViewportSize(new Dimension(595, 350));
		tblMedicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				medicoActual = modeloM.getMedicos().get(
						tblMedicos.getSelectedRow());
				txtNombre.setText(medicoActual.getNombre());
				txtApellido.setText(medicoActual.getApellido());
				txtCod.setText(medicoActual.getCodigoEmpleado());
				txtDi.setText(medicoActual.getDireccion());
				txtCe.setText(medicoActual.getCedula());
				comboEspecialidades.setSelectedItem(medicoActual
						.getEspecialidad());
				modeloT.setTelefonos(medicoActual.getTelefonos());
				btnCambios.setEnabled(true);
				btnEliminarM.setEnabled(true);
				btnAgregarM.setEnabled(false);
			}
		});
		tblTelefonos = new JTable(modeloT);
		tblTelefonos.setPreferredScrollableViewportSize(new Dimension(110, 50));
		tblTelefonos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnEliminarT.setEnabled(true);
				btnAgregarT.setEnabled(false);
			}
		});

		btnCambios = new JButton("Guardar Cambios");
		btnCambios.setEnabled(false);
		btnCambios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				medicoActual = modeloM.getMedicos().get(
						tblMedicos.getSelectedRow());
				if (!"".equals(txtCod.getText())
						&& !"".equals(txtCod.getText())
						&& !"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtCe.getText())
						&& modeloT.getTelefonos().size() != 0) {
					try {
						int esp = comboEspecialidades.getSelectedIndex();
						modeloM.modificar(medicoActual.getId(), 1,
								tblMedicos.getSelectedRow(), esp);
						String codigo_emp = txtCod.getText();
						modeloM.modificar(medicoActual.getId(), 2,
								tblMedicos.getSelectedRow(), codigo_emp);
						String nombre = txtNombre.getText();
						modeloM.modificar(medicoActual.getId(), 3,
								tblMedicos.getSelectedRow(), nombre);
						String apellido = txtApellido.getText();
						modeloM.modificar(medicoActual.getId(), 4,
								tblMedicos.getSelectedRow(), apellido);
						String dir = txtDi.getText();
						modeloM.modificar(medicoActual.getId(), 5,
								tblMedicos.getSelectedRow(), dir);
						String ced = txtCe.getText();
						modeloM.modificar(medicoActual.getId(), 6,
								tblMedicos.getSelectedRow(), ced);
						modeloM.setTelefonosPersona(medicoActual.getId(), modeloT.getTelefonos());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
				btnCambios.setEnabled(false);
				btnAgregarT.setEnabled(false);
				btnAgregarM.setEnabled(true);
				btnEliminarT.setEnabled(false);
				btnEliminarM.setEnabled(false);
				tblMedicos.clearSelection();
				modeloT.setTelefonos(new ArrayList<String>());
				comboEspecialidades.setSelectedIndex(0);
				txtCod.setText("");
				txtNombre.setText("");
				txtApellido.setText("");
				txtCe.setText("");
				txtDi.setText("");
			} 
		});

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
		panel.add(lblTel);
		panel.add(txtTelefono);
		panel.add(new JScrollPane(tblMedicos));
		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}


	
	public void actualizar() {
		btnCambios.setEnabled(false);
		btnAgregarT.setEnabled(false);
		btnAgregarM.setEnabled(true);
		btnEliminarT.setEnabled(false);
		btnEliminarM.setEnabled(false);
		tblMedicos.clearSelection();
		modeloT.setTelefonos(new ArrayList<String>());
		comboEspecialidades.setSelectedIndex(0);
		txtCod.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtCe.setText("");
		txtDi.setText("");
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
