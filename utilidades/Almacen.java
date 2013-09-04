package edu.itla.gestorpacientes.utilidades;

import java.io.IOException;
import java.sql.SQLException;

import org.jdom2.JDOMException;

import edu.itla.gestorpacientes.entidades.Medico;
import edu.itla.gestorpacientes.entidades.Paciente;
import edu.itla.gestorpacientes.entidades.Padecimiento;
import edu.itla.gestorpacientes.entidades.PruebaLaboratorio;
import edu.itla.gestorpacientes.persistencia.Conexion;

public class Almacen {

	private Conexion con;

	public Almacen() {
		try {
			con = Conexion.getInstancia();
		} catch (ClassNotFoundException | SQLException | JDOMException
				| IOException e) {
			e.printStackTrace();
		}
	}

	public Paciente getItemPaciente(int id) {
		Paciente retorno = null;
		try {
			for (int i = 0; i < con.getPacientes().getItemCount(); i++) {
				if (con.getPacientes().getItemAt(i).getId() == id) {
					retorno = con.getPacientes().getItemAt(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public Padecimiento getItemPadecimiento(int id) {
		Padecimiento retorno = null;
		try {
			for (int i = 0; i < con.getPadecimientos().getItemCount(); i++) {
				if (con.getPadecimientos().getItemAt(i).getId() == id) {
					retorno = con.getPadecimientos().getItemAt(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public Medico getItemMedico(int id) {
		Medico retorno = null;
		try {
			for (int i = 0; i < con.getMedicos().getItemCount(); i++) {
				if (con.getMedicos().getItemAt(i).getId() == id) {
					retorno = con.getMedicos().getItemAt(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public PruebaLaboratorio getItemPruebaLaboratorio(int id) {
		PruebaLaboratorio retorno = null; 
		try {
			for (int i = 0; i < con.getPruebas_Laboratorio().getItemCount(); i++) {
				if (con.getPruebas_Laboratorio().getItemAt(i).getId() == id) {
					retorno = con.getPruebas_Laboratorio().getItemAt(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
