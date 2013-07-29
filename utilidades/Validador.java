package com.efrain.gestorpacientes.utilidades;

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

}
