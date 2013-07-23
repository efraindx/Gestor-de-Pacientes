package com.efrain.gestorpacientes.vistas;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

public class VentanaMedico extends Ventana {

	private static final long serialVersionUID = 1L;
	private static VentanaMedico instancia;
	 
	public static synchronized VentanaMedico getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
	    return instancia == null ? instancia = new VentanaMedico() : instancia;
	}
	
	public VentanaMedico() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		this.anchura = 650;
		this.altura = 500;
		this.titulo = "Medico";
		
		prepararVentana(titulo, anchura, altura, null);
		
	}

	@Override
	public JPanel getContenido() {
		this.panel = new JPanel(new BorderLayout());
		JLabel lblTitulo = new JLabel(new ImageIcon(getClass().getResource("/com/efrain/gestorpacientes/imágenes/titulo.png")));
		JPanel pnlCentral = new JPanel(new FlowLayout());
		pnlCentral.add(lblTitulo);
		panel.add(BorderLayout.NORTH, pnlCentral);
		
		JLabel lblCentral = new JLabel(new ImageIcon(getClass().getResource("/com/efrain/gestorpacientes/imágenes/fondo.png")));
		
		panel.add(BorderLayout.CENTER, lblCentral);
		
		JToolBar barraIzquierda = new JToolBar();
		barraIzquierda.setFloatable(false);
		barraIzquierda.setCursor(new Cursor(Cursor.HAND_CURSOR));
	
		JToolBar barraDerecha = new JToolBar();
		barraDerecha.setFloatable(false);
		
		JPanel pnlIz = new JPanel(new GridLayout(6, 1));
		JPanel pnlDerecha = new JPanel(new GridLayout(6,1));
		
		JButton btnMPacientes = new JButton("Man. de Pacientes");
		JButton btnMCitas = new JButton("Man. de Citas");
		JButton btnMPadecimientos = new JButton("Man. de Padecimientos");
		JButton btnMRecetas = new JButton("Man. de Recetas");
		JButton btnMPLab = new JButton("Man. de Pruebas de Laboratorio");
		JButton btnMResultados = new JButton("Man. Resultados de Pruebas");
		JButton btnMUsuarios = new JButton("Man. de Usuarios");
		btnMUsuarios.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					VentanaModeloPersonas.getInstancia().setVisible(true);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException | SQLException
						| JDOMException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		JButton btnMMedicos = new JButton("Man. de Médicos");
		JButton btnMEspecialidades = new JButton("Man. de Especialidades");
		JButton btnMAsistentes = new JButton("Man. de Asistentes");
		JButton btnLMedicos = new JButton("Listado de Médicos");
		JButton btnBPacientes = new JButton("Buscar Pacientes");
		JButton btnHPaciente = new JButton("Ver Historial Paciente");
		JButton bnImprimir = new JButton("Imprimir Receta");
		JButton btnEstadisticas = new JButton("Estadísticas");
	
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
		barraIzquierda.add(pnlIz);
		
		panel.add(BorderLayout.WEST, barraIzquierda);
		panel.add(BorderLayout.EAST, barraDerecha);
		return panel;
	}
	
	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		new VentanaMedico().setVisible(true);
	}

}
