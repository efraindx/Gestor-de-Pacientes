package edu.itla.gestorpacientes.encriptación;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

	public class Encriptadora {

		private static String key = "UniquedKey";
		private static StandardPBEStringEncryptor encriptador;
	
		public static String encriptar(String palabra) {
			encriptador = new StandardPBEStringEncryptor();
			encriptador.setPassword(key);
			return encriptador.encrypt(palabra);
		}
		
		public static String desencriptar(String palabra) {
			encriptador = new StandardPBEStringEncryptor();
			encriptador.setPassword(key);
			return encriptador.decrypt(palabra);
		}
		
		public static void main(String[] args) {
			System.out.println(encriptar("555"));
		}
}
