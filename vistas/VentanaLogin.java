package edu.itla.gestorpacientes.vistas;

import java.awt.Color;
import java.awt.Cursor;
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

import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.algoritmos.AlgoritmoBusqueda;
import edu.itla.gestorpacientes.algoritmos.AlgoritmoBusquedaPerfil;
import edu.itla.gestorpacientes.entidades.Persona;
import edu.itla.gestorpacientes.enums.Rol;
import edu.itla.gestorpacientes.mail.CorreoElectronico;

public class VentanaLogin extends Ventana {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;
	private AlgoritmoBusqueda algoritmoB;
	private AlgoritmoBusquedaPerfil algoritmoBP;
	private VentanaPrincipal ventanaPrincipal;
	private Persona personaActual;
	private VentanaBusquedaPacientes ventanaBP;

	public VentanaLogin(AlgoritmoBusqueda algoritmo)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		this.algoritmoB = algoritmo;
		this.algoritmoBP = new AlgoritmoBusquedaPerfil();
		this.icono = "/edu/itla/gestorpacientes/imágenes/iconoLogin.png";
		this.anchura = 520;
		this.altura = 300;
		this.titulo = "ControlSoft v2.0";
		UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
		SwingUtilities.updateComponentTreeUI(this);
		ventanaBP = VentanaBusquedaPacientes.getInstancia();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() {
		this.panel = new JPanel(new FlowLayout());
		JPanel pnlGrid = new JPanel(new GridLayout(3, 2));
		JPanel pnlInferior = new JPanel(new FlowLayout());
		pnlInferior.setPreferredSize(new Dimension(400, 50));
		JPanel pnlPrueba = new JPanel(new FlowLayout());
		pnlPrueba.setPreferredSize(new Dimension(400, 25));

		JLabel lblTitulo = new JLabel(new ImageIcon(getClass().getResource(
				"/edu/itla/gestorpacientes/imágenes/login.png")));
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Bodoni MT Black", Font.ITALIC + Font.BOLD,
				18));
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setFont(new Font("Bodoni MT Black", Font.ITALIC
				+ Font.BOLD, 18));
		JButton botonIniciar = new JButton("Iniciar Sesión");
		botonIniciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Persona persona = (Persona) algoritmoB.buscar(new Persona(
						txtUsuario.getText(), new String(txtContraseña
								.getPassword())));
				if (persona != null) {
					String perfil = (String) algoritmoBP.buscar(persona);
					if (perfil.equals(Rol.ADMINISTRADOR.name())) {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaAdmin.png",
									"/edu/itla/gestorpacientes/imágenes/admin.png",
									persona);
							ventanaPrincipal.setVisible(true);
							ventanaBP.getBotonHistorial().setVisible(true);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 100));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 140));
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (JDOMException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else if (perfil.equals(Rol.MEDICO.name())) {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaMe.png",
									"/edu/itla/gestorpacientes/imágenes/fondo.png",
									persona);
							ventanaPrincipal.getBotonMUsuarios().setVisible(
									false);
							ventanaPrincipal.setVisible(true);
							ventanaBP.getBotonHistorial().setVisible(true);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 100));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 160));
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (JDOMException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaAsis.png",
									"/edu/itla/gestorpacientes/imágenes/asistente.png",
									persona);
							ventanaPrincipal.getBotonMPacientes().setText(
									"Litado de Pacientes");
							ventanaPrincipal.getBotonMUsuarios().setVisible(
									false);
							ventanaPrincipal.getBtnMMedicos().setVisible(false);
							ventanaPrincipal.getBtnMEspecialidades()
									.setVisible(false);
							ventanaPrincipal.getBtnMAsistentes().setVisible(
									false);
							ventanaPrincipal.getBtnEstadisticas().setVisible(
									false);
							ventanaPrincipal.getBtnMPLab().setVisible(false);
							ventanaPrincipal.getBotonMPadecimientos()
									.setVisible(false);
							ventanaPrincipal.getBtnMRecetas().setVisible(false);
							ventanaPrincipal.getBtnMRPLab().setVisible(false);
							ventanaBP.getBotonHistorial().setVisible(false);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 80));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 80));
							ventanaPrincipal.setVisible(true);
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException
								| SQLException | JDOMException | IOException e1) {
							e1.printStackTrace();
						}
					}
					txtContraseña.setText("");
				} else {
					JOptionPane.showMessageDialog(VentanaLogin.this,
							"Usuario o Contraseña inválidos.");
					txtContraseña.setText("");
				}
			}
		});
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int respuesta = JOptionPane.showConfirmDialog(null,
						"Está seguro ?");
				if (respuesta == JOptionPane.YES_OPTION) {
					VentanaLogin.this.dispose();
				}
			}
		});

		txtUsuario = new JTextField(10);
		txtUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Persona persona = (Persona) algoritmoB.buscar(new Persona(
						txtUsuario.getText(), new String(txtContraseña
								.getPassword())));
				if (persona != null) {
					String perfil = (String) algoritmoBP.buscar(persona);
					if (perfil.equals(Rol.ADMINISTRADOR.name())) {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaAdmin.png",
									"/edu/itla/gestorpacientes/imágenes/admin.png",
									persona);
							ventanaPrincipal.setVisible(true);
							ventanaBP.getBotonHistorial().setVisible(true);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 100));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 140));
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (JDOMException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else if (perfil.equals(Rol.MEDICO.name())) {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaMe.png",
									"/edu/itla/gestorpacientes/imágenes/fondo.png",
									persona);
							ventanaPrincipal.getBotonMUsuarios().setVisible(
									false);
							ventanaPrincipal.setVisible(true);
							ventanaBP.getBotonHistorial().setVisible(true);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 100));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 160));
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (JDOMException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaAsis.png",
									"/edu/itla/gestorpacientes/imágenes/asistente.png",
									persona);
							ventanaPrincipal.getBotonMPacientes().setText(
									"Litado de Pacientes");
							ventanaPrincipal.getBotonMUsuarios().setVisible(
									false);
							ventanaPrincipal.getBtnMMedicos().setVisible(false);
							ventanaPrincipal.getBtnMEspecialidades()
									.setVisible(false);
							ventanaPrincipal.getBtnMAsistentes().setVisible(
									false);
							ventanaPrincipal.getBtnEstadisticas().setVisible(
									false);
							ventanaPrincipal.getBtnMPLab().setVisible(false);
							ventanaPrincipal.getBotonMPadecimientos()
									.setVisible(false);
							ventanaPrincipal.getBtnMRecetas().setVisible(false);
							ventanaPrincipal.getBtnMRPLab().setVisible(false);
							ventanaBP.getBotonHistorial().setVisible(false);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 80));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 80));
							ventanaPrincipal.setVisible(true);
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException
								| SQLException | JDOMException | IOException e1) {
							e1.printStackTrace();
						}
					}
					txtContraseña.setText("");
				} else {
					JOptionPane.showMessageDialog(VentanaLogin.this,
							"Usuario o Contraseña inválidos.");
					txtContraseña.setText("");
				}
			}
		});

		txtContraseña = new JPasswordField(10);
		txtContraseña.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Persona persona = (Persona) algoritmoB.buscar(new Persona(
						txtUsuario.getText(), new String(txtContraseña
								.getPassword())));
				if (persona != null) {
					String perfil = (String) algoritmoBP.buscar(persona);
					if (perfil.equals(Rol.ADMINISTRADOR.name())) {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaAdmin.png",
									"/edu/itla/gestorpacientes/imágenes/admin.png",
									persona);
							ventanaPrincipal.setVisible(true);
							ventanaBP.getBotonHistorial().setVisible(true);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 100));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 140));
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (JDOMException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else if (perfil.equals(Rol.MEDICO.name())) {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaMe.png",
									"/edu/itla/gestorpacientes/imágenes/fondo.png",
									persona);
							ventanaPrincipal.getBotonMUsuarios().setVisible(
									false);
							ventanaPrincipal.setVisible(true);
							ventanaBP.getBotonHistorial().setVisible(true);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 100));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 160));
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (JDOMException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							ventanaPrincipal = new VentanaPrincipal(
									"/edu/itla/gestorpacientes/imágenes/iconoVentanaAsis.png",
									"/edu/itla/gestorpacientes/imágenes/asistente.png",
									persona);
							ventanaPrincipal.getBotonMPacientes().setText(
									"Litado de Pacientes");
							ventanaPrincipal.getBotonMUsuarios().setVisible(
									false);
							ventanaPrincipal.getBtnMMedicos().setVisible(false);
							ventanaPrincipal.getBtnMEspecialidades()
									.setVisible(false);
							ventanaPrincipal.getBtnMAsistentes().setVisible(
									false);
							ventanaPrincipal.getBtnEstadisticas().setVisible(
									false);
							ventanaPrincipal.getBtnMPLab().setVisible(false);
							ventanaPrincipal.getBotonMPadecimientos()
									.setVisible(false);
							ventanaPrincipal.getBtnMRecetas().setVisible(false);
							ventanaPrincipal.getBtnMRPLab().setVisible(false);
							ventanaBP.getBotonHistorial().setVisible(false);
							ventanaBP.getPnlGrid().setPreferredSize(
									new Dimension(550, 80));
							ventanaBP.getTblPacientes()
									.setPreferredScrollableViewportSize(
											new Dimension(700, 80));
							ventanaPrincipal.setVisible(true);
							VentanaLogin.this.dispose();
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException
								| SQLException | JDOMException | IOException e1) {
							e1.printStackTrace();
						}
					}
					txtContraseña.setText("");
				} else {
					JOptionPane.showMessageDialog(VentanaLogin.this,
							"Usuario o Contraseña inválidos.");
					txtContraseña.setText("");
				}
			}
		});

		JTextField enlace = new JTextField("No puedes iniciar sesión?", 15);
		enlace.setEditable(false);

		enlace.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 0)));
		enlace.setForeground(Color.blue);
		enlace.setBackground(new Color(0, 0, 0, 0));
		enlace.setCursor(new Cursor(Cursor.HAND_CURSOR));
		enlace.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String usuario = JOptionPane.showInputDialog(null,
						"Indícanos tu usuario del sistema\npara enviarte un correo"
								+ " con tu contraseña\nUsuario:");
				if (usuario != null) {
					Persona p = (Persona) algoritmoB
							.buscar(new Persona(usuario));
					if (p != null && !"".equals(p.getCorreo())) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						CorreoElectronico co = new CorreoElectronico();
						try {
							if (!p.getCorreo().equals(
									"ejemplo@[gmail|hotmail].com")) {
								co.EnviarCorreo(p);
								JOptionPane
										.showMessageDialog(null,
												"Hemos enviado su contraseña a su correo.");
							} else {
								JOptionPane
										.showMessageDialog(null,
												"El usuario introducido no tiene correo o no existe.");
							}
						} catch (MessagingException e) {
							JOptionPane.showMessageDialog(null,
									"Ha ocurrido un error al enviar el correo, error: "
											+ e.getMessage());
						}

					} else {
						JOptionPane
								.showMessageDialog(null,
										"El usuario introducido no tiene correo o no existe.");
					}
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});

		pnlInferior.add(botonIniciar);
		pnlInferior.add(botonCancelar);

		pnlGrid.add(lblUsuario);
		pnlGrid.add(txtUsuario);
		pnlGrid.add(lblContraseña);
		pnlGrid.add(txtContraseña);

		panel.add(lblTitulo);
		panel.add(pnlPrueba);
		panel.add(pnlGrid);
		panel.add(pnlInferior);
		panel.add(enlace);
		panel.setPreferredSize(new Dimension(anchura, altura));

		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}
}
