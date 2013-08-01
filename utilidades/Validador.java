package edu.itla.gestorpacientes.utilidades;

public class Validador {
	
	public static boolean cedulaEsValida(String cedula) {
		boolean retorno = false;
		try {
			String cod = cedula.substring(0, 3);
			String cod2 = cedula.substring(4, 11);
			String cod3 = cedula.substring(12, 13);
			String pc = cedula.substring(3, 4);
			String sc = cedula.substring(11, 12);

			Integer.parseInt(cod);
			Integer.parseInt(cod2);
			Integer.parseInt(cod3);
			if (cedula.length() == 13 && pc.equals("-") && sc.equals("-")) {
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception ex) {
			retorno = false;
		}
		return retorno;
	}
	
	public static boolean telefonoEsValido(String telefono) {
		boolean retorno = false;
		if (telefono.length() == 12) {
			String cod = telefono.substring(3, 4);
			String cod2 = telefono.substring(7, 8);
			if (cod.equals("-") && cod2.equals("-")) {
				retorno = true;
			} else {
				retorno = false;
			}
		} else if (telefono.length() == 10) {
			String cod = telefono.substring(0, 3);
			String cod2 = telefono.substring(3, 6);
			String cod3 = telefono.substring(6, 10);
			try {
				Integer.parseInt(cod);
				Integer.parseInt(cod2);
				Integer.parseInt(cod3);
				retorno = true;
			} catch (Exception ex) {
				retorno = false;
			}
		} else {
			retorno = false;
		}
		return retorno;
	}

}
