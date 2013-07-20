package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.modelos.ModeloPersonas;
import com.efrain.gestorpacientes.entidades.Persona;

public class VentanaModeloPersonas extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboPersonas;

	public VentanaModeloPersonas() {
		this.titulo = "Usuarios";
		this.anchura = 550;
		this.altura = 550;
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		this.panel = new JPanel(new FlowLayout());
		
		JLabel lblTitulo = new JLabel("Mantenimiento de Usuario");
		JLabel lblNombre = new JLabel("Persona:");
		JLabel lblApellido = new JLabel("Rol:");
		JLabel lblUsuario = new JLabel("Usuario:");
		JLabel lblContrase = new JLabel("Contraseña:");
		
		JTextField txtUsuario = new JTextField(10);
		JPasswordField txtContrase = new JPasswordField();
		iniciarComboPersonas();
		
		return panel;
	}
	
	private void iniciarComboPersonas() throws ClassNotFoundException, SQLException, JDOMException, IOException {
		comboPersonas = new JComboBox<String>();
		ArrayList<Persona> personas = (ArrayList<Persona>)ModeloPersonas.getInstancia().getDatos();
		for(Persona persona: personas) {
			comboPersonas.addItem(persona.getNombre() + " " + persona.getApellido());
		}
	}
	
	public static void main(String[] args) {
		new VentanaModeloPersonas().setVisible(true);
	}

}
