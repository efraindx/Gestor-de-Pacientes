package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
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
import edu.itla.gestorpacientes.utilidades.Validador;

public class VentanaModeloPacientes extends Ventana {

	private static final long serialVersionUID = 1L;
	private String[] meses = { "Mes", "Enero", "Febrero", "Marzo", "Abril",
			"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
			"Noviembre", "Diciembre" };

	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtCedula;
	private JTextArea txtAlergias;
	private JTextArea txtDireccion;
	private JLabel lblNombreFoto;
	private JRadioButton radioSi;
	private JRadioButton radioNo;
	private JRadioButton radioM;
	private JRadioButton radioF;
	private ButtonGroup bGS;
	private ButtonGroup bGF;
	private JTable tblPacientes;
	private String imagen;
	private JComboBox<String> comboDia;
	private JComboBox<String> comboMes;
	private JComboBox<String> comboAño;
	private static VentanaModeloPacientes instancia;
	private ModeloPacientes modelo;

	public static synchronized VentanaModeloPacientes getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloPacientes()
				: instancia;
	}

	public VentanaModeloPacientes() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.anchura = 770;
		this.altura = 560;
		this.titulo = "Pacientes";
		modelo = ModeloPacientes.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() {

		JPanel pnlPrincipal = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Pacientes");
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
		JLabel lblFoto = new JLabel("Foto:");
		JLabel lblSexo = new JLabel("   *Sexo:");
		lblNombreFoto = new JLabel();
		lblNombreFoto.setPreferredSize(new Dimension(200, 20));
		lblNombreFoto.setText("No se ha seleccionado ningún archivo.");

		try {
			tblPacientes = new JTable(ModeloPacientes.getInstancia());
			tblPacientes.setPreferredScrollableViewportSize(new Dimension(720,
					240));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		comboDia = getComboDia();
		comboMes = new JComboBox<String>(meses);
		comboAño = getcomboAño();

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

		JButton btnSeleccionar = new JButton("Seleccionar foto");
		btnSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileC = new JFileChooser();
				fileC.showOpenDialog(VentanaModeloPacientes.this);
				File archivo = fileC.getSelectedFile();
				if (archivo != null) {
					imagen = archivo.getAbsolutePath();
					lblNombreFoto.setText(imagen);
				}
			}
		});
		btnSeleccionar.setPreferredSize(new Dimension(150, 30));

		JButton btnAgregar = new JButton("Agregar Paciente");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtNombre.getText())
						&& !"".equals(txtApellido.getText())
						&& !"".equals(txtDireccion.getText())
						&& !"Dia".equals(comboDia.getSelectedItem())
						&& !"Mes".equals(comboMes.getSelectedItem())
						&& !"Año".equals(comboAño.getSelectedItem())
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
					String direccion = txtDireccion.getText();
					String cedula = txtCedula.getText();
					String dia = (String) comboDia.getSelectedItem();
					int mes = comboMes.getSelectedIndex();
					String year = (String) comboAño.getSelectedItem();
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
							imagen = "/com/efrain/gestorpacientes/imágenes/woman.png";
						} else {
							imagen = "/com/efrain/gestorpacientes/imágenes/man.png";
						}
					}

					if (Validador.cedulaEsValida(cedula)) {

						Paciente paciente = new Paciente(nombre, apellido,
								sexo, telefono, direccion, cedula,
								fecha_nacimiento, fumador, imagen, alergias);
						try {
							modelo.agregar(paciente);
							txtNombre.setText("");
							txtApellido.setText("");
							txtCedula.setText("");
							txtTelefono.setText("");
							comboDia.setSelectedIndex(0);
							comboMes.setSelectedIndex(0);
							comboAño.setSelectedIndex(0);
							txtDireccion.setText("");
							txtAlergias.setText("");
							bGS.clearSelection();
							bGF.clearSelection();
							lblNombreFoto
									.setText("No se ha seleccionado ningún archivo.");
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
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
			}
		});

		JButton btnModificar = new JButton("Modificar Paciente");
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tblPacientes.getSelectedRow() != -1) {

				} else {
					JOptionPane.showMessageDialog(null,
							"Tiene que seleccionar un Paciente");
				}
			}
		});

		JButton btnEliminar = new JButton("Eliminar Paciente");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblPacientes.getSelectedRow() != -1) {
					try {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"Está seguro que desea eliminar este paciente?\n\nNOTA: Se eliminará permanentemente.");
						if (respuesta == JOptionPane.YES_OPTION) {
							modelo.eliminar(tblPacientes.getSelectedRow());
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar un paciente.");
				}
			}
		});

		JPanel pnlGrid = new JPanel(new GridLayout(2, 5));
		pnlGrid.setPreferredSize(new Dimension(new Dimension(700, 50)));
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
		pnlPrincipal.add(lblDireccion);
		pnlPrincipal.add(new JScrollPane(txtDireccion));
		pnlPrincipal.add(lblAlergias);
		pnlPrincipal.add(new JScrollPane(txtAlergias));
		pnlPrincipal.add(lblFecha_Naci);
		pnlPrincipal.add(comboDia);
		pnlPrincipal.add(comboMes);
		pnlPrincipal.add(comboAño);
		pnlPrincipal.add(pnlGridRadios);
		pnlPrincipal.add(lblFoto);
		pnlPrincipal.add(btnSeleccionar);
		pnlPrincipal.add(lblNombreFoto);
		pnlPrincipal.add(btnAgregar);
		pnlPrincipal.add(btnEliminar);
		pnlPrincipal.add(new JScrollPane(tblPacientes));
		return pnlPrincipal;
	}

	private JComboBox<String> getComboDia() {
		comboDia = new JComboBox<String>();
		comboDia.addItem("Dia");
		for (int i = 1; i <= 31; i++) {
			comboDia.addItem(Integer.toString(i));
		}
		return comboDia;
	}

	private JComboBox<String> getcomboAño() {
		comboAño = new JComboBox<String>();
		comboAño.addItem("Año");
		for (int i = 2013; i >= 1990; i--) {
			comboAño.addItem(Integer.toString(i));
		}
		return comboAño;
	}

	public static void main(String[] args) {
		try {
			try {
				new VentanaModeloPacientes().setVisible(true);
			} catch (SQLException | JDOMException | IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}
}
