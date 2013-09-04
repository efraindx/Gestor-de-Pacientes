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
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.enums.Fumador;
import edu.itla.gestorpacientes.enums.Sexo;
import edu.itla.gestorpacientes.modelos.ModeloPacientes;
import edu.itla.gestorpacientes.utilidades.AjustadorFotos;
import edu.itla.gestorpacientes.utilidades.Validador;

public class VentanaModeloPacientes extends Ventana {

	private static final long serialVersionUID = 1L;
	private String[] meses = { "Mes", "Enero", "Febrero", "Marzo", "Abríl",
			"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
			"Noviembre", "Diciembre" };
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtCedula;
	private JTextArea txtAlergias;
	private JTextArea txtDireccion;
	private JLabel lblNombreFoto;
	private JLabel lblFoto;
	private JLabel lblTitulo;
	private JRadioButton radioSi;
	private JRadioButton radioNo;
	private JRadioButton radioM;
	private JRadioButton radioF;
	private ButtonGroup bGS;
	private ButtonGroup bGF;
	private JButton btnModificar;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnSeleccionar;
	private JTable tblPacientes;
	private String imagen;
	private JComboBox<String> cmbDia;
	private JComboBox<String> cmbMes;
	private JComboBox<String> cmbAño;
	private ArrayList<Paciente> pacientes;
	private static VentanaModeloPacientes instancia;
	private Paciente pacienteActual;
	private ModeloPacientes modelo;

	public static synchronized VentanaModeloPacientes getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloPacientes()
				: instancia;
	}

	private VentanaModeloPacientes() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.anchura = 800;
		this.altura = 550;
		this.titulo = "Pacientes";
		this.icono = "/edu/itla/gestorpacientes/imágenes/pac.png";
		modelo = ModeloPacientes.getInstancia();
		modelo.setEditable(false);
		pacientes = modelo.getPacientes();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() {

		JPanel pnlPrincipal = new JPanel(new FlowLayout());

		lblTitulo = new JLabel("Mantenimiento de Pacientes", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/pac.png")), 0);
		lblTitulo.setFont(new Font("Monotype Coursiva",
				Font.BOLD + Font.ITALIC, 16));
		JLabel lblNombre = new JLabel("*Nombre:");
		JLabel lblApellido = new JLabel("*Apellido:");
		JLabel lblTelefono = new JLabel("*Teléfono:");
		JLabel lblDireccion = new JLabel("*Dirección:");
		JLabel lblIntermedio = new JLabel();
		lblIntermedio.setPreferredSize(new Dimension(3, 3));
		JLabel lblIntermedio2 = new JLabel();
		lblIntermedio2.setPreferredSize(new Dimension(3, 3));
		JLabel lblIntermedio3 = new JLabel();
		lblIntermedio3.setPreferredSize(new Dimension(270, 50));
		JLabel lblCedula = new JLabel("*Cédula:");
		JLabel lblFecha_Naci = new JLabel("*Fec. Nac.:");
		JLabel lblFumador = new JLabel("   *Fumador?");
		JLabel lblAlergias = new JLabel("Alergias:");
		JLabel lblNFoto = new JLabel("Foto:");
		JLabel lblSexo = new JLabel("   *Sexo:");
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(120, 25));
		lblNombreFoto = new JLabel();
		lblNombreFoto.setPreferredSize(new Dimension(300, 20));
		lblNombreFoto.setText("No se ha seleccionado ningún archivo.");
		lblFoto = new JLabel();

		txtNombre = new JTextField(10);
		txtApellido = new JTextField(10);
		txtTelefono = new JTextField(10);
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtTelefono.getText().length() == 12) {
					e.consume();
				}
			}
		});
		txtCedula = new JTextField(10);
		txtCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtCedula.getText().length() == 13) {
					e.consume();
				}
			}
		});

		txtDireccion = new JTextArea(3, 15);
		txtAlergias = new JTextArea(3, 15);

		cmbDia = getComboDia();
		cmbDia.setPreferredSize(new Dimension(60, 25));
		cmbMes = new JComboBox<String>(meses);
		cmbMes.setPreferredSize(new Dimension(85, 25));
		cmbAño = getcomboAño();
		cmbAño.setPreferredSize(new Dimension(65, 25));

		bGS = new ButtonGroup();
		radioSi = new JRadioButton("Si");
		radioNo = new JRadioButton("No");
		bGS.add(radioSi);
		bGS.add(radioNo);
		bGF = new ButtonGroup();
		radioM = new JRadioButton("Masculino");
		radioF = new JRadioButton("Femenino");
		bGF.add(radioF);
		bGF.add(radioM);

		btnSeleccionar = new JButton("Seleccionar foto");
		btnSeleccionar.setPreferredSize(new Dimension(150, 30));
		btnSeleccionar.addActionListener(new ActionListener() {
			private File archivo;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileC = new JFileChooser();
				fileC.showOpenDialog(VentanaModeloPacientes.this);
				archivo = fileC.getSelectedFile();
				if (archivo != null && pacienteActual != null) {
					pacienteActual.setFoto(archivo.getAbsolutePath());
					imagen = pacienteActual.getFoto();
					lblNombreFoto.setText(imagen);
					ImageIcon icono = AjustadorFotos.ajustarImagen(Toolkit
							.getDefaultToolkit().getImage(imagen));
					lblFoto.setIcon(icono);
				} else if (pacienteActual == null) {
					imagen = archivo.getAbsolutePath();
					ImageIcon icono = AjustadorFotos.ajustarImagen(Toolkit
							.getDefaultToolkit().getImage(imagen));
					lblFoto.setIcon(icono);
				}
			}
		});

		btnAgregar = new JButton("Agregar Paciente");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtDireccion.getText())
						&& !"Dia".equals(cmbDia.getSelectedItem())
						&& !"Mes".equals(cmbMes.getSelectedItem())
						&& !"Año".equals(cmbAño.getSelectedItem())
						&& !"".equals(txtTelefono.getText())
						&& radioSi.isSelected() == true
						|| radioNo.isSelected() == true
						&& radioM.isSelected() == true
						|| radioF.isSelected() == true) {

					String telefono = null;

					if (txtTelefono.getText().length() == 10) {
						telefono = txtTelefono.getText();
						String cod = telefono.substring(0, 3);
						String cod2 = telefono.substring(3, 6);
						String cod3 = telefono.substring(6, 10);
						telefono = cod + "-" + cod2 + "-" + cod3;
						txtTelefono.setText("");
					} else {
						telefono = txtTelefono.getText();
					}

					String nombre = txtNombre.getText();
					String apellido = txtApellido.getText();
					String d = txtDireccion.getText();
					StringTokenizer st = new StringTokenizer(d, "\n");
					StringBuilder constructor = new StringBuilder();
					while (st.hasMoreTokens()) {
						constructor.append(st.nextToken() + "\n");
					}
					String direccion = constructor.toString();
					String cedula = txtCedula.getText();
					String dia = (String) cmbDia.getSelectedItem();
					int mes = cmbMes.getSelectedIndex();
					String year = (String) cmbAño.getSelectedItem();
					String fecha_nacimiento = dia + "/" + mes + "/" + year;

					String fumador = null;
					if (radioSi.isSelected()) {
						fumador = Fumador.SI.name();
					} else if (radioNo.isSelected()) {
						fumador = Fumador.NO.name();
					}

					String sexo = null;
					if (radioM.isSelected()) {
						sexo = Sexo.MASCULINO.name();
					} else {
						sexo = Sexo.FEMENINO.name();
					}

					String alergias = txtAlergias.getText();
					if (alergias.equals("")) {
						alergias = "Ninguna";
					}

					if (imagen == null) {
						if (sexo.equals(Sexo.FEMENINO.name())) {
							imagen = "/edu/itla/gestorpacientes/imágenes/woman.png";
						} else {
							imagen = "/edu/itla/gestorpacientes/imágenes/man.png";
						}
					}

					if (Validador.cedulaEsValida(cedula)) {
						Paciente paciente = new Paciente(nombre, apellido,
								sexo, telefono, direccion, cedula,
								fecha_nacimiento, fumador, imagen, alergias);
						try {
							modelo.agregar(paciente);
							actualizar();
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
								"Inserte una cédula correcta.\nNNN-NNNNNNN-N");
					}
					try {
						pacientes = modelo.getPacientes();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
			}
		});

		btnModificar = new JButton("Guardar Cambios");
		btnModificar.setEnabled(false);
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					pacienteActual = modelo.getPacientes().get(
							tblPacientes.getSelectedRow());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (!"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtDireccion.getText())
						&& !"Dia".equals(cmbDia.getSelectedItem())
						&& !"Mes".equals(cmbMes.getSelectedItem())
						&& !"Año".equals(cmbAño.getSelectedItem())
						&& !"".equals(txtTelefono.getText())
						&& radioSi.isSelected() == true
						|| radioNo.isSelected() == true
						&& radioM.isSelected() == true
						|| radioF.isSelected() == true) {

					String telefono = null;

					if (txtTelefono.getText().length() == 10) {
						telefono = txtTelefono.getText();
						String cod = telefono.substring(0, 3);
						String cod2 = telefono.substring(3, 6);
						String cod3 = telefono.substring(6, 10);
						telefono = cod + "-" + cod2 + "-" + cod3;
						txtTelefono.setText("");
					} else {
						telefono = txtTelefono.getText();
					}

					int id = pacienteActual.getId();
					int fila = tblPacientes.getSelectedRow();

					String nombre = txtNombre.getText();
					String apellido = txtApellido.getText();
					String direccion = txtDireccion.getText();
					String cedula = txtCedula.getText();
					String dia = (String) cmbDia.getSelectedItem();
					int mes = cmbMes.getSelectedIndex();
					String year = (String) cmbAño.getSelectedItem();
					String fecha_nacimiento = dia + "/" + mes + "/" + year;
					String fumador = null;
					if (radioSi.isSelected()) {
						fumador = Fumador.SI.name();
					} else if (radioNo.isSelected()) {
						fumador = Fumador.NO.name();
					}

					String sexo = null;
					if (radioM.isSelected()) {
						sexo = Sexo.MASCULINO.name();
					} else {
						sexo = Sexo.FEMENINO.name();
					}

					String alergias = txtAlergias.getText();
					if (alergias.equals("")) {
						alergias = "Ninguna";
					}

					if (Validador.cedulaEsValida(cedula)) {

						try {
							modelo.modificar(id, 0, fila, nombre);
							pacienteActual.setNombre(nombre);
							modelo.modificar(id, 1, fila, apellido);
							modelo.modificar(id, 2, fila, sexo);
							modelo.modificar(id, 3, fila, telefono);
							modelo.modificar(id, 4, fila, direccion);
							modelo.modificar(id, 5, fila, cedula);
							modelo.modificar(id, 6, fila, fecha_nacimiento);
							modelo.modificar(id, 7, fila, fumador);
							modelo.modificar(id, 8, fila, imagen);
							modelo.modificar(id, 9, fila, alergias);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						actualizar();
						VentanaModeloPacientes.this.setSize(800, 550);
						btnSeleccionar.setText("Seleccionar foto");
					} else {
						JOptionPane.showMessageDialog(null,
								"Inserte una cédula correcta.\nNNN-NNNNNNN-N");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
				imagen = null;
			}

		});

		btnEliminar = new JButton("Eliminar Paciente");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane
						.showConfirmDialog(
								null,
								"Está seguro que desea eliminar esta receta?\n\nNOTA: Se eliminará permanentemente.");
				if (respuesta == JOptionPane.YES_OPTION) {
					try {
						modelo.eliminar(tblPacientes.getSelectedRow());
						actualizar();
						VentanaModeloPacientes.this.setSize(800, 550);
						btnSeleccionar.setText("Seleccionar foto");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				imagen = null;
			}
		});

		tblPacientes = new JTable(modelo);
		tblPacientes
				.setPreferredScrollableViewportSize(new Dimension(720, 200));
		tblPacientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				VentanaModeloPacientes.this.setSize(800, 610);
				btnSeleccionar.setText("Cambiar Foto");
				try {
					pacientes = modelo.getPacientes();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				pacienteActual = pacientes.get(tblPacientes.getSelectedRow());
				txtNombre.setText(pacienteActual.getNombre());
				txtApellido.setText(pacienteActual.getApellido());
				txtTelefono.setText(pacienteActual.getTelefono());
				txtCedula.setText(pacienteActual.getCedula());
				txtDireccion.setText(pacienteActual.getDireccion());
				txtAlergias.setText(pacienteActual.getAlergias());
				imagen = pacienteActual.getFoto();
				ImageIcon icono = null;

				if (imagen.substring(0, 1).equals("/")) {
					icono = AjustadorFotos.ajustarImagen(Toolkit
							.getDefaultToolkit().getImage(
									getClass().getResource(imagen)));
				} else {
					icono = AjustadorFotos.ajustarImagen(Toolkit
							.getDefaultToolkit().getImage(imagen));
				}

				lblFoto.setIcon(icono);
				lblNombreFoto.setText("");
				String[] fechaNaci = pacienteActual.getFecha_nacimiento()
						.split("/");
				String dia = fechaNaci[0];
				int mes = Integer.parseInt(fechaNaci[1]);
				String year = fechaNaci[2];
				cmbAño.setSelectedItem(year);
				cmbDia.setSelectedItem(dia);
				cmbMes.setSelectedIndex(mes);

				if ("MASCULINO".equals(pacienteActual.getSexo())) {
					radioM.setSelected(true);
				} else {
					radioF.setSelected(true);
				}

				if ("SI".equals(pacienteActual.getFumador())) {
					radioSi.setSelected(true);
				} else {
					radioNo.setSelected(true);
				}
				btnModificar.setEnabled(true);
				btnEliminar.setEnabled(true);
				btnAgregar.setEnabled(false);
			}
		});

		JPanel pnlGrid = new JPanel(new GridLayout(2, 5));
		pnlGrid.setPreferredSize(new Dimension(new Dimension(600, 50)));
		pnlGrid.add(lblNombre);
		pnlGrid.add(txtNombre);
		pnlGrid.add(lblIntermedio);
		pnlGrid.add(lblApellido);
		pnlGrid.add(txtApellido);
		pnlGrid.add(lblTelefono);
		pnlGrid.add(txtTelefono);
		pnlGrid.add(lblIntermedio2);
		pnlGrid.add(lblCedula);
		pnlGrid.add(txtCedula);

		JPanel pnlGridRadios = new JPanel(new GridLayout(2, 2));
		pnlGridRadios.add(lblFumador);
		pnlGridRadios.add(radioSi);
		pnlGridRadios.add(radioNo);
		pnlGridRadios.add(lblSexo);
		pnlGridRadios.add(radioM);
		pnlGridRadios.add(radioF);

		pnlPrincipal.add(lblTitulo);
		pnlPrincipal.add(pnlGrid);
		pnlPrincipal.add(lblBlanco);
		pnlPrincipal.add(lblDireccion);
		pnlPrincipal.add(new JScrollPane(txtDireccion));
		pnlPrincipal.add(lblAlergias);
		pnlPrincipal.add(new JScrollPane(txtAlergias));
		pnlPrincipal.add(lblFecha_Naci);
		pnlPrincipal.add(cmbDia);
		pnlPrincipal.add(cmbMes);
		pnlPrincipal.add(cmbAño);
		pnlPrincipal.add(pnlGridRadios);
		pnlPrincipal.add(lblNFoto);
		pnlPrincipal.add(btnSeleccionar);
		pnlPrincipal.add(lblNombreFoto);
		pnlPrincipal.add(lblFoto);
		pnlPrincipal.add(btnAgregar);
		pnlPrincipal.add(btnModificar);
		pnlPrincipal.add(btnEliminar);
		pnlPrincipal.add(new JScrollPane(tblPacientes));
		return pnlPrincipal;
	}
	
	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	private JComboBox<String> getComboDia() {
		cmbDia = new JComboBox<String>();
		cmbDia.addItem("Dia");
		for (int i = 1; i <= 31; i++) {
			cmbDia.addItem(Integer.toString(i));
		}
		return cmbDia;
	}

	public JLabel getLblTitulo() {
		return lblTitulo;
	}

	private JComboBox<String> getcomboAño() {
		cmbAño = new JComboBox<String>();
		cmbAño.addItem("Año");
		for (int i = 2013; i >= 1990; i--) {
			cmbAño.addItem(Integer.toString(i));
		}
		return cmbAño;
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(JTextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public JTextField getTxtCedula() {
		return txtCedula;
	}

	public void setTxtCedula(JTextField txtCedula) {
		this.txtCedula = txtCedula;
	}

	public JTextArea getTxtAlergias() {
		return txtAlergias;
	}

	public void setTxtAlergias(JTextArea txtAlergias) {
		this.txtAlergias = txtAlergias;
	}

	public JTextArea getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(JTextArea txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public JButton getBotonAgregar() {
		return btnAgregar;
	}

	public JButton getBotonEliminar() {
		return btnEliminar;
	}

	public JButton getBotonCambios() {
		return btnModificar;
	}

	public JButton getBotonFotos() {
		return btnSeleccionar;
	}

	private void actualizar() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtCedula.setText("");
		txtTelefono.setText("");
		cmbDia.setSelectedIndex(0);
		cmbMes.setSelectedIndex(0);
		cmbAño.setSelectedIndex(0);
		txtDireccion.setText("");
		txtAlergias.setText("");
		bGS.clearSelection();
		bGF.clearSelection();
		btnModificar.setEnabled(false);
		btnAgregar.setEnabled(true);
		tblPacientes.clearSelection();
		btnEliminar.setEnabled(false);
		lblNombreFoto.setText("No se ha seleccionado ningún archivo.");
		lblFoto.setIcon(null);
	}
}
