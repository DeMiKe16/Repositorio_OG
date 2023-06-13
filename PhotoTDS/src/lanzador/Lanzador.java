package lanzador;

import java.awt.EventQueue;

import vista.VentanaLoginRegister;

public class Lanzador {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLoginRegister ventana = new VentanaLoginRegister();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
