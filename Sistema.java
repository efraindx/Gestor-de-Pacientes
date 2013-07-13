import javax.swing.UnsupportedLookAndFeelException;

import vistas.VentanaModeloPacientes;




public class Sistema {
	
	private static Sistema instancia;
	private int numero;
	
	private static synchronized Sistema getInstancia() {
		return instancia == null ? instancia = new Sistema() : instancia;
	}
	
	public Sistema() {
		System.out.println("hollla  "+ numero++);
		numero += numero;
	}

	
	public static void main(String[] args) {/*
		VentanaModeloPacientes vm;
		try {
			vm =new VentanaModeloPacientes();
			vm.setVisible(true);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Sistema.getInstancia();
		Sistema st = new Sistema();
	
		
	}
}
