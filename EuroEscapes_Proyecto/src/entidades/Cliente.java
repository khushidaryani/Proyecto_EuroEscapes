package entidades;

/**
 * 	Representa un cliente con sus atributos
 */
public class Cliente {

	String dni;
	String password;
	String nombre;
	String primerApellido;
	String segundoApellido;
	String contacto;
	
	/**
	 * Constructor del objeto Cliente
	 * @param dni El DNI del cliente
	 * @param password La contraseña del cliente
	 * @param nombre El nombre del cliente
	 * @param primerApellido El primer apellido del cliente
	 * @param segundoApellido El segundo apellido del cliente
	 * @param contacto El contacto del cliente
	 */

	public Cliente(String dni, String password, String nombre, String primerApellido, String segundoApellido,
			String contacto) {
		this.dni = dni;
		this.password = password;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.contacto = contacto;
	}
	
	// Métodos para acceder y modificar los atributos de la clase

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

}
