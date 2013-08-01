package edu.itla.gestorpacientes.vistas;

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


import edu.itla.gestorpacientes.entidades.Padecimiento;
import edu.itla.gestorpacientes.modelos.ModeloPadecimientos;

public class VentanaModeloPadecimientos extends Ventana {

	private ModeloPadecimientos modelo;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTable tblPadecimientos;

	private static final long serialVersionUID = 1L;

	public VentanaModeloPadecimientos() throws ClassNotFoundException,
			SQLException, JDOMException, IOException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		this.titulo = "";
		this.anchura = 500;
		this.altura = 420;
		modelo = ModeloPadecimientos.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() {
		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Padecimientos");
		lblTitulo.setPreferredSize(new Dimension(350, 50));
		JLabel lblCodigo = new JLabel("Codigo:");
		JLabel lblNombre = new JLabel("Nombre:");
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(150, 50));

		txtCodigo = new JTextField(10);
		txtNombre = new JTextField(10);

		try {
			tblPadecimientos = new JTable(ModeloPadecimientos.getInstancia());
			tblPadecimientos.setPreferredScrollableViewportSize(new Dimension(
					450, 180));
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (JDOMException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		JButton btnAgregar = new JButton("Agregar Padecimiento");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(txtCodigo.getText())
						&& !"".equals(txtNombre.getText())) {

					String codigo = txtCodigo.getText();
					String nombre = txtNombre.getText();

					Padecimiento padecimiento = new Padecimiento(codigo, nombre);
					try {
						modelo.agregar(padecimiento);
						txtCodigo.setText("");
						txtNombre.setText("");
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
			}
		});

		JButton btnEliminar = new JButton("Eliminar Padecimiento");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tblPadecimientos.getSelectedRow() != -1) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Está seguro que desea eliminar este padecimiento?\n\nNOTA: Se eliminará permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblPadecimientos.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar un padecimiento.");
				}
			}
		});

		JPanel pnlGrid = new JPanel(new GridLayout(2, 2));
		pnlGrid.add(lblCodigo);
		pnlGrid.add(txtCodigo);
		pnlGrid.add(lblNombre);
		pnlGrid.add(txtNombre);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		panel.add(lblBlanco);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(new JScrollPane(tblPadecimientos));
		return panel;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			JDOMException, IOException, UnsupportedLookAndFeelException {
		new VentanaModeloPadecimientos().setVisible(true);
	}
}