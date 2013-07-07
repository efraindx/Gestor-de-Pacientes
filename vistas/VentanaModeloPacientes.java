package com.efrain.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import com.efrain.gestorpacientes.modelos.ModeloPacientes;

public class VentanaModeloPacientes extends Ventana {

	private static final long serialVersionUID = 1L;
	private String[] meses = { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo",
			"Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
			"Diciembre" };
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextArea txtDireccion;
	private JTextField txtCedula;
	private JTable tablaPacientes = null;
	private JComboBox<String> comboDia, comboMes, comboAño;

	public VentanaModeloPacientes() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.anchura = 660;
		this.altura = 540;
		this.titulo = "Pacientes";
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() {
		
		JPanel pnlPrincipal = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Pacientes");
		lblTitulo.setFont(new Font("Monotype Coursiva", Font.BOLD + Font.ITALIC, 16));
		JLabel lblNombre = new JLabel("Nombre:");
		JLabel lblApellido = new JLabel("Apellido:");
		JLabel lblTelefono = new JLabel("Teléfono:");
		JLabel lblDireccion = new JLabel("Dirección:");
		JLabel lblIntermedio = new JLabel();
		lblIntermedio.setPreferredSize(new Dimension(3,3));
		JLabel lblIntermedio2 = new JLabel();
		lblIntermedio2.setPreferredSize(new Dimension(3,3));
		JLabel lblIntermedio3 = new JLabel();
		lblIntermedio3.setPreferredSize(new Dimension(270,50));
		JLabel lblCedula = new JLabel("Cédula:");
		JLabel lblFecha_Naci = new JLabel("Fec. Nac.:");
		//lblFecha_Naci.setPreferredSize(new Dimension(115, 10));
		JLabel lblFumador = new JLabel("Fumador?");
		JLabel lblAlergias = new JLabel("Alergias:");
		JLabel lblFoto = new JLabel("Foto:");
		
		try {
			tablaPacientes = new JTable(ModeloPacientes.getInstancia());
			tablaPacientes.setPreferredScrollableViewportSize(new Dimension(630, 240));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		JButton btnSeleccionar = new JButton("Seleccionar foto");
		btnSeleccionar.setPreferredSize(new Dimension(310, 30));
		JButton btnAgregar = new JButton("Agregar Paciente"); 
		JButton btnModificar = new JButton("Modificar Paciente");
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tablaPacientes.getSelectedRow() != -1) {
					
				} else {
					JOptionPane.showMessageDialog(null, "Tiene que seleccionar un Paciente");
				}
			}
		});
		JButton btnEliminar = new JButton("Eliminar Paciente");
		
		JTextField txtNombre = new JTextField(10);
		JTextField txtApellido = new JTextField(10);
		JTextField txtTelefono = new JTextField(10);
		JTextField txtCedula = new JTextField(10);
		
		JTextArea txtDireccion = new JTextArea(3, 15);
		JTextArea txtAlergias = new JTextArea(3, 15);
		
		comboDia = getComboDia();
		comboMes = new JComboBox<String>(meses);
		comboAño = getcomboAño();
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton radioSi = new JRadioButton("Si");
		JRadioButton radioNo = new JRadioButton("No");
		radioNo.setPreferredSize(new Dimension(80,50));
		bg.add(radioSi);
		bg.add(radioNo);
		
		JPanel pnlGrid = new JPanel(new GridLayout(2, 5));
		pnlGrid.setPreferredSize(new Dimension(new Dimension(600,50)));
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
		pnlPrincipal.add(lblFumador);
		pnlPrincipal.add(radioSi);
		pnlPrincipal.add(radioNo);
		pnlPrincipal.add(lblFoto);
		pnlPrincipal.add(btnSeleccionar);
		pnlPrincipal.add(btnAgregar);
		pnlPrincipal.add(btnEliminar);
		pnlPrincipal.add(new JScrollPane(tablaPacientes));
		return pnlPrincipal;
	}

	private JComboBox<String> getComboDia() {
		comboDia = new JComboBox<String>();
		comboDia.addItem("Dia");
		for(int i = 1; i <= 31; i++) {
			comboDia.addItem(Integer.toString(i));
		} 
		return comboDia;
	}
	
	private JComboBox<String> getcomboAño() {
		comboAño = new JComboBox<String>();
		comboAño.addItem("Año");
		for(int i = 2013; i >= 1990; i--) {
			comboAño.addItem(Integer.toString(i));
		}
		return comboAño;
	}
	
	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	
	public static void main(String[] args) {
		try {
			new VentanaModeloPacientes().setVisible(true);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
