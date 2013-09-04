package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.modelos.ModeloPacientes;

public class VentanaListadoPacientes extends Ventana {

	private static final long serialVersionUID = 1L;
	private JTable tblPacientes;
	private ModeloPacientes modelo;
	private static VentanaListadoPacientes instancia;
	private String[] meses = { "Mes", "Enero", "Febrero", "Marzo",
			"Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtCedula;
	private JTextArea txtAlergias;
	private JTextArea txtDireccion;
	private ArrayList<Paciente> pacientes;
	private JRadioButton radioM;
	private JRadioButton radioF;
	private JRadioButton radioSi;
	private JRadioButton radioNo;
	private JComboBox<String> cmbAño;
	private JComboBox<String> cmbDia;
	private ButtonGroup bGS;
	private ButtonGroup bGF;
	private JComboBox<String> cmbMes;

	public static synchronized VentanaListadoPacientes getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaListadoPacientes()
				: instancia;
	}

	private VentanaListadoPacientes() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Listado de Pacientes";
		this.anchura = 860;
		this.altura = 550;
		this.icono = "/edu/itla/gestorpacientes/imágenes/pac.png";
		modelo = ModeloPacientes.getInstancia();
		pacientes = modelo.getListadoPacientes();
		modelo.setPacientes(pacientes);
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		
		JPanel pnlPrincipal = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Listado de Pacientes",
				new ImageIcon(getClass().getResource(
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
		JLabel lblSexo = new JLabel("   *Sexo:");
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(100, 50));

		txtNombre = new JTextField(10);
		txtNombre.setEditable(false);
		txtApellido = new JTextField(10);
		txtApellido.setEditable(false);
		txtTelefono = new JTextField(10);
		txtTelefono.setEditable(false);

		txtCedula = new JTextField(10);
		txtCedula.setEditable(false);

		txtDireccion = new JTextArea(3, 15);
		txtDireccion.setEditable(false);
		txtAlergias = new JTextArea(3, 15);
		txtAlergias.setEditable(false);

		cmbDia = getComboDia();
		cmbDia.setPreferredSize(new Dimension(60, 25));
		cmbMes = new JComboBox<String>(meses);
		cmbMes.setPreferredSize(new Dimension(100, 25));
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

		tblPacientes = new JTable(ModeloPacientes.getInstancia());
		tblPacientes
				.setPreferredScrollableViewportSize(new Dimension(720, 240));
		tblPacientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Paciente pacienteActual = pacientes.get(tblPacientes.getSelectedRow());
				txtNombre.setText(pacienteActual.getNombre());
				txtApellido.setText(pacienteActual.getApellido());
				txtTelefono.setText(pacienteActual.getTelefono());
				txtCedula.setText(pacienteActual.getCedula());
				txtDireccion.setText(pacienteActual.getDireccion());
				txtAlergias.setText(pacienteActual.getAlergias());
				
				String[] fechaNaci = pacienteActual.getFecha_nacimiento().split("/");
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
		pnlPrincipal.add(new JScrollPane(tblPacientes));
		
		return pnlPrincipal;
	}


	private JComboBox<String> getcomboAño() {
		cmbAño = new JComboBox<String>();
		cmbAño.addItem("Año");
		for (int i = 2013; i >= 1990; i--) {
			cmbAño.addItem(Integer.toString(i));
		}
		return cmbAño;
	}

	private JComboBox<String> getComboDia() {
		cmbDia = new JComboBox<String>();
		cmbDia.addItem("Dia");
		for (int i = 1; i <= 31; i++) {
			cmbDia.addItem(Integer.toString(i));
		}
		return cmbDia;
	}
}
