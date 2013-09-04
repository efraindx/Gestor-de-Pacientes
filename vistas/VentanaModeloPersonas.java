package edu.itla.gestorpacientes.vistas;

import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.encriptación.Encriptadora;
import edu.itla.gestorpacientes.entidades.Persona;
import edu.itla.gestorpacientes.modelos.ModeloPersonas;

public class VentanaModeloPersonas extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<Persona> comboPersonas;
	private JComboBox<String> comboRoles;
	private JTable tblUsuarios;
	private JTextField txtUsuario;
	private JTextField txtCorreo;
	private JPasswordField txtContrase;
	private ModeloPersonas modelo;
	private JButton btnEliminar;

	public VentanaModeloPersonas() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Usuarios";
		this.anchura = 700;
		this.altura = 460;
		this.icono = "/edu/itla/gestorpacientes/imágenes/usuario.png";
		modelo = ModeloPersonas.getInstancia();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		this.panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Usuarios",
				new ImageIcon(getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/usuario.png")), 0);
		lblTitulo.setFont(new Font("Monotype Coursiva",
				Font.BOLD + Font.ITALIC, 20));
		JLabel lblBlanco3 = new JLabel();
		lblBlanco3.setPreferredSize(new Dimension(30, 50));
		lblTitulo.setForeground(Color.red);
		JLabel lblPersona = new JLabel("        Persona:");
		lblPersona.setFont(new Font("Monotype Coursiva", Font.ITALIC, 16));
		JLabel lblRol = new JLabel("    Rol:");
		lblRol.setFont(new Font("Monotype Coursiva", Font.ITALIC, 16));
		JLabel lblUsuario = new JLabel("       *Usuario:");
		lblUsuario.setFont(new Font("Monotype Coursiva", Font.ITALIC, 16));
		JLabel lblContrase = new JLabel("   *Contraseña:");
		lblContrase.setFont(new Font("Monotype Coursiva", Font.ITALIC, 16));
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(70, 10));
		JLabel lblCorreo = new JLabel("Correo Electrónico:");
		lblCorreo.setFont(new Font("Monotype Coursiva", Font.ITALIC, 16));
		JLabel lblBlanco2 = new JLabel();
		lblBlanco2.setPreferredSize(new Dimension(70, 20));

		txtCorreo = new JTextField(18);
		txtCorreo.setText("ejemplo@[gmail|hotmail].com");
		txtCorreo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCorreo.setText("");
			}
		});

		txtUsuario = new JTextField(10);
		txtContrase = new JPasswordField();
		txtContrase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String contrase = new String(txtContrase.getPassword());
				if (!"".equals(txtUsuario.getText()) & !"".equals(contrase)) {
					Persona persona = comboPersonas.getItemAt(comboPersonas
							.getSelectedIndex());
					String nombre = persona.getNombre();
					String apellido = persona.getApellido();
					String rol = comboRoles.getItemAt(comboRoles
							.getSelectedIndex());
					String usuario = txtUsuario.getText();
					contrase = Encriptadora.encriptar(contrase);
					String correo = txtCorreo.getText();
					Persona objeto = new Persona(rol, nombre, apellido,
							usuario, contrase, correo);
					try {
						if ("".equals(txtCorreo.getText())) {
							int resp = JOptionPane
									.showConfirmDialog(
											null,
											"ATENCION:Está a punto de registrar un"
													+ " usuario\n que no tiene correo, es recomendable que tenga\n correo para posteriormente"
													+ " enviarle su contraseña\n en caso de que la olvide. Agregar de todas formas?");
							if (resp == JOptionPane.YES_OPTION)
								modelo.agregar(objeto);

						} else {
							modelo.agregar(objeto);
						}

					} catch (ClassNotFoundException | SQLException
							| JDOMException | IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}

			}
		});

		comboRoles = new JComboBox<String>();

		JButton btnAgregar = new JButton("Agregar Usuario");
		btnAgregar.setFont(new Font("Monotype Coursiva", Font.ITALIC, 16));
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String contrase = new String(txtContrase.getPassword());
				if (!"".equals(txtUsuario.getText()) && !"".equals(contrase)) {
					Persona persona = comboPersonas.getItemAt(comboPersonas
							.getSelectedIndex());
					String usuario = txtUsuario.getText();
					contrase = Encriptadora.encriptar(contrase);
					String correo = txtCorreo.getText();
					persona.setUsuario(usuario);
					persona.setContrase(contrase);
					persona.setCorreo(correo);

					try {
						if (modelo.usuarioExiste(usuario)) {
							JOptionPane.showMessageDialog(null,
									"Este usuario ya existe.");
						} else {

							if ("".equals(txtCorreo.getText())) {
								int resp = JOptionPane
										.showConfirmDialog(
												null,
												"ATENCION:Está a punto de registrar un"
														+ " usuario\n que no tiene correo, es recomendable que tenga\n correo para posteriormente"
														+ " enviarle su contraseña\n en caso de que la olvide. Agregar de todas formas?");

								if (resp == JOptionPane.YES_OPTION) {
									modelo.agregar(persona);
									txtUsuario.setText("");
									txtContrase.setText("");
									txtCorreo.setText("ejemplo@[gmail|hotmail].com");
								}
							} else {
								modelo.agregar(persona);
								txtUsuario.setText("");
								txtContrase.setText("");
								txtCorreo.setText("ejemplo@[gmail|hotmail].com");
							}
						}
					} catch (ClassNotFoundException | SQLException
							| JDOMException | IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}

			}
		});

		btnEliminar = new JButton("Eliminar Usuario");
		btnEliminar.setFont(new Font("Monotype Coursiva", Font.ITALIC, 16));
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tblUsuarios.getSelectedRow() != -1) {
					try {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"Está seguro que desea eliminar este usuario?\n\nNOTA: Se eliminará permanentemente.");
						if (respuesta == JOptionPane.YES_OPTION) {
							modelo.eliminar(tblUsuarios.getSelectedRow());
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

		tblUsuarios = new JTable(modelo);
		tblUsuarios.setPreferredScrollableViewportSize(new Dimension(630, 210));
		tblUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnEliminar.setEnabled(true);
			}
		});

		comboPersonas = getComboUsuarios();
		comboRoles = getComboRoles();

		JPanel pnlGrid = new JPanel(new GridLayout(2, 4)); 
		pnlGrid.setPreferredSize(new Dimension(500, 50));
		pnlGrid.add(lblPersona);
		pnlGrid.add(comboPersonas);
		pnlGrid.add(lblRol);
		pnlGrid.add(comboRoles);
		pnlGrid.add(lblUsuario);
		pnlGrid.add(txtUsuario);
		pnlGrid.add(lblContrase);
		pnlGrid.add(txtContrase);

		panel.add(lblTitulo);
		panel.add(lblBlanco3);
		panel.add(pnlGrid);
		panel.add(lblBlanco);
		panel.add(lblCorreo);
		panel.add(txtCorreo);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(lblBlanco2);
		panel.add(new JScrollPane(tblUsuarios));
		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	public JComboBox<Persona> getComboUsuarios() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		return modelo.getComboPersonas();
	}

	public JComboBox<String> getComboRoles() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		return modelo.getRoles();
	}

	@Override
	public boolean esSalirAlCerrar() {
		return false;
	}
}
