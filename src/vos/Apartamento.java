package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Apartamento 
{

	@JsonProperty( value = "amoblado")
	private Boolean amoblado;

	@JsonProperty( value = "capacidad_apto")
	private Integer capacidad_apto;

	@JsonProperty( value = "id_apartamento")
	private Integer id_apartamento;

	@JsonProperty( value = "precio_apto")
	private Double precio_apto;
	
	@JsonProperty (value = "id_Persona")
	private Integer id_persona;

	@JsonProperty ( value = "id_Oferta")
	private Integer id_oferta;
	
	@JsonProperty( value = "incluye_servicios")
	private Boolean incluye_servicios;


	//
	// Constructor
	//

	public Apartamento(@JsonProperty( value = "amoblado") Boolean amoblado,
			@JsonProperty( value = "capacidad_apto") Integer capacidad,
			@JsonProperty( value = "id_apartamento") Integer id, 
			@JsonProperty( value = "precio_apto") Double precio,
			@JsonProperty (value = "id_Persona") Integer idPersonaNatural,
			@JsonProperty ( value = "id_Oferta") Integer idOferta,
			@JsonProperty( value = "incluye_servicios") Boolean incluyeServicios) {
		super();
		this.amoblado = amoblado;
		this.capacidad_apto= capacidad;
		this.id_apartamento = id;
		this.precio_apto = precio;
		this.id_persona = idPersonaNatural;
		this.id_oferta = idOferta;
		this.incluye_servicios = incluyeServicios;
	}

	//
	// Getters y Setters
	// 
	public Boolean getIncluyeServicios(){
		return incluye_servicios;
	}
	
	public void setIncluyeServicios(){
		this.incluye_servicios = incluye_servicios;
	}

	public Boolean isAmoblado(){
		return amoblado;
	}

	public void setAmoblado(boolean amoblado) {
		this.amoblado = amoblado;
	}

	public Integer getCapacidad() {
		return  capacidad_apto;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad_apto = capacidad;
	}

	public Integer getId() {
		return id_apartamento;
	}

	public void setId(int id) {
		this.id_apartamento = id;
	}

	public Double getPrecio() {
		return precio_apto;
	}

	public void setPrecio(double precio) {
		this.precio_apto = precio;
	}
	
	/**
	 * @return the idPersonaNatural
	 */
	public Integer getIdPersonaNatural() {
		return id_persona;
	}

	/**
	 * @param idPersonaNatural the idPersonaNatural to set
	 */
	public void setIdPersonaNatural(int idPersonaNatural) {
		this.id_persona = idPersonaNatural;
	}

	/**
	 * @return the idOferta
	 */
	public Integer getIdOferta() {
		return id_oferta;
	}

	/**
	 * @param idOferta the idOferta to set
	 */
	public void setIdOferta(int idOferta) {
		this.id_oferta = idOferta;
	}

	public String toString(Boolean amoblado2) 
	{
		String rta = "F";
		
		if(amoblado2 != null)
			if(amoblado2)
			rta = "T";
		return rta;
	}

}
