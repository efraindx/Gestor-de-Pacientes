package com.efrain.gestorpacientes.mail;

import java.util.Properties;

import javax.mail.Session;

public class CorreoElectronico {
	
	private String correo = "efrainreyes05@hotmail.com";
	private String pass = "losreyes05";
	private Properties props;
	private Session sesion;
	
	public CorreoElectronico() {
		this.props = new Properties();
	}
	
	public void setPropiedades() {
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.stattls.enable", true);
		props.put("mail.smtp.host", "smtp.mail.com");
		props.put("mail.smtp.port", "587");
	}
	
	public void setSession() {
	//	sesion = Session.getInstance(props, new Authenticador(){
			
		});
	}

}
