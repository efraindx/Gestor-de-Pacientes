package com.efrain.gestorpacientes.vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
			this.titulo = "Bievenid@";
			prepararVentana(titulo, anchura, altura, icono);
		}
		
		@Override
		public JPanel getContenido() {
			
			JPanel pnlPrincipal = new JPanel();
			pnlPrincipal.setLayout(new FlowLayout());
			pnlPrincipal.setSize(anchura, altura);
			
			JLabel lblTitulo = new JLabel(new ImageIcon("imágenes/login.png"));
			JLabel lblUsuario = new JLabel("Usuario:");
			JLabel lblContraseña = new JLabel("Contraseña:");
			JLabel lblInter = new JLabel();
			lblInter.setPreferredSize(new Dimension(300, 15));
			
			JButton botonIniciar = new JButton("Iniciar Sesión");
			JButton botonCancelar = new JButton("Cancelar");
			
			JTextField txtUsuario = new JTextField(10);
			JTextField txtContraseña = new JTextField(10);
			JTextField enlace = new JTextField("Olvidaste la contraseña?");
			enlace.setBackground(this.getBackground());
			enlace.setCaretColor(new Color(0,128,0));
			
			JPanel pnlGrid = new JPanel(new GridLayout(3, 2));
			//pnlGrid.setPreferredSize(new Dimension(430,80));
		
		    pnlGrid.add(lblUsuario);
		    pnlGrid.add(txtUsuario);
		    pnlGrid.add(lblContraseña);
		    pnlGrid.add(txtContraseña);
		    
		    pnlPrincipal.add(lblTitulo);
			pnlPrincipal.add(pnlGrid);
			pnlPrincipal.add(lblInter);
			pnlPrincipal.add(botonIniciar);
			pnlPrincipal.add(botonCancelar);
			pnlPrincipal.add(enlace);
			pnlPrincipal.setPreferredSize(new Dimension(anchura, altura));
			return pnlPrincipal;
		}
		
		@Override
		public boolean esDisponibleCambiarTamaño() {
			return true;
		}
		
		public static void main(String[] args) {
				try {
					VentanaLogin.getInstancia().setVisible(true);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
		}
	}
