package com.efrain.gestorpacientes.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class GestorXml {
	
	private SAXBuilder constructor;
	private Document documento;
	private Element ra�z;
	
	public GestorXml() throws JDOMException, IOException{
		constructor = new SAXBuilder();
		
		documento = constructor.build(new File("src/com/efrain/gestorpacientes/config.xml"));
		
		
		ra�z = documento.getRootElement();
	}
	
	public String getClaseDriver()  {
		String claseDriver = ra�z.getChildText("clase-driver");
		return claseDriver;
	}
	
	public String getDirecci�nBD() {
		String dirBD = "jdbc:" 
		+ getGestorBD()
		+ "://" + getUbicaci�nBD()
		+ "/" + getNombreBD()
		+ "?user=" + getUsuarioBD()
		+ "&password=" + getPassBD();
		return dirBD;
	}

	public String getGestorBD() {
		String gestorBD = ra�z.getChildText("gestor");
		return gestorBD;
	}
	
	public String getNombreBD() {
		String nombreBD = ra�z.getChildText("nombre");
		return nombreBD;
	}
	
	public String getUbicaci�nBD() {
		String ubiBD = ra�z.getChildText("ubicaci�n");
		return ubiBD;
	}
	
	public String getUsuarioBD() {
		String usuarioBD = ra�z.getChildText("usuario");
		return usuarioBD;
	}
	
	public String getPassBD() {
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
