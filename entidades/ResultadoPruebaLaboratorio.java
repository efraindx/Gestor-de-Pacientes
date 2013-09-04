package edu.itla.gestorpacientes.entidades;

public class ResultadoPruebaLaboratorio {

	private int id;
	private Paciente paciente;
	private PruebaLaboratorio prueba;
	private String resultado;

	public ResultadoPruebaLaboratorio() {
		//
	}

	public ResultadoPruebaLaboratorio(Paciente paciente,
			PruebaLaboratorio prueba, String resultado) {
		this.paciente = paciente;
		this.prueba = prueba;
		this.resultado = resultado;
	}

	public ResultadoPruebaLaboratorio(int id, Paciente paciente,
			PruebaLaboratorio prueba, String resultado) {
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public PruebaLaboratorio getPrueba() {
		return prueba;
	}

	public void setPrueba(PruebaLaboratorio prueba) {
		this.prueba = prueba;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
