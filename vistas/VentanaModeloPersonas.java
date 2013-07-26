package com.efrain.gestorpacientes.vistas;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

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
import javax.swing.FocusManager;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.encriptación.Encriptadora;
import com.efrain.gestorpacientes.entidades.Persona;
import com.efrain.gestorpacientes.modelos.ModeloPersonas;

public class VentanaModeloPersonas extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboPersonas;
	private JComboBox<String> comboRoles;
	private JTable tblUsuarios;
	private JTextField txtUsuario;
	private JTextField txtCorreo;
	private JPasswordField txtContrase;
	private ModeloPersonas modelo;
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
		modelo = ModeloPersonas.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		this.panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Usuarios");
		lblTitulo.setFont(new Font("Monotype Coursiva",
				Font.BOLD + Font.ITALIC, 16));
		JLabel lblPersona = new JLabel("        Persona:");
		JLabel lblRol = new JLabel("    Rol:");
		JLabel lblUsuario = new JLabel("       *Usuario:");
		JLabel lblContrase = new JLabel("   *Contraseña:");
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(70, 10));
		JLabel lblCorreo = new JLabel("Correo Electrónico:");
		JLabel lblBlanco2 = new JLabel();
		lblBlanco2.setPreferredSize(new Dimension(70,20));
		
		txtCorreo = new JTextField(18){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)){
			        Graphics2D g2 = (Graphics2D)g.create();
			        g2.setBackground(Color.gray);
			        g2.setFont(getFont().deriveFont(Font.ITALIC));
			        g2.drawString("ejemplo@[gmail|hotmail].com", 10, 15); 
			        g2.dispose();
			    }
			}
		};
		
		comboRoles = new JComboBox<String>();
		
		txtUsuario = new JTextField(10);
		txtContrase = new JPasswordField();
		txtContrase.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String contrase = new String(txtContrase.getPassword());
				if (!"".equals(txtUsuario.getText()) & !"".equals(contrase)) {
					String persona = comboPersonas.getItemAt(comboPersonas
							.getSelectedIndex());
					String nombre = persona.split(" ")[0];
					String apellido = persona.split(" ")[1];
					String rol = comboRoles.getItemAt(comboRoles
							.getSelectedIndex());
					String usuario = txtUsuario.getText();
					contrase = Encriptadora.encriptar(contrase);
					Persona objeto = new Persona(rol, nombre, apellido,
							usuario, contrase);
					try {
						if("".equals(txtCorreo.getText())) {
						int resp = JOptionPane.showConfirmDialog(null, "ATENCION:Está a punto de registrar un" +
								" usuario\n que no tiene correo, es recomendable que tenga\n correo para posteriormente" +
								" enviarle su contraseña\n en caso de que la olvide. Agregar de todas formas?");
							if(resp == JOptionPane.YES_OPTION)
								modelo.agregar(objeto);
						} else {
							modelo.agregar(objeto);
						}
						
					} catch (ClassNotFoundException | SQLException
							| JDOMException | IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Los campos con \"*\" son obligatorios.");
				}

			}
		});

		tblUsuarios = new JTable(modelo);
		tblUsuarios
				.setPreferredScrollableViewportSize(new Dimension(550, 350));

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String contrase = new String(txtContrase.getPassword());
				if (!"".equals(txtUsuario.getText()) & !"".equals(contrase)) {
					String persona = comboPersonas.getItemAt(comboPersonas
							.getSelectedIndex());
					String nombre = persona.split(" ")[0];
					String apellido = persona.split(" ")[1];
					String rol = comboRoles.getItemAt(comboRoles
							.getSelectedIndex());
					String usuario = txtUsuario.getText();
					contrase = Encriptadora.encriptar(contrase);
					Persona objeto = new Persona(rol, nombre, apellido,
							usuario, contrase);
					try {
						if("".equals(txtCorreo.getText())) {
						int resp = JOptionPane.showConfirmDialog(null, "ATENCION:Está a punto de registrar un" +
								" usuario\n que no tiene correo, es recomendable que tenga\n correo para posteriormente" +
								" enviarle su contraseña\n en caso de que la olvide. Agregar de todas formas?");
							if(resp == JOptionPane.YES_OPTION)
								modelo.agregar(objeto);
						} else {
							modelo.agregar(objeto);
						}
						
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
				if (tblUsuarios.getSelectedRow() != -1) {
					try {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"Está seguro que desea eliminar este usuario?\n\nNOTA: Se eliminará permanentemente.");
						if (respuesta == JOptionPane.YES_OPTION) {
							modelo.eliminar(tblUsuarios
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
		
		comboPersonas = getComboUsuarios();
		comboPersonas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					comboPersonas = getComboUsuarios();
				} catch (ClassNotFoundException | SQLException | JDOMException
						| IOException e) {
					e.printStackTrace();
				}
			}
		});
		comboRoles = getComboRoles();
		comboRoles.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					comboRoles = getComboRoles();
				} catch (ClassNotFoundException | SQLException | JDOMException
						| IOException e) {
					e.printStackTrace();
				}
			}
		});

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
		panel.add(lblCorreo);
		panel.add(txtCorreo);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(lblBlanco2);
		panel.add(new JScrollPane(tblUsuarios));
		return panel;
	}

	public JComboBox<String> getComboUsuarios() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		return modelo.getPersonas();
	}

	public JComboBox<String> getComboRoles() throws ClassNotFoundException,
			SQLException, JDOMException, IOException {
		return modelo.getRoles();
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
			e.printStackTrace();
		}
	}

}
