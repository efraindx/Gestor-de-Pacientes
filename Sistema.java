package edu.itla.gestorpacientes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jdom2.JDOMException;


import edu.itla.gestorpacientes.algoritmos.AlgoritmoBusquedaPersonas;
import edu.itla.gestorpacientes.vistas.VentanaLogin;

public class Sistema {
	public static void main(String[] args) {
		try {
			VentanaLogin.getInstancia(new AlgoritmoBusquedaPersonas())
					.setVisible(true);

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Ha ocurrido un problema con el archivo config.xml,\n error: "
							+ e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(null,
					"Ha ocurrido un problema con el archivo config.xml,\n error: "
							+ e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner lector = new Scanner(System.in);
		String telefono = lector.nextLine();
		if(telefonoEsValido(telefono)) {
			System.out.println("valido");
		} else {
			System.out.println("invalido");
		}
		
	}
	public static boolean telefonoEsValido(String telefono) {
		String cod = telefono.substring(3, 4);
		String cod2 = telefono.substring(7, 8);
		System.out.println("Cod:" + cod + "Cod2:" + cod2);
		if (cod.equals("-") && cod2.equals("-")) {
			return true;
		} else {
			return false;
		}
	}
}