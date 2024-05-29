package interfazGrafica;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;

import baseDatos.BaseDatos;

/**
 * Clase principal que inicia la aplicacion EuroEscapes.
 */
public class MainEuroEscapes {

	/**
	 * Método principal que inicia la aplicacion
	 * 
	 * @param args Los argumentos de la linea de comandos
	 */
	public static void main(String[] args) {

		// Iniciar la interfaz de usuario en el hilo de despacho de eventos
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio ventana = new Inicio();
					ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			// Verificar si la tabla de países existe en la base de datos
			BaseDatos.verificarTablaPaises();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

	}

}
