package com.efrain.gestorpacientes.entidades;

public class Receta {

	private int id;
	private String paciente;
	private String padecimiento;
	private String medicamentos;
	private int idPaciente;
	private int idPadecimiento;
	
	public Receta() {
		//
	}

	public Receta(String paciente, String padecimiento, String medicamentos) {
		this.paciente = paciente;
		this.padecimiento = padecimiento;
		this.medicamentos = medicamentos;
	}
	
	public Receta(int idPaciente, int idPadecimiento, String medicamentos) {
		this.setIdPaciente(idPaciente);
		this.setIdPadecimiento(idPadecimiento);
		this.medicamentos = medicamentos;
	}
	

	public Receta(int id, String paciente, String padecimiento, String medicamentos) {
		this.id = id;
		this.paciente = paciente;
		this.padecimiento = padecimiento;
		this.medicamentos = medicamentos;
	}
	
	public Receta(int id, int idPaciente, int idPadecimiento, String medicamentos) {
		this.id = id;
		this.idPaciente = idPaciente;
		this.idPadecimiento = idPadecimiento;
		this.medicamentos = medicamentos;
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

	public String getPadecimiento() {
		return padecimiento;
	}

	public void setPadecimiento(String padecimiento) {
		this.padecimiento = padecimiento;
	}

	public String getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(String medicamentos) {
		this.medicamentos = medicamentos;
	}

	public int getIdPadecimiento() {
		return idPadecimiento;
	}

	public void setIdPadecimiento(int idPadecimiento) {
		this.idPadecimiento = idPadecimiento;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

}
