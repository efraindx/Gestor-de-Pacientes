package edu.itla.gestorpacientes.entidades;

public class Resultado_Prueba_Laboratorio {

	private int id;
	private String paciente;
	private String prueba;
	private String resultado;
	private int idPaciente;
	private int idPrueba;
	
	public Resultado_Prueba_Laboratorio() {
		//
	}
	
	public Resultado_Prueba_Laboratorio(int idPaciente, int idPrueba, String resultado) {
		this.setIdPaciente(idPaciente);
		this.setIdPrueba(idPrueba);
		this.resultado = resultado;
	}

	public Resultado_Prueba_Laboratorio(int id, String paciente, String prueba,
			String resultado) {
		this.id = id;
		this.paciente = paciente;
		this.prueba = prueba;
		this.resultado = resultado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public int getIdPrueba() {
		return idPrueba;
	}

	public void setIdPrueba(int idPrueba) {
		this.idPrueba = idPrueba;
	}

}
