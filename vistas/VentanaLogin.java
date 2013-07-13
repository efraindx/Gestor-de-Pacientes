package com.efrain.gestorpacientes.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;

import com.efrain.gestorpacientes.algoritmos.AlgoritmoBusqueda;
import com.efrain.gestorpacientes.algoritmos.AlgoritmoBusquedaPerfil;
import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.entidades.Rol;

public class VentanaLogin extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaLogin instancia;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;
	private AlgoritmoBusqueda algoritmoB;
	private AlgoritmoBusquedaPerfil algoritmoBP;

	public static synchronized VentanaLogin getInstancia(
			AlgoritmoBusqueda algoritmo) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		return instancia == null ? instancia = new VentanaLogin(algoritmo)
				: instancia;
	}

	public VentanaLogin(AlgoritmoBusqueda algoritmo)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		this.algoritmoB = algoritmo;
		this.algoritmoBP = new AlgoritmoBusquedaPerfil();
		this.icono = "imágenes/iconoLogin.png";
		this.anchura = 520;
		this.altura = 300;
		this.titulo = "ControlSoft v2.0";
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

		JLabel lblTitulo = new JLabel(new ImageIcon("imágenes/login.png"));
		JLabel lblUsuario = new JLabel("Usuario:");
		JLabel lblContraseña = new JLabel("Contraseña:");

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
							VentanaAdministrador.getInstancia()
									.setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					} else if (perfil.equals(Rol.MEDICO.name())) {
						try {
							VentanaMedico.getInstancia().setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							VentanaAsistente.getInstancia().setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(VentanaLogin.this,
							"Usuario o Contraseña inválidos.");
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
							VentanaAdministrador.getInstancia()
									.setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					} else if (perfil.equals(Rol.MEDICO.name())) {
						try {
							VentanaMedico.getInstancia().setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							VentanaAsistente.getInstancia().setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(VentanaLogin.this,
							"Usuario o Contraseña inválidos.");
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
							VentanaAdministrador.getInstancia()
									.setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					} else if (perfil.equals(Rol.MEDICO.name())) {
						try {
							VentanaMedico.getInstancia().setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							VentanaAsistente.getInstancia().setVisible(true);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException
								| UnsupportedLookAndFeelException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(VentanaLogin.this,
							"Usuario o Contraseña inválidos.");
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
				JOptionPane.showMessageDialog(null, "ASDgasd");
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
}
