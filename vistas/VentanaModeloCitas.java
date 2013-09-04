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
import edu.itla.gestorpacientes.entidades.Medico;
import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.modelos.ModeloCitas;

public class VentanaModeloCitas extends Ventana {

	private JComboBox<Paciente> cmbPacientes;
	private JComboBox<Medico> cmbMedicos;
	private JComboBox<String> cmbDia;
	private JComboBox<String> cmbMes;
	private JComboBox<String> cmbAño;
	private JComboBox<String> cmbAP;
	private String[] meses = { "Mes", "Enero", "Febrero", "Marzo", "Abril",
			"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
			"Noviembre", "Diciembre" };
	private ModeloCitas modelo;
	private JTextField txtHora;
	private JTextField txtMinutos;
	private JTextArea txtCausa;
	private JTextArea txtObs;
	private JTable tblCitas;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnCambios;
	private Cita citaActual;

	private static final long serialVersionUID = 1L;

	public VentanaModeloCitas() throws ClassNotFoundException, SQLException,
			JDOMException, IOException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		this.titulo = "Citas";
		this.anchura = 590;
		this.altura = 540;
		this.icono = "/edu/itla/gestorpacientes/imágenes/citas.png";
		modelo = ModeloCitas.getInstancia();
		prepararVentana(titulo, anchura, altura, icono);
	}

	@Override
	public JPanel getContenido() {
		panel = new JPanel(new FlowLayout());

		JLabel lblTitulo = new JLabel("Mantenimiento de Citas", new ImageIcon(
				getClass().getResource(
						"/edu/itla/gestorpacientes/imágenes/citas.png")), 0);
		lblTitulo.setFont(new Font("Lucida Fax", Font.ITALIC + Font.BOLD, 14));
		lblTitulo.setPreferredSize(new Dimension(250, 100));
		JLabel lblPaciente = new JLabel("   Paciente:");
		lblPaciente.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		JLabel lblMedico = new JLabel("   Médico:");
		lblMedico.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		JLabel lblFecha = new JLabel("   Fecha:");
		lblFecha.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		JLabel lblHora = new JLabel("Hora:");
		lblHora.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		JLabel lblCausa = new JLabel("Causa:");
		lblCausa.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		JLabel lblInter = new JLabel(":");
		JLabel lblObs = new JLabel("Obs:");
		lblObs.setFont(new Font("Lucida Fax", Font.BOLD, 12));
		JLabel lblBlanco2 = new JLabel();
		lblBlanco2.setPreferredSize(new Dimension(10, 50));
		JLabel lblBlanco = new JLabel();
		lblBlanco.setPreferredSize(new Dimension(5, 50));

		btnAgregar = new JButton("Agregar Cita");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!"".equals(txtHora.getText())
						&& !"".equals(txtMinutos.getText())
						&& cmbMedicos.getSelectedIndex() != 0
						&& cmbDia.getSelectedIndex() != 0
						&& cmbMes.getSelectedIndex() != 0
						&& cmbAño.getSelectedIndex() != 0
						&& !"".equals(txtCausa.getText())
						&& !"".equals(txtObs.getText())) {

					Paciente paciente = cmbPacientes.getItemAt(cmbPacientes
							.getSelectedIndex());
					Medico medico = cmbMedicos.getItemAt(cmbMedicos
							.getSelectedIndex());
					String dia = (String) cmbDia.getSelectedItem();
					int mes = (int) cmbMes.getSelectedIndex();
					String año = (String) cmbAño.getSelectedItem();
					String fecha = dia + "/" + mes + "/" + año;
					String h = txtHora.getText();
					String m = txtMinutos.getText();
					String t = (String) cmbAP.getSelectedItem();
					String hora = h + ":" + m + " " + t;
					String causa = txtCausa.getText();
					String obs = txtObs.getText();

					Cita cita = new Cita(paciente, medico, fecha, hora, causa,
							obs);
					try {
						modelo.agregar(cita);
						cmbPacientes.setSelectedIndex(0);
						cmbMedicos.setSelectedIndex(0);
						cmbDia.setSelectedIndex(0);
						cmbMes.setSelectedIndex(0);
						cmbAño.setSelectedIndex(0);
						txtHora.setText("");
						txtMinutos.setText("");
						txtObs.setText("");
						txtCausa.setText("");
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
									"Está seguro que desea eliminar esta cita?\n\nNOTA: Se eliminará permanentemente.");
					if (respuesta == JOptionPane.YES_OPTION) {
						try {
							modelo.eliminar(tblCitas.getSelectedRow());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						cmbAP.setSelectedIndex(0);
						cmbAño.setSelectedIndex(0);
						cmbDia.setSelectedIndex(0);
						cmbMedicos.setSelectedIndex(0);
						cmbPacientes.setSelectedIndex(0);
						cmbMes.setSelectedIndex(0);
						txtCausa.setText("");
						txtObs.setText("");
						txtMinutos.setText("");
						txtHora.setText("");
						btnCambios.setEnabled(false);
						btnAgregar.setEnabled(true);
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Debe seleccionar una cita.");
				}

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
						&& cmbAño.getSelectedIndex() != 0
						&& !"".equals(txtCausa.getText())) {

					int id = citaActual.getId();
					int fila = tblCitas.getSelectedRow();
					try {
						Paciente paciente = cmbPacientes.getItemAt(cmbPacientes
								.getSelectedIndex());
						modelo.modificar(id, 1, fila, paciente);
						Medico medico = cmbMedicos.getItemAt(cmbMedicos
								.getSelectedIndex());
						modelo.modificar(id, 2, fila, medico);
						String dia = (String) cmbDia.getSelectedItem();
						int mes = (int) cmbMes.getSelectedIndex();
						String año = (String) cmbAño.getSelectedItem();
						String fecha = dia + "/" + mes + "/" + año;
						modelo.modificar(id, 3, fila, fecha);
						String h = txtHora.getText();
						String m = txtMinutos.getText();
						String t = (String) cmbAP.getSelectedItem();
						String hora = h + ":" + m + " " + t;
						modelo.modificar(id, 4, fila, hora);
						String causa = txtCausa.getText();
						modelo.modificar(id, 5, fila, causa);
						String obs = txtObs.getText();
						modelo.modificar(id, 6, fila, obs);
						btnCambios.setEnabled(false);
						btnAgregar.setEnabled(true);
						btnEliminar.setEnabled(false);
						tblCitas.clearSelection();
						txtHora.setText("");
						txtMinutos.setText("");
						cmbAño.setSelectedIndex(0);
						cmbMes.setSelectedIndex(0);
						cmbDia.setSelectedIndex(0);
						cmbPacientes.setSelectedIndex(0);
						cmbMedicos.setSelectedIndex(0);
						cmbAP.setSelectedIndex(0);
						txtCausa.setText("");
						txtObs.setText("");
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Los campos con \"*\" son obligatorios.");
				}
			}
		});

		try {
			tblCitas = new JTable(ModeloCitas.getInstancia());
			tblCitas.setPreferredScrollableViewportSize(new Dimension(550, 160));
			tblCitas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					citaActual = modelo.getCitas().get(
							tblCitas.getSelectedRow());
					btnCambios.setEnabled(true);
					btnEliminar.setEnabled(true);
					btnAgregar.setEnabled(false);
					cmbPacientes.setSelectedItem(getItemPaciente(citaActual
							.getPaciente().getId()));
					cmbMedicos.setSelectedItem(getItemMedico(citaActual
							.getMedico().getId()));
					String[] f = citaActual.getFecha().split("/");
					cmbDia.setSelectedItem(f[0]);
					cmbMes.setSelectedIndex(Integer.parseInt(f[1]));
					cmbAño.setSelectedItem(f[2]);
					String[] h = citaActual.getHora().split(":");
					txtHora.setText(h[0]);
					txtMinutos.setText(h[1].split(" ")[0]);
					cmbAP.setSelectedItem(h[1].split(" ")[1]);
					txtCausa.setText(citaActual.getCausa());
					txtObs.setText(citaActual.getObservacion());
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
		cmbAño = getcomboAño();
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
		txtCausa = new JTextArea(5, 10);
		txtObs = new JTextArea(5, 10);

		JPanel pnlGrid = new JPanel(new GridLayout(2, 4));
		pnlGrid.setPreferredSize(new Dimension(550, 50));
		pnlGrid.add(lblPaciente);
		pnlGrid.add(cmbPacientes);
		pnlGrid.add(lblMedico);
		pnlGrid.add(cmbMedicos);
		pnlGrid.add(lblFecha);
		pnlGrid.add(cmbDia);
		pnlGrid.add(cmbMes);
		pnlGrid.add(cmbAño);

		panel.add(lblTitulo);
		panel.add(lblBlanco);
		panel.add(pnlGrid);
		panel.add(lblBlanco2);
		panel.add(lblHora);
		panel.add(txtHora);
		panel.add(lblInter);
		panel.add(txtMinutos);
		panel.add(cmbAP);
		panel.add(lblCausa);
		panel.add(new JScrollPane(txtCausa));
		panel.add(lblObs);
		panel.add(new JScrollPane(txtObs));
		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(btnCambios);
		panel.add(new JScrollPane(tblCitas));
		return panel;
	}

	public Medico getItemMedico(int id) {
		Medico retorno = null;
		for (int i = 0; i < cmbMedicos.getItemCount(); i++) {
			if (cmbMedicos.getItemAt(i).getId() == id) {
				retorno = cmbMedicos.getItemAt(i);
			}
		}
		return retorno;
	}

	public Paciente getItemPaciente(int id) {
		Paciente retorno = null;
		for (int i = 0; i < cmbPacientes.getItemCount(); i++) {
			if (cmbPacientes.getItemAt(i).getId() == id) {
				retorno = cmbPacientes.getItemAt(i);
			}
		}
		return retorno;
	}

	private JComboBox<String> getComboDia() {
		cmbDia = new JComboBox<String>();
		cmbDia.addItem("Día");
		for (int i = 1; i <= 31; i++) {
			cmbDia.addItem(Integer.toString(i));
		}
		return cmbDia;
	}

	private JComboBox<String> getcomboAño() {
		cmbAño = new JComboBox<String>();
		cmbAño.addItem("Año");
		for (int i = 2013; i >= 1990; i--) {
			cmbAño.addItem(Integer.toString(i));
		}
		return cmbAño;
	}
}
