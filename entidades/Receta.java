package edu.itla.gestorpacientes.entidades;

public class Receta {

	private int id;
	private Paciente paciente;
	private Padecimiento padecimiento;
	private String medicamentos;

	public Receta() {
		//
	}

	public Receta(Paciente paciente, Padecimiento padecimiento,
			String medicamentos) {
		this.setPaciente(paciente);
		this.setPadecimiento(padecimiento);
		this.medicamentos = medicamentos;
	}

	public Receta(int id, Paciente paciente, Padecimiento padecimiento,
			String medicamentos) {
		this.id = id;
		this.paciente = paciente;
		this.padecimiento = padecimiento;
		this.medicamentos = medicamentos;
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

	public Padecimiento getPadecimiento() {
		return padecimiento;
	}

	public void setPadecimiento(Padecimiento padecimiento) {
		this.padecimiento = padecimiento;
	}

	public String getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(String medicamentos) {
		this.medicamentos = medicamentos;
	}

}
