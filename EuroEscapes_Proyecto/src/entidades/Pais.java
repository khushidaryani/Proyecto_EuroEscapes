package entidades;

/**
 * 	Representa un pais con sus atributos
 */
public class Pais {

	Integer id;
	String nombre;

	/**
	 * 
	 * @param id El ID del pais
	 * @param nombre El nombre del pais
	 */
	public Pais(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	// Métodos para acceder y modificar los atributos de la clase
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return id + " - " + nombre;
	}

}
