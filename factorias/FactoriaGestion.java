package com.efrain.gestorpacientes.factorias;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FactoriaGestion {
	public void agregar(Object persona);
	public void eliminar(int fila); 
	public void modificar(int id, int atributo, Object valor) throws SQLException;
	public ArrayList<?> getDatos();
}
