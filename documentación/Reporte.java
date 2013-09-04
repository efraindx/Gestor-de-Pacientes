package edu.itla.gestorpacientes.documentación;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.persistencia.Conexion;
import edu.itla.gestorpacientes.xml.GestorXml;

public class Reporte {

	protected ResultSet resultado;
	protected PreparedStatement enunciado;
	protected Connection con;
	protected Statement consulta;
	private Conexion conexion;;

	public Reporte() {
		try {
			this.conexion = Conexion.getInstancia();
			this.con = conexion.getConnection();
			this.consulta = con.createStatement();
		} catch (SQLException | ClassNotFoundException | JDOMException
				| IOException e) {
			e.printStackTrace();
		}
	}

	public void generarReceta(int id) throws JDOMException, IOException,
			ClassNotFoundException, JRException, SQLException {
		String query = "SELECT p.nombre, p.apellido, pd.nombre_padecimiento as padecimiento, r.medicamentos FROM pacientes p JOIN "
				+ "padecimientos pd JOIN recetas r WHERE id_paciente = "
				+ id
				+ " GROUP BY r.id";
		resultado = consulta.executeQuery(query);
		JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(
				resultado);
		JasperReport jasperReport = JasperCompileManager
				.compileReport("./src/edu/itla/gestorpacientes/reportación/reporteRecetas.jrxml");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, resultSetDataSource);
		JasperViewer jViewer = new JasperViewer(jasperPrint);
		jViewer.setVisible(true);
	}

	public void topMedicos() throws SQLException, JRException {
		String query = "SELECT medico ,COUNT('medicos') AS frecuencia FROM citas c JOIN pacientes p "
				+ "ON p.id = c.id_paciente GROUP BY c.medico LIMIT 10";
		resultado = consulta.executeQuery(query);
		JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(
				resultado);
		JasperReport jasperReport = JasperCompileManager
				.compileReport("./src/edu/itla/gestorpacientes/reportación/TopMedicos.jrxml");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				null, resultSetDataSource);
		JasperViewer jViewer = new JasperViewer(jasperPrint);
		jViewer.setVisible(true);
	}

	public void topPadecimientos() throws SQLException, JRException {
			String query = "SELECT `nombre_padecimiento`, COUNT(`id_padecimiento`) AS frecuencias FROM `recetas` r  "
					+ "JOIN `padecimientos` p  WHERE r.`id_padecimiento` = p.`id` GROUP BY `id_padecimiento`  "
					+ "ORDER BY frecuencias DESC  LIMIT 10";
			resultado = consulta.executeQuery(query);
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(
					resultado);
			JasperReport jasperReport = JasperCompileManager
					.compileReport("./src/edu/itla/gestorpacientes/reportación/TopPadecimientos.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, null, resultSetDataSource);

			JasperViewer jViewer = new JasperViewer(jasperPrint);
			jViewer.setVisible(true);
	}

	public static void main(String[] args) throws ClassNotFoundException,
			JDOMException, IOException, JRException, SQLException {
		new Reporte().topMedicos();
	}
}