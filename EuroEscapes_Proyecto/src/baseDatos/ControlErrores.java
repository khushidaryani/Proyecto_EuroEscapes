package baseDatos;

import javax.swing.JOptionPane;

public class ControlErrores {

	// String con todas las letras del DNI en orden

	static final String dniLetras = "TRWAGMYFPDXBNJZSQVHLCKE";

	/**
	 * Valida si un DNI es correcto
	 * 
	 * @param dni El DNI a validar
	 * @return true si el DNI es válido, false en caso contrario
	 */
	public static boolean validarDNI(String dni) {

		// Verifica que el DNI tenga exactamente 9 caracteres
		if (dni.length() != 9) {
			JOptionPane.showMessageDialog(null, "El DNI debe tener exactamente 9 caracteres");
			return false;
		}

		// Verifica que los primeros 8 caracteres sean dígitos

		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(dni.charAt(i))) {
				JOptionPane.showMessageDialog(null, "Los primeros 8 caracteres del DNI deben ser números");
				return false;
			}
		}

		// Verifica que el último carácter sea una letra
		if (!Character.isLetter(dni.charAt(8))) {
			JOptionPane.showMessageDialog(null, "El último carácter del DNI debe ser una letra");
			return false;
		}

		// Calcula la letra del DNI y la compara con la proporcionada
		char letra = Character.toUpperCase(extraerLetraDNI(dni));
		int numero = extraerNumeroDNI(dni);
		if (calcularLetraDNI(numero) == letra) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "La letra del DNI no es correcta.");
			return false;
		}
	}

	/**
	 * Extrae la letra de un DNI
	 * 
	 * @param dni El DNI del cual extraer la letra
	 * @return La letra del DNI
	 */
	static char extraerLetraDNI(String dni) {
		return dni.charAt(dni.length() - 1);
	}

	/**
	 * Extrae los números de un DNI
	 * 
	 * @param dni El DNI del cual extraer los números
	 * @return Los números del DNI
	 */
	static int extraerNumeroDNI(String dni) {
		return Integer.parseInt(dni.substring(0, dni.length() - 1));
	}

	/**
	 * Calcula la letra correspondiente a los números del DNI
	 * 
	 * @param numerosDNI Los números del DNI
	 * @return La letra calculada del DNI
	 */
	static char calcularLetraDNI(int numerosDNI) {
		return dniLetras.charAt(numerosDNI % 23);
	}

	/**
	 * Valida si una contraseña es correcta
	 * 
	 * @param password La contraseña a validar
	 * @return true si la contraseña es válida, false en caso contrario
	 */
	public static boolean validarPassword(String password) {

		// Verifica que la contraseña tenga entre 8 y 15 caracteres
		if (password.length() < 8 || password.length() > 15) {
			JOptionPane.showMessageDialog(null, "La contraseña debe tener entre 8 y 15 caracteres.");
			return false;
		}
		return true;
	}

	/**
	 * Valida si un nombre es correcto
	 * 
	 * @param nombre El nombre a validar
	 * @return true Si el nombre es válido, false en caso contrario
	 */
	public static boolean validarNombre(String nombre) {
		
		// Verifica que el nombre tenga entre 1 y 50 caracteres
		if (nombre.length() < 1 || nombre.length() > 50) {
			JOptionPane.showMessageDialog(null, "El nombre debe tener entre 1 y 50 caracteres");
			return false;
		}
		return true;
	}

	/**
	 * Valida si un apellido es correcto
	 * 
	 * @param apellido El apellido a validar
	 * @return true si el apellido es válido, false en caso contrario
	 */
	public static boolean validarApellido(String apellido) {
		
		// Verifica que el apellido no tenga más de 50 caracteres
		if (apellido.length() < 1 ||apellido.length() > 50) {
			JOptionPane.showMessageDialog(null, "El apellido debe tener entre 1 y 50 caracteres");
			return false;
		}
		return true;
	}

	/**
	 * Valida si un número de contacto es correcto
	 * 
	 * @param contacto El número de contacto a validar
	 * @return true si el número de contacto es válido, false en caso contrario
	 */
	public static boolean validarContacto(String contacto) {
		try {
			// Verifica que el contacto tenga exactamente 9 caracteres
			if (contacto.length() != 9) {
				JOptionPane.showMessageDialog(null, "El número de contacto debe tener exactamente 9 dígitos.");
				return false;
			}

			// Intentar convertir el contacto a un número entero
			Long.parseLong(contacto);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "El número de contacto solo puede contener dígitos.");
			return false;
		}

		return true;
	}
}
