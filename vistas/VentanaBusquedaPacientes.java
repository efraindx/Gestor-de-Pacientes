package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.modelos.ModeloBusquedaPacientes;
import edu.itla.gestorpacientes.utilidades.AjustadorFotos;
import edu.itla.gestorpacientes.utilidades.Dialogo;

public class VentanaBusquedaPacientes extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaBusquedaPacientes instancia;
	private ModeloBusquedaPacientes modelo;
	private JTextField txtPalabra;
	private JTextArea txtDireccion;
	private JTextArea txtAlergias;
	private JTable tblPacientes;
	private JLabel lblFoto;
	private JLabel txtNombre;
	private JLabel txtApellido;
	private JLabel txtSexo;
	private JLabel txtTelefono;
	private JLabel txtCed;
	private JLabel txtFecha;
	private JLabel txtFumador;
	private JPanel pnlGrid;
	private JPanel pnlAreas;
	private ArrayList<Paciente> pacientes;
	private JButton btnHistorial;

	public static synchronized VentanaBusquedaPacientes getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaBusquedaPacientes()
				: instancia;
	}

	private VentanaBusquedaPacientes() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Búsquedas";
		this.anchura = 750;
		this.altura = 470;
		this.icono = "/edu/itla/gestorpacientes/imágenes/lupa.png";
		modelo = ModeloBusquedaPacientes.getInstancia();
		pacientes = modelo.getPacientesBusqueda();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());
		pnlGrid = new JPanel(new GridLayout(4, 4));
		pnlGrid.setPreferredSize(new Dimension(550, 100));
		pnlGrid.setVisible(false);
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtNombre = new JLabel();
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtApellido = new JLabel();
		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtSexo = new JLabel();
		JLabel lblTelefono = new JLabel("Teléfono");
		lblTelefono.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtTelefono = new JLabel();
		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtCed = new JLabel();
		JLabel lblFecha = new JLabel("Fecha de Nacimiento:");
		lblFecha.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtFecha = new JLabel();
		JLabel lblFumador = new JLabel("Fumador?:");
		lblFumador.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtFumador = new JLabel();
		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setFont(new Font("Times New Roman", Font.BOLD, 12));
		JLabel lblAlergias = new JLabel("Alergias:");
		lblAlergias.setFont(new Font("Times New Roman", Font.BOLD, 12));

		pnlGrid.add(lblNombre);
		pnlGrid.add(txtNombre);
		pnlGrid.add(lblApellido);
		pnlGrid.add(txtApellido);
		pnlGrid.add(lblSexo);
		pnlGrid.add(txtSexo);
		pnlGrid.add(lblTelefono);
		pnlGrid.add(txtTelefono);
		pnlGrid.add(lblCedula);
		pnlGrid.add(txtCed);
		pnlGrid.add(lblFecha);
		pnlGrid.add(txtFecha);
		pnlGrid.add(lblFumador);
		pnlGrid.add(txtFumador);
		pnlGrid.add(new JLabel());
		pnlGrid.add(new JLabel());

		JLabel lblTitulo = new JLabel("Búsqueda de Pacientes ", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/lupa.png")), 0);
		lblTitulo.setFont(new Font("Tw Cen MT Condensed Extra Bold",
				Font.ITALIC + Font.BOLD, 22));
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(300, 100));
		JLabel lblPalabra = new JLabel(
				"Inserte el nombre, apellido y/o cédula del paciente:");
		lblFoto = new JLabel();

		modelo.setPacientesBusqueda(new ArrayList<Paciente>());
		tblPacientes = new JTable(modelo);
		tblPacientes
				.setPreferredScrollableViewportSize(new Dimension(700, 160));
		tblPacientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Paciente paciente = pacientes.get(tblPacientes.getSelectedRow());
				pnlGrid.setVisible(true);
				String foto = paciente.getFoto();
				ImageIcon icono = null;
				if (foto.substring(0, 1).equals("/")) {
					icono = AjustadorFotos.ajustarImagen(Toolkit
							.getDefaultToolkit().getImage(
									getClass().getResource(foto)));
				} else {
					icono = AjustadorFotos.ajustarImagen(Toolkit
							.getDefaultToolkit().getImage(foto));
				}

				lblFoto.setIcon(icono);
				pnlAreas.setVisible(true);
				txtNombre.setText(paciente.getNombre());

				txtApellido.setText(paciente.getApellido());
				txtCed.setText(paciente.getCedula());
				txtDireccion.setText(paciente.getDireccion());
				txtAlergias.setText(paciente.getAlergias());
				txtSexo.setText(paciente.getSexo());
				txtTelefono.setText(paciente.getTelefono());
				txtFecha.setText(paciente.getFecha_nacimiento());
				txtFumador.setText(paciente.getFumador());
				VentanaBusquedaPacientes.this.setSize(750, 550);
				tblPacientes.setPreferredScrollableViewportSize(new Dimension(
						700, 120));
				btnHistorial.setEnabled(true);
			}
		});

		txtPalabra = new JTextField(10);
		txtPalabra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if ("".equals(txtPalabra.getText())) {
					modelo.setPacientesBusqueda(new ArrayList<Paciente>());
					pnlAreas.setVisible(false);
					pnlGrid.setVisible(false);
					lblFoto.setIcon(null);
					VentanaBusquedaPacientes.this.setSize(750, 470);
					tblPacientes
							.setPreferredScrollableViewportSize(new Dimension(
									700, 450));
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				try {
					modelo.setPacientesBusqueda(modelo.getPacientes(txtPalabra
							.getText()));
					pacientes = modelo.getPacientesBusqueda();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnBuscar = new JButton("Buscar Paciente");
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("".equals(txtPalabra.getText())) {
					modelo.setPacientesBusqueda(new ArrayList<Paciente>());
					pnlAreas.setVisible(false);
					pnlGrid.setVisible(false);
					lblFoto.setIcon(null);
					VentanaBusquedaPacientes.this.setSize(750, 470);
					btnHistorial.setEnabled(false);
				} else {
					try {
						modelo.setPacientesBusqueda(modelo
								.getPacientes(txtPalabra.getText()));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			}
		});

		btnHistorial = new JButton("Ver Historial Paciente");
		btnHistorial.setEnabled(false);
		btnHistorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Paciente paciente = pacientes.get(tblPacientes.getSelectedRow());
				try {
					ArrayList<String> datos = modelo.getDatos("fecha", "citas",
							paciente.getId());
					if (datos.size() != 0) {
						VentanaHistorialPacientes vHistorial;
						vHistorial = new VentanaHistorialPacientes(paciente);
						JDialog dialogo = Dialogo.getInstancia(vHistorial);
						dialogo.setContentPane(vHistorial.getContentPane());
						dialogo.setSize(vHistorial.getSize());
						dialogo.setLocationRelativeTo(null);
						dialogo.setResizable(false);
						dialogo.setTitle("Reportes");
						dialogo.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(
								VentanaBusquedaPacientes.this,
								"Este paciente no tiene historial.");
					}
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException | SQLException
						| JDOMException | IOException e) {
					e.printStackTrace();
				}
			}
		});

		txtAlergias = new JTextArea(3, 15);
		txtAlergias.setEditable(false);
		txtDireccion = new JTextArea(3, 15);
		txtDireccion.setEditable(false);

		pnlAreas = new JPanel(new FlowLayout());
		pnlAreas.setVisible(false);
		pnlAreas.add(lblAlergias);
		pnlAreas.add(new JScrollPane(txtAlergias));
		pnlAreas.add(lblDireccion);
		pnlAreas.add(new JScrollPane(txtDireccion));

		panel.add(lblTitulo);
		panel.add(lblBlanco);
		panel.add(lblPalabra);
		panel.add(txtPalabra);
		panel.add(btnBuscar);
		panel.add(btnHistorial);
		panel.add(lblFoto);
		panel.add(pnlGrid);
		panel.add(pnlAreas);
		panel.add(new JScrollPane(tblPacientes));

		return panel;
	}
	
	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	public JTable getTblPacientes() {
		return tblPacientes;
	}

	public void setTblPacientes(JTable tblPacientes) {
		this.tblPacientes = tblPacientes;
	}

	public JPanel getPnlGrid() {
		return pnlGrid;
	}

	public void setPnlGrid(JPanel pnlGrid) {
		this.pnlGrid = pnlGrid;
	}

	public JButton getBotonHistorial() {
		return btnHistorial;
	}

}