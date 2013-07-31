package com.efrain.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import com.efrain.gestorpacientes.entidades.Prueba_Laboratorio;
import com.efrain.gestorpacientes.modelos.ModeloPruebas_Laboratorio;

public class VentanaModeloPrueba_Laboratorio extends Ventana {

	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTable tblPruebas;
	private static final long serialVersionUID = 1L;
	private ModeloPruebas_Laboratorio modelo;
	private static VentanaModeloPrueba_Laboratorio instancia;

	public static synchronized VentanaModeloPrueba_Laboratorio getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloPrueba_Laboratorio()
				: instancia;
	}

	public VentanaModeloPrueba_Laboratorio() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Pruebas";
		this.anchura = 500;
		this.altura = 500;
		modelo = ModeloPruebas_Laboratorio.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());
		tblPruebas = new JTable(ModeloPruebas_Laboratorio.getInstancia());
		tblPruebas.setPreferredScrollableViewportSize(new Dimension(450, 300));

		txtCodigo = new JTextField(10);
		txtNombre = new JTextField(10);

		JLabel lblTitulo = new JLabel("Mantenimiento de Pruebas de Laboratorio");
		lblTitulo.setPreferredSize(new Dimension(380, 50));
		JLabel lblCodigo = new JLabel("Código:");
		JLabel lblNombre = new JLabel("Nombre:");

		JButton btnAgregar = new JButton("Agregar Prueba");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(txtCodigo.getText())
						&& !"".equals(txtNombre.getText())) {
					Prueba_Laboratorio prueba = new Prueba_Laboratorio(txtCodigo
							.getText(), txtNombre.getText());
					try {
						modelo.agregar(prueba);
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
							"Todos los campos son obligatorios.");
				}
				txtCodigo.setText("");
				txtNombre.setText("");
			}
		});

		JButton btnEliminar = new JButton("Eliminar Prueba");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblPruebas.getSelectedRow() != -1) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Está seguro que desea eliminar esta prueba?\n\nNOTA: Se eliminará permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblPruebas.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar una prueba.");
				}
			}
		});

		JPanel pnlGrid = new JPanel(new GridLayout(3, 2));
		pnlGrid.add(lblCodigo);
		pnlGrid.add(txtCodigo);
		pnlGrid.add(lblNombre);
		pnlGrid.add(txtNombre);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(new JScrollPane(tblPruebas));
		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		new VentanaModeloPrueba_Laboratorio().setVisible(true);
	}
}
