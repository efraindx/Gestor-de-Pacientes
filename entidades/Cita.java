package edu.itla.gestorpacientes.entidades;

public class Cita {

	private int id;
	private String paciente;
	private int idPaciente;
	private String medico;
	private String fecha;
	private String hora;
	private String causa;

	public Cita() {
		//
	}

	public Cita(int idPaciente, String medico, String fecha, String hora,
			String causa) {
		this.setIdPaciente(idPaciente);
		this.medico = medico;
		this.fecha = fecha;
		this.hora = hora;
		this.causa = causa;
	}

	public Cita(String paciente, String medico, String fecha, String hora,
			String causa) {
		this.paciente = paciente;
		this.medico = medico;
		this.fecha = fecha;
		this.hora = hora;
		this.causa = causa;
	}

	public Cita(int id, int idPaciente, String medico, String fecha,
			String hora, String causa) {
		this.id = id;
		this.idPaciente = idPaciente;
		this.medico = medico;
		this.fecha = fecha;
		this.hora = hora;
		this.causa = causa;
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

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
}
