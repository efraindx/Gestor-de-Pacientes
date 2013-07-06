package com.efrain.gestorpacientes.vistas;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

	public class VentanaLogin extends Ventana {
		
		private static final long serialVersionUID = 1L;
		private static VentanaLogin instancia;
		
		public static synchronized VentanaLogin getInstancia() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
			return instancia == null ? instancia = new VentanaLogin() : instancia;
		}
		
		public VentanaLogin() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
			this.icono = "imágenes/iconoLogin.png";
			this.anchura = 550;
			this.altura = 300;
			prepararVentana("Bievenid@", anchura, altura, icono);
		}
		
		@Override
		public JFrame getContenido() {
			
			JFrame pnlPrincipal = new JFrame();
			pnlPrincipal.setLayout(new FlowLayout());
			//pnlPrincipal.setBackground(Color.gray);
			pnlPrincipal.setSize(anchura, altura);
			
			JLabel lblTitulo = new JLabel(new ImageIcon("imágenes/login.png"));
			JLabel lblUsuario = new JLabel("Usuario:");
			JLabel lblContraseña = new JLabel("Contraseña:");
			
			JTextField txtUsuario = new JTextField(10);
			JTextField txtContraseña = new JTextField(10);
			
			JPanel pnlGrid = new JPanel(new GridLayout(2, 2));
		    pnlGrid.add(lblUsuario);
		    pnlGrid.add(txtUsuario);
		    pnlGrid.add(lblContraseña);
		    pnlGrid.add(txtContraseña);
		
		    pnlPrincipal.add(lblTitulo);
			pnlPrincipal.add(pnlGrid);
			
			return pnlPrincipal;
		}
		
		@Override
		public boolean esDisponibleCambiarTamaño() {
			return true;
		}
		
		public static void main(String[] args) {
			try {
				VentanaLogin.getInstancia().setVisible(true);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
