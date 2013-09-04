package edu.itla.gestorpacientes.vistas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;



import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.Resultado_Prueba_Laboratorio;
import edu.itla.gestorpacientes.modelos.ModeloResultados_Prueba_Laboratorio;

public class VentanaModeloResultados_Prueba_Laboratorio extends Ventana {

	private static final long serialVersionUID = 1L;
	private JComboBox<Paciente> cmbPacientes;
	private JComboBox<String> cmbResultadosPruebas_Laboratorio;
	private JTextArea txtResultado;
	private ModeloResultados_Prueba_Laboratorio modelo;
	private JTable tblResultados;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnCambios;
	private Resultado_Prueba_Laboratorio resultadoActual;

	public VentanaModeloResultados_Prueba_Laboratorio() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		this.titulo = "";
		this.anchura = 505;
		this.altura = 500;
		modelo = ModeloResultados_Prueba_Laboratorio.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {

		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Resultados de Prueba de Laboratorio");
		lblTitulo.setPreferredSize(new Dimension(355, 100));
		JLabel lblPrueba = new JLabel("Prueba de Laboratorio:");
		JLabel lblPaciente = new JLabel("Paciente:");
		JLabel lblResultado = new JLabel("Resultados:");
		JLabel lblBlanco = new JLabel();
		JLabel lblBlanco2 = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(57, 50));
		lblBlanco2.setPreferredSize(new Dimension(160, 50));

		tblResultados = new JTable(ModeloResultados_Prueba_Laboratorio.getInstancia());
		tblResultados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resultadoActual = modelo.getResultados().get(
						tblResultados.getSelectedRow());
				cmbPacientes.setSelectedIndex(resultadoActual.getIdPaciente());
				cmbResultadosPruebas_Laboratorio.setSelectedIndex(resultadoActual.getIdPrueba());
				txtResultado.setText(resultadoActual.getResultado());
				btnCambios.setEnabled(true);
				btnEliminar.setEnabled(true);
				btnAgregar.setEnabled(false);
			}
		});

		btnAgregar = new JButton("Agregar Resultado de Prueba");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!"".equals(txtResultado.getText())
						&& cmbPacientes.getSelectedIndex() != 0
						&& cmbResultadosPruebas_Laboratorio.getSelectedIndex() != 0) {

					int paciente = cmbPacientes.getSelectedIndex();
					int prueba = cmbResultadosPruebas_Laboratorio.getSelectedIndex();
					String resultado = txtResultado.getText();

					Resultado_Prueba_Laboratorio result = new Resultado_Prueba_Laboratorio(prueba, paciente,
							resultado );
					try {
						modelo.agregar(result);
						cmbPacientes.setSelectedIndex(0);
						cmbResultadosPruebas_Laboratorio.setSelectedIndex(0);
						txtResultado.setText("");
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

		btnEliminar = new JButton("Eliminar Resultado de Prueba");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblResultados.getSelectedRow() != -1) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Está seguro que desea eliminar este resultado de prueba?\n\nNOTA: Se eliminará permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblResultados.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar una receta.");
				}
			}
		});

		btnCambios = new JButton("Guardar Cambios");
		btnCambios.setEnabled(false);
		btnCambios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resultadoActual = modelo.getResultados().get(
						tblResultados.getSelectedRow());
				if (!"".equals(txtResultado.getText())
						&& cmbPacientes.getSelectedIndex() != 0
						&& cmbResultadosPruebas_Laboratorio.getSelectedIndex() != 0) {
					try {
						int id = resultadoActual.getId();
						int fila = tblResultados.getSelectedRow();
						
						int paciente = cmbPacientes.getSelectedIndex();
						modelo.modificar(id, 1, fila, paciente);
						int prueba = cmbResultadosPruebas_Laboratorio.getSelectedIndex();
						modelo.modificar(id, 2, fila, prueba);
						String  resultado = txtResultado.getText();
						modelo.modificar(id, 3, fila, resultado);
					
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
				btnCambios.setEnabled(false);
				btnAgregar.setEnabled(true);
				btnEliminar.setEnabled(false);
				tblResultados.clearSelection();
				cmbPacientes.setSelectedIndex(0);
				cmbResultadosPruebas_Laboratorio.setSelectedIndex(0);
				txtResultado.setText("");
			}
		});

		cmbPacientes = modelo.getPacientes();
		cmbResultadosPruebas_Laboratorio = modelo.getResultados_Pruebas_Laboratorio();

		txtResultado = new JTextArea(5, 20);

		JPanel pnlGrid = new JPanel(new GridLayout(1, 4));
		pnlGrid.add(lblPrueba);
		pnlGrid.add(cmbResultadosPruebas_Laboratorio);
		pnlGrid.add(lblPaciente);
		pnlGrid.add(cmbPacientes);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		panel.add(lblBlanco);
		panel.add(lblResultado);
		panel.add(new JScrollPane(txtResultado));
		panel.add(lblBlanco2);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(btnCambios);
		panel.add(new JScrollPane(tblResultados));

		return panel;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, SQLException, JDOMException,
			IOException {
		new VentanaModeloResultados_Prueba_Laboratorio().setVisible(true);
	}

}