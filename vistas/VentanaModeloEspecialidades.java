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


import edu.itla.gestorpacientes.entidades.Especialidad;
import edu.itla.gestorpacientes.modelos.ModeloEspecialidades;

public class VentanaModeloEspecialidades extends Ventana {

	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTable tblEspecialidades;
	private static final long serialVersionUID = 1L;
	private ModeloEspecialidades modelo;
	private static VentanaModeloEspecialidades instancia;

	public static synchronized VentanaModeloEspecialidades getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloEspecialidades()
				: instancia;
	}

	public VentanaModeloEspecialidades() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Especialidades";
		this.anchura = 500;
		this.altura = 500;
		modelo = ModeloEspecialidades.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());
		tblEspecialidades = new JTable(ModeloEspecialidades.getInstancia());
		tblEspecialidades.setPreferredScrollableViewportSize(new Dimension(450,
				300));

		txtCodigo = new JTextField(10);
		txtNombre = new JTextField(10);

		JLabel lblTitulo = new JLabel("Mantenimiento de Especialidades");
		lblTitulo.setPreferredSize(new Dimension(380, 50));
		JLabel lblCodigo = new JLabel("Código:");
		JLabel lblNombre = new JLabel("Nombre:");

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(txtCodigo.getText())
						&& !"".equals(txtNombre.getText())) {
					Especialidad especialidad = new Especialidad(txtCodigo
							.getText(), txtNombre.getText());
					modelo.agregar(especialidad);
				} else {
					JOptionPane.showMessageDialog(null,
							"Todos los campos son obligatorios.");
				}
				txtCodigo.setText("");
				txtNombre.setText("");
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblEspecialidades.getSelectedRow() != -1) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Está seguro que desea eliminar esta especialidad?\n\nNOTA: Se eliminará permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblEspecialidades.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar una especialidad.");
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
		panel.add(new JScrollPane(tblEspecialidades));
		return panel;
	}

	@Override
	public boolean esDisponibleCambiarTamaño() {
		return false;
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		new VentanaModeloEspecialidades().setVisible(true);
	}
}
