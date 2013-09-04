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
	private Element raíz;
	private static GestorXml instancia;
	
	public static synchronized GestorXml getInstancia() throws JDOMException, IOException {
		return instancia == null ? instancia = new GestorXml() : instancia;
	}
	
	private GestorXml() throws JDOMException, IOException{
		constructor = new SAXBuilder();
		documento = constructor.build(new File("src/edu/itla/gestorpacientes/config.xml"));
		raíz = documento.getRootElement();
	}
	
	public String getClaseDriver()  {
		String claseDriver = raíz.getChildText("clase-driver");
		return claseDriver;
	}
	
	//Fachada XMl
	public String getDirecciónBD() {
		String dirBD = "jdbc:" 
		+ getGestorBD()
		+ "://" + getUbicaciónBD()
		+ "/" + getNombreBD()
		+ "?user=" + getUsuarioBD()
		+ "&password=" + getPassBD();
		return dirBD;
	}

	private String getGestorBD() {
		String gestorBD = raíz.getChildText("gestor");
		return gestorBD;
	}
	
	private String getNombreBD() {
		String nombreBD = raíz.getChildText("nombre");
		return nombreBD;
	}
	
	private String getUbicaciónBD() {
		String ubiBD = raíz.getChildText("ubicación");
		return ubiBD;
	}
	
	private String getUsuarioBD() {
		String usuarioBD = raíz.getChildText("usuario");
		return usuarioBD;
	}
	
	private String getPassBD() {
		String passBD = raíz.getChildText("contraseña");
		return passBD;
	}

	public Document getDocumento() {
		return documento;
	}

	public void setDocumento(Document documento) {
		this.documento = documento;
	}
}
