package vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;

	public class VentanaLogin extends Ventana {
		
		private static final long serialVersionUID = 1L;
		private static VentanaLogin instancia;
		
		public static synchronized VentanaLogin getInstancia() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
			return instancia == null ? instancia = new VentanaLogin() : instancia;
		}
		
		public VentanaLogin() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
			this.icono = "imágenes/iconoLogin.png";
			this.anchura = 540;
			this.altura = 300;
			this.titulo = "Bievenid@";
			prepararVentana(titulo, anchura, altura, icono);
		}
		
		@Override
		public JPanel getContenido() {
			
			JPanel pnlPrincipal = new JPanel();
			pnlPrincipal.setLayout(new FlowLayout());
			pnlPrincipal.setSize(anchura, altura);
			JPanel pnlGrid = new JPanel(new GridLayout(3, 2));
			JPanel pnlInferior = new JPanel(new FlowLayout());
			pnlInferior.setPreferredSize(new Dimension(400, 50));
			JPanel pnlPrueba = new JPanel(new FlowLayout());
			pnlPrueba.setPreferredSize(new Dimension(400, 25));
			
			JLabel lblTitulo = new JLabel(new ImageIcon("imágenes/login.png"));
			JLabel lblUsuario = new JLabel("Usuario:");
			JLabel lblContraseña = new JLabel("Contraseña:");
			
			JButton botonIniciar = new JButton("Iniciar Sesión");
			JButton botonCancelar = new JButton("Cancelar");
			
			JTextField txtUsuario = new JTextField(10);
			JTextField txtContraseña = new JTextField(10);
			JTextField enlace = new JTextField("Olvidaste la contraseña?");
			enlace.setEditable(false);
			enlace.setBorder(new MatteBorder(0,0,1,0,new Color(0,0,0,0)));
			enlace.setForeground(Color.blue);
			enlace.setBackground(new Color(0,0,0,0));
			enlace.setCursor( new Cursor(Cursor.HAND_CURSOR) );
			enlace.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					JOptionPane.showMessageDialog(null, "ASDgasd");
				}
			});
			
			pnlInferior.add(botonIniciar);
			pnlInferior.add(botonCancelar);
		
		    pnlGrid.add(lblUsuario);
		    pnlGrid.add(txtUsuario);
		    pnlGrid.add(lblContraseña);
		    pnlGrid.add(txtContraseña);
		    
		    pnlPrincipal.add(lblTitulo);
		    pnlPrincipal.add(pnlPrueba);
			pnlPrincipal.add(pnlGrid);
			pnlPrincipal.add(pnlInferior);
			pnlPrincipal.add(enlace);
			pnlPrincipal.setPreferredSize(new Dimension(anchura, altura));
			return pnlPrincipal;
		}
		
		@Override
		public boolean esDisponibleCambiarTamaño() {
			return true;
		}
	}
