package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Prueba_Laboratorio;
import edu.itla.gestorpacientes.modelos.ModeloPruebas_Laboratorio;

public class VentanaModeloPruebas_Laboratorio extends Ventana {

	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTable tblPruebas;
	private static final long serialVersionUID = 1L;
	private ModeloPruebas_Laboratorio modelo;
	private static VentanaModeloPruebas_Laboratorio instancia;
	private JButton btnEliminar;

	public static synchronized VentanaModeloPruebas_Laboratorio getInstancia()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException, JDOMException, IOException {
		return instancia == null ? instancia = new VentanaModeloPruebas_Laboratorio()
				: instancia;
	}

	private VentanaModeloPruebas_Laboratorio() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "Pruebas de Laboratorio";
		this.anchura = 500;
		this.altura = 400;
		this.icono = "/edu/itla/gestorpacientes/imágenes/prueb.png";
		modelo = ModeloPruebas_Laboratorio.getInstancia();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());
		tblPruebas = new JTable(ModeloPruebas_Laboratorio.getInstancia());
		tblPruebas.setPreferredScrollableViewportSize(new Dimension(450, 190));
		tblPruebas.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnEliminar.setEnabled(true);
			}
		});

		txtCodigo = new JTextField(10);
		txtNombre = new JTextField(10);

		JLabel lblTitulo = new JLabel("Mantenimiento de Pruebas de Laboratorio", new ImageIcon(getClass().getResource(
				"/edu/itla/gestorpacientes/imágenes/prueb.png")), 0);
		lblTitulo.setFont(new Font("Georgia", Font.BOLD + Font.ITALIC, 17));
		lblTitulo.setPreferredSize(new Dimension(460, 50));
		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(new Font("Georgia", Font.ITALIC, 15));
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Georgia", Font.ITALIC, 15));

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

		btnEliminar = new JButton("Eliminar Prueba");
		btnEliminar.setEnabled(false);
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
							btnEliminar.setEnabled(false);
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
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, JDOMException, IOException {
		new VentanaModeloPruebas_Laboratorio().setVisible(true);
	}
}
