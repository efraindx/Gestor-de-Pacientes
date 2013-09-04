package edu.itla.gestorpacientes.vistas;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.utilidades.Dialogo;
import edu.itla.gestorpacientes.algoritmos.AlgoritmoBusquedaPersonas;
import edu.itla.gestorpacientes.modelos.ModeloPersonas;
import edu.itla.gestorpacientes.entidades.Persona;
import edu.itla.gestorpacientes.enums.Rol;
import edu.itla.gestorpacientes.vistas.VentanaModeloResultadosPruebasLaboratorio;

public class VentanaPrincipal extends Ventana {

	private static final long serialVersionUID = 1L;
	private String imagenBanner;
	private String imagenFondo;
	private JLabel lblTitulo;
	private JLabel lblCentral;
	private JLabel lblUsuarioActual;
	private JButton btnMUsuarios;
	private JButton btnMPacientes;
	private JButton btnMPadecimientos;
	private JButton btnMRecetas;
	private JButton btnMPLab;
	private JButton btnMResultados;
	private JButton btnMEspecialidades;
	private JButton btnMAsistentes;
	private JButton btnMMedicos;
	private JButton btnEstadisticas;
	private Persona personaActual;

	public VentanaPrincipal(String imagenBanner, String imagenFondo,
			Persona personaActual) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.anchura = 720;
		this.altura = 500;
		this.titulo = "Ventana Principal";
		this.personaActual = personaActual;
		lblTitulo = new JLabel(new ImageIcon(getClass().getResource(
				imagenBanner)));

		this.icono = "/edu/itla/gestorpacientes/imágenes/iconoVentana.jpg";
		lblCentral = new JLabel(new ImageIcon(getClass().getResource(
				imagenFondo)));
		lblUsuarioActual = new JLabel();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() {
		this.panel = new JPanel(new BorderLayout());

		JPanel pnlCentral = new JPanel(new FlowLayout());
		pnlCentral.setPreferredSize(new Dimension(600, 50));

		panel.add(BorderLayout.CENTER, lblCentral);

		lblUsuarioActual.setText(personaActual.getNombre() + " "
				+ personaActual.getApellido());
		lblUsuarioActual.setFont(new Font("Times New Roman", Font.BOLD
				+ Font.ITALIC, 20));
		lblUsuarioActual.setPreferredSize(new Dimension(400, 50));

		pnlCentral.add(lblTitulo);
		pnlCentral.add(lblUsuarioActual);
		panel.add(BorderLayout.NORTH, pnlCentral);

		JToolBar barraIzquierda = new JToolBar();
		barraIzquierda.setFloatable(false);
		barraIzquierda.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JToolBar barraDerecha = new JToolBar();
		barraDerecha.setFloatable(false);
		barraDerecha.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JToolBar barraInferior = new JToolBar();
		barraInferior.setFloatable(false);
		JPanel pnlInferior = new JPanel(new GridLayout(1, 3));
		pnlInferior.setPreferredSize(new Dimension(5, 50));
		barraInferior.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JPanel pnlIz = new JPanel(new GridLayout(6, 1));
		pnlIz.setPreferredSize(new Dimension(210, 50));
		JPanel pnlDerecha = new JPanel(new GridLayout(6, 1));
		pnlDerecha.setPreferredSize(new Dimension(230, 50));

		btnMPacientes = new JButton("Man. de Pacientes", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/pac.png")));
		btnMPacientes.setToolTipText("Mantenimiento de Pacientes");
		btnMPacientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (personaActual.getRol().equals(Rol.ADMINISTRADOR.name())
						|| personaActual.getRol().equals(Rol.MEDICO.name())) {

					try {
						VentanaModeloPacientes.getInstancia().setVisible(true);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JDOMException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					try {
						VentanaListadoPacientes.getInstancia().setVisible(true);
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException | SQLException
							| JDOMException | IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

		JButton btnMCitas = new JButton("Man. de Citas", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/citas.png")));
		btnMCitas.setToolTipText("Mantenimiento de Citas");
		btnMCitas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VentanaModeloCitas ventanaCitas;
				try {
					ventanaCitas = new VentanaModeloCitas();
					JDialog dialogo = Dialogo.getInstancia(ventanaCitas);
					dialogo.setContentPane(ventanaCitas.getContentPane());
					dialogo.setSize(ventanaCitas.getSize());
					dialogo.setLocationRelativeTo(null);
					dialogo.setResizable(false);
					dialogo.setTitle(ventanaCitas.getTitulo());
					dialogo.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

			}
		});

		btnMPadecimientos = new JButton("Man. de Padecimientos", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/pade.png")));
		btnMPadecimientos.setToolTipText("Mantenimiento de Padecimientos");
		btnMPadecimientos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					VentanaModeloPadecimientos.getInstancia().setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		});

		btnMRecetas = new JButton("Man. de Recetas", new ImageIcon(getClass()
				.getResource("/edu/itla/gestorpacientes/imágenes/receta.png")));
		btnMRecetas.setToolTipText("Mantenimiento de Recetas");
		btnMRecetas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VentanaModeloRecetas ventanaRecetas;
				try {
					ventanaRecetas = new VentanaModeloRecetas();
					JDialog dialogo = Dialogo.getInstancia(ventanaRecetas);
					dialogo.setContentPane(ventanaRecetas.getContentPane());
					dialogo.setSize(ventanaRecetas.getSize());
					dialogo.setLocationRelativeTo(null);
					dialogo.setResizable(false);
					dialogo.setTitle(ventanaRecetas.getTitulo());
					dialogo.setAlwaysOnTop(false);
					dialogo.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnMPLab = new JButton("Man. de Pruebas Lab.", new ImageIcon(getClass()
				.getResource("/edu/itla/gestorpacientes/imágenes/prueb.png")));
		btnMPLab.setToolTipText("Mantenimiento de Pruebas de Laboratorio");
		btnMPLab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					VentanaModeloPruebasLaboratorio.getInstancia().setVisible(
							true);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (JDOMException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnMResultados = new JButton("Man. Result. de Pruebas", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/result.png")));
		btnMResultados
				.setToolTipText("Mantenimiento de Resultados de Pruebas de Laboratorio");
		btnMResultados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaModeloResultadosPruebasLaboratorio ventanaRPruebas;
				try {
					ventanaRPruebas = new VentanaModeloResultadosPruebasLaboratorio();
					JDialog dialogo = Dialogo.getInstancia(ventanaRPruebas);
					dialogo.setContentPane(ventanaRPruebas.getContentPane());
					dialogo.setSize(ventanaRPruebas.getSize());
					dialogo.setLocationRelativeTo(null);
					dialogo.setTitle(ventanaRPruebas.getTitulo());
					dialogo.setVisible(true);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (JDOMException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			};
		});

		btnMUsuarios = new JButton("Man. de Usuarios", new ImageIcon(getClass()
				.getResource("/edu/itla/gestorpacientes/imágenes/usuario.png")));
		btnMUsuarios.setToolTipText("Mantenimiento de Usuarios");
		btnMUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int sizePersonas = ModeloPersonas.getInstancia()
							.getComboPersonas().getItemCount();
					if (sizePersonas != 0) {
						Ventana ventanaUsuarios;
						ventanaUsuarios = new VentanaModeloPersonas();
						JDialog dialogo = Dialogo.getInstancia(ventanaUsuarios);
						dialogo.setContentPane(ventanaUsuarios.getContentPane());
						dialogo.setSize(ventanaUsuarios.getSize());
						dialogo.setLocationRelativeTo(null);
						dialogo.setTitle(ventanaUsuarios.getTitulo());
						dialogo.setVisible(true);
					} else {
						JOptionPane
								.showMessageDialog(VentanaPrincipal.this,
										"No existen medicos ni asistentes registrados.");
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnMMedicos = new JButton("Man. de Médicos", new ImageIcon(getClass()
				.getResource("/edu/itla/gestorpacientes/imágenes/medico.png")));
		btnMMedicos.setToolTipText("Mantenimiento de Médicos");
		btnMMedicos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JDialog dialogo = Dialogo
							.getInstancia(new VentanaModeloMedicos());
					dialogo.setContentPane(new VentanaModeloMedicos()
							.getContentPane());
					dialogo.setSize(new VentanaModeloMedicos().getSize());
					dialogo.setLocationRelativeTo(null);
					dialogo.setTitle(new VentanaModeloMedicos().getTitulo());
					dialogo.setResizable(false);
					dialogo.setVisible(true);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (JDOMException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnMEspecialidades = new JButton("Man. de Espec.", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/esp.png")));
		btnMEspecialidades.setToolTipText("Mantenimiento de Especialidades");
		btnMEspecialidades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					VentanaModeloEspecialidades.getInstancia().setVisible(true);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (JDOMException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnMAsistentes = new JButton("Man. de Asistentes", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/asis.png")));
		btnMAsistentes.setToolTipText("Mantenimiento de Asistentes");
		btnMAsistentes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					VentanaModeloAsistentes.getInstancia().setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnLMedicos = new JButton("Listado de Médicos", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/med.png")));
		btnLMedicos.setToolTipText("Listado de Médicos");
		btnLMedicos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					VentanaListadoMedicos.getInstancia().setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnBPacientes = new JButton("Buscar Pacientes", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/lupa.png")));
		btnBPacientes.setToolTipText("Buscar Pacientes");
		btnBPacientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					VentanaBusquedaPacientes.getInstancia().setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnEstadisticas = new JButton("Estadísticas", new ImageIcon(getClass()
				.getResource("/edu/itla/gestorpacientes/imágenes/est.png")));
		btnEstadisticas.setToolTipText("Estadísticas");
		btnEstadisticas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					VentanaEstadisticas.getInstancia().setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnCerrar = new JButton("Cerrar Sesión", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/down.png")));
		btnCerrar.setToolTipText("Cerrar Sesión");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int resp = JOptionPane
							.showConfirmDialog(null,
									"Está seguro ?\nNOTA:Si hay procesos abiertos se cerrarán todos.");
					if (resp == JOptionPane.YES_OPTION) {
						VentanaModeloEspecialidades.getInstancia().setVisible(
								false);
						VentanaBusquedaPacientes.getInstancia().setVisible(
								false);
						VentanaListadoMedicos.getInstancia().setVisible(false);
						VentanaEstadisticas.getInstancia().setVisible(false);
						VentanaModeloAsistentes.getInstancia()
								.setVisible(false);
						VentanaModeloPacientes.getInstancia().setVisible(false);
						VentanaModeloPadecimientos.getInstancia().setVisible(
								false);
						VentanaModeloPruebasLaboratorio.getInstancia()
								.setVisible(false);
						VentanaPrincipal.this.dispose();
						new VentanaLogin(new AlgoritmoBusquedaPersonas())
								.setVisible(true);
					}
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException | SQLException
						| JDOMException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		pnlInferior.add(btnBPacientes);
		pnlInferior.add(btnEstadisticas);
		pnlInferior.add(btnLMedicos);
		barraInferior.add(pnlInferior);

		pnlDerecha.add(btnMPacientes);
		pnlDerecha.add(btnMCitas);
		pnlDerecha.add(btnMPadecimientos);
		pnlDerecha.add(btnMRecetas);
		pnlDerecha.add(btnMPLab);
		pnlDerecha.add(btnMResultados);
		barraDerecha.add(pnlDerecha);

		pnlIz.add(btnMUsuarios);
		pnlIz.add(btnMMedicos);
		pnlIz.add(btnMEspecialidades);
		pnlIz.add(btnMAsistentes);
		pnlIz.add(btnCerrar);

		pnlIz.add(lblUsuarioActual);
		barraIzquierda.add(pnlIz);

		panel.add(BorderLayout.WEST, barraIzquierda);
		panel.add(BorderLayout.EAST, barraDerecha);
		panel.add(BorderLayout.SOUTH, barraInferior);
		return panel;
	}

	public void setImagenFondo(String url) {
		lblTitulo = new JLabel(new ImageIcon(getClass().getResource(
				imagenBanner)));
	}

	public void setBanner(String banner) {
		lblCentral = new JLabel(new ImageIcon(getClass().getResource(
				imagenFondo)));
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	public JButton getBotonMUsuarios() {
		return btnMUsuarios;
	}

	public JButton getBotonMPacientes() {
		return btnMPacientes;
	}

	public JButton getBotonMPadecimientos() {
		return btnMPadecimientos;
	}

	public JButton getBtnMRecetas() {
		return btnMRecetas;
	}

	public void setBtnMRecetas(JButton btnMRecetas) {
		this.btnMRecetas = btnMRecetas;
	}

	public JButton getBtnMPLab() {
		return btnMPLab;
	}

	public void setBtnMPLab(JButton btnMPLab) {
		this.btnMPLab = btnMPLab;
	}

	public JButton getBtnMResultados() {
		return btnMResultados;
	}

	public void setBtnMResultados(JButton btnMResultados) {
		this.btnMResultados = btnMResultados;
	}

	public void setBtnMPacientes(JButton btn) {
		this.btnMPacientes = btn;
	}

	public JButton getBtnMMedicos() {
		return btnMMedicos;
	}

	public void setBtnMMedicos(JButton btnMMedicos) {
		this.btnMMedicos = btnMMedicos;
	}

	public JButton getBtnEstadisticas() {
		return btnEstadisticas;
	}

	public JButton getBtnMRPLab() {
		return btnMResultados;
	}

	public JButton getBtnMEspecialidades() {
		return btnMEspecialidades;
	}

	public void setBtnMEspecialidades(JButton btnMEspecialidades) {
		this.btnMEspecialidades = btnMEspecialidades;
	}

	public JButton getBtnMAsistentes() {
		return btnMAsistentes;
	}

	public void setBtnMAsistentes(JButton btnMAsistentes) {
		this.btnMAsistentes = btnMAsistentes;
	}

	public Persona getPersonaActual() {
		return personaActual;
	}

	public void setPersonaActual(Persona personaActual) {
		this.personaActual = personaActual;
	}
}