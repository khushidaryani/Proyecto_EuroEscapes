package entidades;

/**
 * 	Representa un itinerario con sus atributos
 */
public class Itinerario {
	
	Pais pais;
	String medioTransporte;
	Integer duracion;

	/**
	 * 
	 * @param pais El pais del itinerario
	 * @param medioTransporte El medio de transporte del itinerario
	 * @param duracion La duracion del itinerario
	 */
	public Itinerario(Pais pais, String medioTransporte, Integer duracion) {
		this.pais = pais;
		this.medioTransporte = medioTransporte;
		this.duracion = duracion;
	}
	
	// Métodos para acceder y modificar los atributos de la clase

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public String getMedioTransporte() {
		return medioTransporte;
	}

	public void setMedioTransporte(String medioTransporte) {
		this.medioTransporte = medioTransporte;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

}
