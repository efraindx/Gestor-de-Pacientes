package com.efrain.gestorpacientes.factorias;

import java.util.ArrayList;


public interface FactoriaGestion {

	public void agregar(Object persona, String tabla);

	public void eliminar();

	public void modificiar();

	public ArrayList<?> mostrar();

	

}
