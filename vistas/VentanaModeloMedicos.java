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
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Especialidad;
import edu.itla.gestorpacientes.entidades.Medico;
import edu.itla.gestorpacientes.modelos.ModeloMedicos;
import edu.itla.gestorpacientes.modelos.ModeloTelefonos;
import edu.itla.gestorpacientes.utilidades.Validador;

public class VentanaModeloMedicos extends Ventana {

	private JTextField txtCod;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextArea txtDi;
	private JTextField txtTelefono;
	private JTextField txtCe;
	private JTable tblMedicos;
	private JTable tblTelefonos;
	private JComboBox<Especialidad> comboEspecialidades;
	private JScrollPane scrollTelefonos;
	private ModeloMedicos modeloM;
	private ModeloTelefonos modeloT;
	private JButton btnCambios;
	private JButton btnAgregarT;
	private JButton btnEliminarT;
	private JButton btnEliminarM;
	private JButton btnAgregarM;
	private Medico medicoActual;

	private static final long serialVersionUID = 1L;

	public VentanaModeloMedicos() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Médicos";
		this.anchura = 855;
		this.altura = 570;
		this.modeloM = ModeloMedicos.getInstancia();
		this.modeloT = new ModeloTelefonos();
		this.icono = "/edu/itla/gestorpacientes/imágenes/medico.png";
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Médicos",
				new ImageIcon(getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/medico.png")), 0);
		lblTitulo.setFont(new Font("Monotype Coursiva",
				Font.BOLD + Font.ITALIC, 16));
		lblTitulo.setPreferredSize(new Dimension(300, 50));
		JLabel lblCod = new JLabel("*Código de empleado:");
		lblCod.setFont(new Font("Monotype Coursiva", Font.ITALIC, 14));
		JLabel lblNombre = new JLabel("   *Nombre:");
		lblNombre.setFont(new Font("Monotype Coursiva", Font.ITALIC, 14));
		JLabel lblApellido = new JLabel("*Apellido:");
		lblApellido.setFont(new Font("Monotype Coursiva", Font.ITALIC, 14));
		JLabel lblDi = new JLabel("   Dirección:");
		lblDi.setFont(new Font("Monotype Coursiva", Font.ITALIC, 14));
		JLabel lblCe = new JLabel("*Cédula:");
		lblCe.setFont(new Font("Monotype Coursiva", Font.ITALIC, 14));
		JLabel lblTel = new JLabel("*Teléfono:");
		lblTel.setFont(new Font("Monotype Coursiva", Font.ITALIC, 14));
		JLabel lblEsp = new JLabel("   Especialidad:");
		lblEsp.setFont(new Font("Monotype Coursiva", Font.ITALIC, 14));

		txtCod = new JTextField(10);
		txtNombre = new JTextField(10);
		txtApellido = new JTextField(10);
		txtDi = new JTextArea(4, 14);
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
						&& !"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtCe.getText())
						&& modeloT.getTelefonos().size() != 0) {

					Especialidad especialidad = comboEspecialidades
							.getItemAt(comboEspecialidades.getSelectedIndex());
					String codemp = txtCod.getText();
					String nombre = txtNombre.getText();
					String apellido = txtApellido.getText();
					String d = txtDi.getText();
					StringTokenizer st = new StringTokenizer(d, "\n");
					StringBuilder constructor = new StringBuilder();
					while (st.hasMoreTokens()) {
						constructor.append(st.nextToken() + "\n");
					}
					String direccion = constructor.toString();
					String cedula = txtCe.getText();
					if (Validador.cedulaEsValida(cedula)) {
						ArrayList<String> telefonos = modeloT.getTelefonos();
						Medico medico = new Medico(nombre, apellido, telefonos,
								direccion, cedula, codemp, especialidad);
						medico.setIdEspecialidad(especialidad.getId());
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
						JOptionPane.showMessageDialog(null,
								"Inserte una cédula correcta.\nNNN-NNNNNNN-N");
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
		tblMedicos.setPreferredScrollableViewportSize(new Dimension(595, 280));
		tblMedicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tblMedicos.getSelectedRow() != -1) {
					medicoActual = modeloM.getMedicos().get(
							tblMedicos.getSelectedRow());
					txtNombre.setText(medicoActual.getNombre());
					txtApellido.setText(medicoActual.getApellido());
					txtCod.setText(medicoActual.getCodigoEmpleado());
					txtDi.setText(medicoActual.getDireccion());
					txtCe.setText(medicoActual.getCedula());
					Especialidad especialidad = medicoActual.getEspecialidad();
					comboEspecialidades
							.setSelectedItem(getItemEspecialidad(especialidad
									.getId()));
					modeloT.setTelefonos(medicoActual.getTelefonos());
					btnCambios.setEnabled(true);
					btnEliminarM.setEnabled(true);
					btnAgregarM.setEnabled(false);
				}
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
						int idEsp = comboEspecialidades.getItemAt(
								comboEspecialidades.getSelectedIndex()).getId();
						int fila = tblMedicos.getSelectedRow();
						int id = medicoActual.getId();

						modeloM.modificar(id, 1, fila, idEsp);
						String codigo_emp = txtCod.getText();
						modeloM.modificar(id, 2, fila, codigo_emp);
						String nombre = txtNombre.getText();
						modeloM.modificar(id, 3, fila, nombre);
						String apellido = txtApellido.getText();
						modeloM.modificar(id, 4, fila, apellido);
						String dir = txtDi.getText();
						modeloM.modificar(id, 5, fila, dir);
						String ced = txtCe.getText();
						modeloM.modificar(id, 6, fila, ced);
						modeloM.setTelefonosPersona(id, modeloT.getTelefonos());
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
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
			}
		});

		JPanel pnlGrid = new JPanel(new GridLayout(3, 4));
		pnlGrid.setPreferredSize(new Dimension(570, 80));
		pnlGrid.add(lblCod);
		pnlGrid.add(txtCod);
		pnlGrid.add(lblNombre);
		pnlGrid.add(txtNombre);
		pnlGrid.add(lblApellido);
		pnlGrid.add(txtApellido);
		pnlGrid.add(lblEsp);
		pnlGrid.add(comboEspecialidades);
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
		panel.add(lblDi);
		panel.add(new JScrollPane(txtDi));
		panel.add(btnAgregarM);
		panel.add(btnEliminarM);

		panel.add(btnCambios);
		panel.add(lblTel);
		panel.add(txtTelefono);
		panel.add(new JScrollPane(tblMedicos));
		return panel;
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

	public Especialidad getItemEspecialidad(int id) {
		Especialidad retorno = null;
		for (int i = 0; i < comboEspecialidades.getItemCount(); i++) {
			if (comboEspecialidades.getItemAt(i).getId() == id) {
				retorno = comboEspecialidades.getItemAt(i);
			}
		}
		return retorno;
	}
}
