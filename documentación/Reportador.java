package edu.itla.gestorpacientes.documentación;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Receta;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class Reportador {

	protected ResultSet resultado;
	protected Connection con;
	private Conexion conexion;
	private static Reportador instancia;

	public static synchronized Reportador getInstancia()
			throws ClassNotFoundException, SQLException, JDOMException,
			IOException {
		return instancia == null ? instancia = new Reportador() : instancia;
	}

	private Reportador() throws ClassNotFoundException, SQLException,
			JDOMException, IOException {
		conexion = Conexion.getInstancia();
		con = conexion.getConexion();
	}

	@SuppressWarnings("deprecation")
	public void generarReceta(int id) throws JDOMException, IOException,
			ClassNotFoundException, JRException, SQLException {
		Receta receta = conexion.getRecetaById(id);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("paciente", receta.getPaciente());
		parametros.put("padecimiento", receta.getPadecimiento());
		parametros.put("medicamentos", receta.getMedicamentos());
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject("./src/edu/itla/gestorpacientes/documentación/ReporteRecetas.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				parametros, con);
		JasperViewer jViewer = new JasperViewer(jasperPrint, false);
		jViewer.setVisible(true);
		jViewer = new JasperViewer(jasperPrint, true);
	}

	@SuppressWarnings("deprecation")
	public void getTopMedicos() throws SQLException, JRException {
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject("./src/edu/itla/gestorpacientes/documentación/ReporteMedicos.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, con);
		JasperViewer jViewer = new JasperViewer(jasperPrint, false);
		jViewer.setVisible(true);
		jViewer = new JasperViewer(jasperPrint, true);
	}

	@SuppressWarnings("deprecation")
	public void getTopPadecimientos() throws SQLException, JRException {
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject("./src/edu/itla/gestorpacientes/documentación/ReportePadecimientos.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, con);
		JasperViewer jViewer = new JasperViewer(jasperPrint, false);
		jViewer.setVisible(true);
		jViewer = new JasperViewer(jasperPrint, true);
	}

	public static void main(String[] args) {
		try {

			Reportador re = new Reportador();
			re.generarReceta(8);
		} catch (ClassNotFoundException | SQLException | JDOMException
				| IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

}