package edu.itla.gestorpacientes.vistas;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.entidades.Asistente;
import edu.itla.gestorpacientes.modelos.ModeloAsistentes;
import edu.itla.gestorpacientes.modelos.ModeloTelefonos;
import edu.itla.gestorpacientes.utilidades.Validador;

public class VentanaModeloAsistentes extends Ventana {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txtCod;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDi;
	private JTextField txtTelefono;
	private JTextField txtCe;
	private JTable tblAsistentes;
	private JTable tblTelefonos;
	private JScrollPane scrollTelefonos;
	private ModeloAsistentes modeloA;
	private ModeloTelefonos modeloT;
	private JButton btnCambios;
	private JButton btnAgregarT;
	private JButton btnEliminarT;
	private JButton btnEliminarA;
	private JButton btnAgregarA;
	private Asistente asistenteActual;
	private static VentanaModeloAsistentes instancia;
	
	public static synchronized VentanaModeloAsistentes getInstancia() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloAsistentes() : instancia;
	}
	
	public VentanaModeloAsistentes() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		this.titulo = "Mantenimiento de Asistentes";
		this.altura = 585;
		this.anchura = 705;
		modeloT = new ModeloTelefonos();
		modeloA = ModeloAsistentes.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Asistentes");
		lblTitulo.setFont(new Font("Monotype Coursiva",
				Font.BOLD + Font.ITALIC, 16));
		lblTitulo.setPreferredSize(new Dimension(280, 50));
		JLabel lblCod = new JLabel("*Código de empleado:");
		JLabel lblNombre = new JLabel("   *Nombre:");
		JLabel lblApellido = new JLabel("*Apellido:");
		JLabel lblDi = new JLabel("   Dirección:");
		JLabel lblCe = new JLabel("*Cédula:");
		JLabel lblTel = new JLabel("*Teléfono:");

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

		btnAgregarA = new JButton("Agregar Asistente");
		btnAgregarA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtCod.getText())
						&& !"".equals(txtCod.getText())
						&& !"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtCe.getText())
						&& modeloT.getTelefonos().size() != 0) {

					String codemp = txtCod.getText();
					String nombre = txtNombre.getText();
					String apellido = txtApellido.getText();
					String direccion = txtDi.getText();
					String cedula = txtCe.getText();
					if (Validador.cedulaEsValida(cedula)) {
					ArrayList<String> telefonos = modeloT.getTelefonos();
					Asistente asistente = new Asistente(nombre, apellido, telefonos,
							direccion, cedula, codemp);
					try {
						modeloA.agregar(asistente);
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

		btnEliminarA = new JButton("Eliminar Asistente");
		btnEliminarA.setEnabled(false);
		btnEliminarA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tblAsistentes.getSelectedRow() != -1) {
					try {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"Está seguro que desea eliminar este asistente?\n\nNOTA: Se eliminará permanentemente.");
						if (respuesta == JOptionPane.YES_OPTION) {
							modeloA.eliminar(tblAsistentes.getSelectedRow());
							btnEliminarA.setEnabled(false);
							actualizar();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar un asistente.");
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

		tblAsistentes = new JTable(modeloA);
		tblAsistentes.setPreferredScrollableViewportSize(new Dimension(595, 350));
		tblAsistentes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				asistenteActual = modeloA.getAsistentes().get(
						tblAsistentes.getSelectedRow());
				txtNombre.setText(asistenteActual.getNombre());
				txtApellido.setText(asistenteActual.getApellido());
				txtCod.setText(asistenteActual.getCodigoEmpleado());
				txtDi.setText(asistenteActual.getDireccion());
				txtCe.setText(asistenteActual.getCedula());
				modeloT.setTelefonos(asistenteActual.getTelefonos());
				btnCambios.setEnabled(true);
				btnEliminarA.setEnabled(true);
				btnAgregarA.setEnabled(false);
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
				asistenteActual = modeloA.getAsistentes().get(
						tblAsistentes.getSelectedRow());
				if (!"".equals(txtCod.getText())
						&& !"".equals(txtCod.getText())
						&& !"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtCe.getText())
						&& modeloT.getTelefonos().size() != 0) {
					try {
						String codigo_emp = txtCod.getText();
						modeloA.modificar(asistenteActual.getId(), 1,
								tblAsistentes.getSelectedRow(), codigo_emp);
						String nombre = txtNombre.getText();
						modeloA.modificar(asistenteActual.getId(), 2,
								tblAsistentes.getSelectedRow(), nombre);
						String apellido = txtApellido.getText();
						modeloA.modificar(asistenteActual.getId(), 3,
								tblAsistentes.getSelectedRow(), apellido);
						String dir = txtDi.getText();
						modeloA.modificar(asistenteActual.getId(), 4,
								tblAsistentes.getSelectedRow(), dir);
						String ced = txtCe.getText();
						modeloA.modificar(asistenteActual.getId(), 5,
								tblAsistentes.getSelectedRow(), ced);
						modeloA.setTelefonosPersona(asistenteActual.getId(), modeloT.getTelefonos());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
				btnCambios.setEnabled(false);
				btnAgregarT.setEnabled(false);
				btnAgregarA.setEnabled(true);
				btnEliminarT.setEnabled(false);
				btnEliminarA.setEnabled(false);
				tblAsistentes.clearSelection();
				modeloT.setTelefonos(new ArrayList<String>());
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

		JPanel pnlGridTel = new JPanel(new GridLayout(2, 1));
		pnlGridTel.add(btnAgregarT);
		pnlGridTel.add(btnEliminarT);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		scrollTelefonos = new JScrollPane(tblTelefonos);
		scrollTelefonos.setWheelScrollingEnabled(false);
		panel.add(scrollTelefonos);
		panel.add(pnlGridTel);
		panel.add(btnAgregarA);
		panel.add(btnEliminarA);
		panel.add(btnCambios);
		panel.add(lblTel);
		panel.add(txtTelefono);
		panel.add(new JScrollPane(tblAsistentes));
		return panel;
	}
	
	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}
	
	public void actualizar() {
		btnCambios.setEnabled(false);
		btnAgregarT.setEnabled(false);
		btnAgregarA.setEnabled(true);
		btnEliminarT.setEnabled(false);
		btnEliminarA.setEnabled(false);
		tblAsistentes.clearSelection();
		modeloT.setTelefonos(new ArrayList<String>());
		txtCod.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtCe.setText("");
		txtDi.setText("");
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		new VentanaModeloAsistentes().setVisible(true);
	}

}
