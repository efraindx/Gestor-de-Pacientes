package com.efrain.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

import com.efrain.gestorpacientes.encriptación.Encriptadora;
import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.modelos.ModeloPersonas;

public class VentanaModeloPersonas extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboPersonas;
	private JComboBox<String> comboRoles;
	private ArrayList<Persona> personas;
	private JTable tablaUsuarios;
	private JTextField txtUsuario;
	private JPasswordField txtContrase;
	private ModeloPersonas modeloUsuarios;
	private static VentanaModeloPersonas instancia;
	
	public static synchronized VentanaModeloPersonas getInstancia() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloPersonas() : instancia;
	}

	public VentanaModeloPersonas() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Usuarios";
		this.anchura = 600;
		this.altura = 530;
		modeloUsuarios = ModeloPersonas.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		this.panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Usuarios");
		lblTitulo.setFont(new Font("Monotype Coursiva",
				Font.BOLD + Font.ITALIC, 16));
		JLabel lblPersona = new JLabel("Persona:");
		JLabel lblRol = new JLabel("    Rol:");
		JLabel lblUsuario = new JLabel("*Usuario:");
		JLabel lblContrase = new JLabel("   *Contraseña:");
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(70, 10));

		txtUsuario = new JTextField(10);
		txtContrase = new JPasswordField();

		tablaUsuarios = new JTable(ModeloPersonas.getInstancia());
		tablaUsuarios
				.setPreferredScrollableViewportSize(new Dimension(550, 350));

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtUsuario.getText())) {
					String persona = comboPersonas.getItemAt(comboPersonas
							.getSelectedIndex());
					String nombre = persona.split(" ")[0];
					String apellido = persona.split(" ")[1];
					String rol = comboRoles.getItemAt(comboRoles
							.getSelectedIndex());
					String usuario = txtUsuario.getText();
					String contrase = Encriptadora.encriptar(new String(txtContrase.getPassword()));
					Persona objeto = new Persona(rol, nombre, apellido,
							usuario, contrase);
					try {
						modeloUsuarios.agregar(objeto);
					} catch (ClassNotFoundException | SQLException
							| JDOMException | IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Los campos con \"*\" son obligatorios.");
				}

			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tablaUsuarios.getSelectedRow() != -1) {
					try {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"Está seguro que desea eliminar este usuario?\n\nNOTA: Se eliminará permanentemente.");
						if (respuesta == JOptionPane.YES_OPTION) {
							modeloUsuarios.eliminar(tablaUsuarios
									.getSelectedRow());
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

		iniciarComboUsuarios();
		iniciarComboRoles();

		JPanel pnlGrid = new JPanel(new GridLayout(2, 4));
		pnlGrid.add(lblPersona);
		pnlGrid.add(comboPersonas);
		pnlGrid.add(lblRol);
		pnlGrid.add(comboRoles);
		pnlGrid.add(lblUsuario);
		pnlGrid.add(txtUsuario);
		pnlGrid.add(lblContrase);
		pnlGrid.add(txtContrase);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		panel.add(lblBlanco);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(new JScrollPane(tablaUsuarios));
		return panel;
	}

	private void iniciarComboUsuarios() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		comboPersonas = new JComboBox<String>();
		personas = ModeloPersonas.getInstancia().getUsuarios();
		for (Persona usuario : personas) {
			comboPersonas.addItem(usuario.getNombre() + " "
					+ usuario.getApellido());
		}
	}

	private void iniciarComboRoles() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		comboRoles = new JComboBox<String>();
		ArrayList<String> roles = ModeloPersonas.getInstancia().getRoles();
		for (String rol : roles) {
			comboRoles.addItem(rol);
		}
	}
	
	@Override
	public boolean esSalirAlCerrar() {
		return false;
	}
	
	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	public static void main(String[] args) {
		try {
			new VentanaModeloPersonas().setVisible(true);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException
				| SQLException | JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
