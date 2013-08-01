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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.entidades.Cita;
import edu.itla.gestorpacientes.modelos.ModeloCitas;

public class VentanaModeloCitas extends Ventana {

	private JComboBox<String> cmbPacientes;
	private JComboBox<String> cmbMedicos;
	private JComboBox<String> cmbDia;
	private JComboBox<String> cmbMes;
	private JComboBox<String> cmbA�o;
	private JComboBox<String> cmbAP;
	private String[] meses = { "Mes", "Enero", "Febrero", "Marzo", "Abril",
			"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
			"Noviembre", "Diciembre" };
	private ModeloCitas modelo;
	private JTextField txtHora;
	private JTextField txtMinutos;
	private JTextArea txtCausa;
	private JTable tblCitas;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnCambios;
	private Cita citaActual;

	private static final long serialVersionUID = 1L;

	public VentanaModeloCitas() throws ClassNotFoundException, SQLException,
			JDOMException, IOException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		this.titulo = "";
		this.anchura = 500;
		this.altura = 550;
		modelo = ModeloCitas.getInstancia();
		prepararVentana(titulo, anchura, altura, null);
	}

	@Override
	public JPanel getContenido() {
		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Citas");
		lblTitulo.setPreferredSize(new Dimension(170, 100));
		JLabel lblPaciente = new JLabel("   Paciente:");
		JLabel lblMedico = new JLabel("   Medico:");
		JLabel lblFecha = new JLabel("   Fecha:");
		JLabel lblHora = new JLabel("Hora:");
		JLabel lblCausa = new JLabel("Causa:");
		JLabel lblInter = new JLabel(":");
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(150, 50));
		JLabel lblBlanco2 = new JLabel();
		lblBlanco2.setPreferredSize(new Dimension(100, 50));

		btnAgregar = new JButton("Agregar Cita");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtHora.getText())
						&& !"".equals(txtMinutos.getText())
						&& cmbPacientes.getSelectedIndex() != 0
						&& cmbMedicos.getSelectedIndex() != 0
						&& cmbDia.getSelectedIndex() != 0
						&& cmbMes.getSelectedIndex() != 0
						&& cmbA�o.getSelectedIndex() != 0
						&& !"".equals(txtCausa.getText())) {

					int idPaciente = cmbPacientes.getSelectedIndex();
					String medico = (String) cmbMedicos.getSelectedItem();
					String dia = (String) cmbDia.getSelectedItem();
					int mes = (int) cmbMes.getSelectedIndex();
					String a�o = (String) cmbA�o.getSelectedItem();
					String fecha = dia + "/" + mes + "/" + a�o;
					String h = txtHora.getText();
					String m = txtMinutos.getText();
					String t = (String) cmbAP.getSelectedItem();
					String hora = h + ":" + m + " " + t;
					String causa = txtCausa.getText();

					Cita cita = new Cita(idPaciente, medico, fecha, hora, causa);
					try {
						modelo.agregar(cita);
						cmbPacientes.setSelectedIndex(0);
						cmbMedicos.setSelectedIndex(0);
						cmbDia.setSelectedIndex(0);
						cmbMes.setSelectedIndex(0);
						cmbA�o.setSelectedIndex(0);
						txtHora.setText("");
						txtMinutos.setText("");
						cmbAP.setSelectedIndex(0);
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

		btnEliminar = new JButton("Eliminar Cita");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tblCitas.getSelectedRow() != -1) {
					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Est� seguro que desea eliminar esta cita?\n\nNOTA: Se eliminar� permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblCitas.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar una cita.");
				}
				cmbAP.setSelectedIndex(0);
				cmbA�o.setSelectedIndex(0);
				cmbDia.setSelectedIndex(0);
				cmbMedicos.setSelectedIndex(0);
				cmbMes.setSelectedIndex(0);
				txtCausa.setText("");
				txtMinutos.setText("");
				txtHora.setText("");
				
			}
		});

		btnCambios = new JButton("Guardar Cambios");
		btnCambios.setEnabled(false);
		btnCambios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				citaActual = modelo.getCitas().get(tblCitas.getSelectedRow());
				if (!"".equals(txtHora.getText())
						&& !"".equals(txtMinutos.getText())
						&& cmbPacientes.getSelectedIndex() != 0
						&& cmbMedicos.getSelectedIndex() != 0
						&& cmbDia.getSelectedIndex() != 0
						&& cmbMes.getSelectedIndex() != 0
						&& cmbA�o.getSelectedIndex() != 0
						&& !"".equals(txtCausa.getText())) {

					int id = citaActual.getId();
					int fila = tblCitas.getSelectedRow();

					int idPaciente = cmbPacientes.getSelectedIndex();
					try {
						modelo.modificar(id, 1, fila, idPaciente);
						String medico = (String) cmbMedicos.getSelectedItem();
						modelo.modificar(id, 2, fila, medico);
						String dia = (String) cmbDia.getSelectedItem();
						int mes = (int) cmbMes.getSelectedIndex();
						String a�o = (String) cmbA�o.getSelectedItem();
						String fecha = dia + "/" + mes + "/" + a�o;
						modelo.modificar(idPaciente, 3, fila, fecha);
						String h = txtHora.getText();
						String m = txtMinutos.getText();
						String t = (String) cmbAP.getSelectedItem();
						String hora = h + ":" + m + " " + t;
						modelo.modificar(idPaciente, 4, fila, hora);
						String causa = txtCausa.getText();
						modelo.modificar(idPaciente, 5, fila, causa);

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
				tblCitas.clearSelection();
				txtHora.setText("");
				txtMinutos.setText("");
				cmbA�o.setSelectedIndex(0);
				cmbMes.setSelectedIndex(0);
				cmbDia.setSelectedIndex(0);
				cmbPacientes.setSelectedIndex(0);
				cmbMedicos.setSelectedIndex(0);
				cmbAP.setSelectedIndex(0);
				txtCausa.setText("");
			}
		});

		try {
			tblCitas = new JTable(ModeloCitas.getInstancia());
			tblCitas.setPreferredScrollableViewportSize(new Dimension(450, 180));
			tblCitas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					citaActual = modelo.getCitas().get(
							tblCitas.getSelectedRow());
					btnCambios.setEnabled(true);
					btnEliminar.setEnabled(true);
					btnAgregar.setEnabled(false);
					cmbPacientes.setSelectedIndex(citaActual.getIdPaciente());
					cmbMedicos.setSelectedItem(citaActual.getMedico());
					String[] f = citaActual.getFecha().split("/");
					cmbDia.setSelectedItem(f[0]);
					cmbMes.setSelectedIndex(Integer.parseInt(f[1]));
					cmbA�o.setSelectedItem(f[2]);
					String[] h = citaActual.getHora().split(":");
					txtHora.setText(h[0]);
					txtMinutos.setText(h[1].substring(0, 2));
					cmbAP.setSelectedItem(h[1].substring(3, 5));
					txtCausa.setText(citaActual.getCausa());
				}
			});
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		cmbDia = getComboDia();
		cmbMes = new JComboBox<String>(meses);
		cmbA�o = getcomboA�o();
		try {
			cmbPacientes = modelo.getPacientes();
			cmbMedicos = modelo.getMedicos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cmbAP = new JComboBox<String>();
		cmbAP.addItem("AM");
		cmbAP.addItem("PM");

		txtHora = new JTextField(2);
		txtMinutos = new JTextField(2);
		txtCausa = new JTextArea(5, 15);

		JPanel pnlGrid = new JPanel(new GridLayout(2, 4));
		pnlGrid.add(lblPaciente);
		pnlGrid.add(cmbPacientes);
		pnlGrid.add(lblMedico);
		pnlGrid.add(cmbMedicos);
		pnlGrid.add(lblFecha);
		pnlGrid.add(cmbDia);
		pnlGrid.add(cmbMes);
		pnlGrid.add(cmbA�o);

		panel.add(lblTitulo);
		panel.add(pnlGrid);
		panel.add(lblBlanco);
		panel.add(lblHora);
		panel.add(txtHora);
		panel.add(lblInter);
		panel.add(txtMinutos);
		panel.add(cmbAP);
		panel.add(lblCausa);
		panel.add(new JScrollPane(txtCausa));
		panel.add(lblBlanco2);
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(btnCambios);
		panel.add(new JScrollPane(tblCitas));
		return panel;
	}

	private JComboBox<String> getComboDia() {
		cmbDia = new JComboBox<String>();
		cmbDia.addItem("Dia");
		for (int i = 1; i <= 31; i++) {
			cmbDia.addItem(Integer.toString(i));
		}
		return cmbDia;
	}

	private JComboBox<String> getcomboA�o() {
		cmbA�o = new JComboBox<String>();
		cmbA�o.addItem("A�o");
		for (int i = 2013; i >= 1990; i--) {
			cmbA�o.addItem(Integer.toString(i));
		}
		return cmbA�o;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			JDOMException, IOException, UnsupportedLookAndFeelException {
		new VentanaModeloCitas().setVisible(true);
	}

}
