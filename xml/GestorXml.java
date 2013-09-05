package edu.itla.gestorpacientes.xml;

import java.io.File;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class GestorXml {
	
	private SAXBuilder constructor;
	private Document documento;
	private Element ra�z;
	private static GestorXml instancia;
	
	public static synchronized GestorXml getInstancia() throws JDOMException, IOException {
		return instancia == null ? instancia = new GestorXml() : instancia;
	}
	
	private GestorXml() throws JDOMException, IOException{
		constructor = new SAXBuilder();
		documento = constructor.build(new File("src/edu/itla/gestorpacientes/config.xml"));
		ra�z = documento.getRootElement();
	}
	
	public String getClaseDriver()  {
		String claseDriver = ra�z.getChildText("clase-driver");
		return claseDriver;
	}
	
	//Fachada XMl
	public String getDirecci�nBD() {
		String dirBD = "jdbc:" 
		+ getGestorBD()
		+ "://" + getUbicaci�nBD()
		+ "/" + getNombreBD()
		+ "?user=" + getUsuarioBD()
		+ "&password=" + getPassBD();
		return dirBD;
	}

	private String getGestorBD() {
		String gestorBD = ra�z.getChildText("gestor");
		return gestorBD;
	}
	
	private String getNombreBD() {
		String nombreBD = ra�z.getChildText("nombre");
		return nombreBD;
	}
	
	private String getUbicaci�nBD() {
		String ubiBD = ra�z.getChildText("ubicaci�n");
		return ubiBD;
	}
	
	private String getUsuarioBD() {
		String usuarioBD = ra�z.getChildText("usuario");
		return usuarioBD;
	}
	
	private String getPassBD() {
		String passBD = ra�z.getChildText("contrase�a");
		return passBD;
	}

	public Document getDocumento() {
		return documento;
	}

	public void setDocumento(Document documento) {
		this.documento = documento;
	}
}
