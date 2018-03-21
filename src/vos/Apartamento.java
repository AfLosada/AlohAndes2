package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Apartamento 
{

	@JsonProperty( value = "amoblado")
	private boolean amoblado;

	@JsonProperty( value = "capacidad_apto")
	private int capacidad;

	@JsonProperty( value = "id_apartamento")
	private int id;

	@JsonProperty( value = "precio_apto")
	private double precio;
	
	@JsonProperty (value = "id_Persona")
	private int idPersonaNatural;

	@JsonProperty ( value = "id_Oferta")
	private int idOferta;


	//
	// Constructor
	//

	public Apartamento(@JsonProperty( value = "amoblado") boolean amoblado,@JsonProperty( value = "capacidad_apto") int capacidad,
			@JsonProperty( value = "id_apartamento") int id, 
			@JsonProperty( value = "precio_apto")double precio,@JsonProperty (value = "id_Persona") int idPersonaNatural, @JsonProperty ( value = "id_Oferta")int idOferta) {
		super();
		this.amoblado = amoblado;
		this.capacidad = capacidad;
		this.id = id;
		this.precio = precio;
		this.idPersonaNatural = idPersonaNatural;
		this.idOferta = idOferta;
	}

	//
	// Getters y Setters
	// 


	public boolean isAmoblado() {
		return amoblado;
	}

	public void setAmoblado(boolean amoblado) {
		this.amoblado = amoblado;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	/**
	 * @return the idPersonaNatural
	 */
	public int getIdPersonaNatural() {
		return idPersonaNatural;
	}

	/**
	 * @param idPersonaNatural the idPersonaNatural to set
	 */
	public void setIdPersonaNatural(int idPersonaNatural) {
		this.idPersonaNatural = idPersonaNatural;
	}

	/**
	 * @return the idOferta
	 */
	public int getIdOferta() {
		return idOferta;
	}

	/**
	 * @param idOferta the idOferta to set
	 */
	public void setIdOferta(int idOferta) {
		this.idOferta = idOferta;
	}

	public String toString(boolean amoblado2) 
	{
		// TODO Auto-generated method stub
		String rta = "F";
		if(amoblado2)
			rta = "T";
		return rta;
	}

}
