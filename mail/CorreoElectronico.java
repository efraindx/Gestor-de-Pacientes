package edu.itla.gestorpacientes.mail;

import java.util.Properties;



import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import edu.itla.gestorpacientes.encriptación.Encriptadora;
import edu.itla.gestorpacientes.entidades.Persona;

public class CorreoElectronico {
	
	private String correo = "efrainreyes05@gmail.com";
	private String pass = "losreyes";
	private String asunto = "Recuperación de Contraseña";
	private Properties props;
	private Session sesion;
	private MimeMessage mensaje;
	private Transport transportador;
	private String texto;
	Encriptadora encrip = new Encriptadora();

	public CorreoElectronico() {
		this.props = new Properties();
	}
	
	public void setPropiedades() {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.user", correo);
	}

	public void EnviarCorreo(Persona persona) throws AddressException, MessagingException {
		setPropiedades();
		sesion = Session.getDefaultInstance(props);
		mensaje = new MimeMessage(sesion);
		mensaje.setFrom(new InternetAddress(correo));
		mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(persona.getCorreo()));
		mensaje.setSubject(asunto);
		texto = "Estimado " + persona.getNombre() + " " + persona.getApellido() + 
				":\n\nNos place recordarle su contraseña en este corrreo, " +
				"debido a que usted la había pedido, aquí esta: " + Encriptadora.desencriptar(persona.getContrase()) +
				".\n\n@ControlSoft\nTodos los derechos Reservados.\n\nGracias por utilizar nuestros servicios!";
		mensaje.setContent(texto, "text/html");
		
		transportador = sesion.getTransport("smtp");
		transportador.connect(correo, pass);
		transportador.sendMessage(mensaje, mensaje.getAllRecipients());
		transportador.close();
	}

}
