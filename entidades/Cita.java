package edu.itla.gestorpacientes.entidades;

public class Cita {

	private int id;
	private Paciente paciente;
	private Medico medico;
	private String fecha;
	private String hora;
	private String causa;
	private String observacion;

	public Cita() {
		//
	}

	public Cita(Paciente paciente, Medico medico, String fecha, String hora,
			String causa, String observacion) {
		this.paciente = paciente;
		this.medico = medico;
		this.fecha = fecha;
		this.hora = hora;
		this.causa = causa;
		this.observacion = observacion;
	}

	public Cita(int id, Paciente paciente, Medico medico, String fecha,
			String hora, String causa, String observacion) {
		this.id = id;
		this.paciente = paciente;
		this.medico = medico;
		this.fecha = fecha;
		this.hora = hora;
		this.causa = causa;
		this.observacion = observacion;
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

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
