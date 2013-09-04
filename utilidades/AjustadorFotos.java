package edu.itla.gestorpacientes.utilidades;

import java.awt.Image;

import javax.swing.ImageIcon;

public class AjustadorFotos {
	
	public static ImageIcon ajustarImagen(Image imagen) {
		ImageIcon retorno = new ImageIcon(imagen.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		return retorno;
	}

}
