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
	private Element raíz;
	
	public GestorXml() throws JDOMException, IOException{
		constructor = new SAXBuilder();
		
		documento = constructor.build(new File("src/com/efrain/gestorpacientes/config.xml"));
		
		
		raíz = documento.getRootElement();
	}
	
	public String getClaseDriver()  {
		String claseDriver = raíz.getChildText("clase-driver");
		return claseDriver;
	}
	
	public String getDirecciónBD() {
		String dirBD = "jdbc:" 
		+ getGestorBD()
		+ "://" + getUbicaciónBD()
		+ "/" + getNombreBD()
		+ "?user=" + getUsuarioBD()
		+ "&password=" + getPassBD();
		return dirBD;
	}

	public String getGestorBD() {
		String gestorBD = raíz.getChildText("gestor");
		return gestorBD;
	}
	
	public String getNombreBD() {
		String nombreBD = raíz.getChildText("nombre");
		return nombreBD;
	}
	
	public String getUbicaciónBD() {
		String ubiBD = raíz.getChildText("ubicación");
		return ubiBD;
	}
	
	public String getUsuarioBD() {
		String usuarioBD = raíz.getChildText("usuario");
		return usuarioBD;
	}
	
	public String getPassBD() {
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
