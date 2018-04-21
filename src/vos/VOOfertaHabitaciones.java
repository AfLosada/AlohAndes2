package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOOfertaHabitaciones 
{

	@JsonProperty ( value = "id_Prov")
	Integer idProv;
	
	@JsonProperty ( value = "id_Oferta")
	Integer idOferta;
	
	@JsonProperty ( value = "habitaciones")
	private List<Integer> habitaciones;

	@JsonProperty ( value = "id_servicio_publico")
	private Integer idSPub;
	
	@JsonProperty ( value = "id_servicio_inmobiliario")
	private Integer idSIn;
	
	/**
	 * @param habitaciones
	 */
	public VOOfertaHabitaciones(
			@JsonProperty ( value = "habitaciones") List<Integer> habitaciones, 
			@JsonProperty ( value = "id_servicio_inmobiliario") Integer idSIn, 
			@JsonProperty ( value = "id_servicio_publico") Integer idSPub,
			@JsonProperty ( value = "id_Prov")Integer idProv,
			@JsonProperty ( value = "id_Oferta")Integer idOferta) 
	{
		super();
		this.habitaciones = habitaciones;
		this.idOferta = idOferta;
		this.idProv = idProv;
		this.idSIn = idSIn;
		this.idSPub = idSPub;
	}

	/**
	 * @return the habitaciones
	 */
	public List<Integer> getHabitaciones() {
		return habitaciones;
	}

	/**
	 * @param habitaciones the habitaciones to set
	 */
	public void setHabitaciones(List<Integer> habitaciones) {
		this.habitaciones = habitaciones;
	}
	
	
	/**
	 * @return the idSPub
	 */
	public Integer getIdSPub() {
		return idSPub;
	}

	/**
	 * @param idSPub the idSPub to set
	 */
	public void setIdSPub(Integer idSPub) {
		this.idSPub = idSPub;
	}

	/**
	 * @return the idSIn
	 */
	public Integer getIdSIn() {
		return idSIn;
	}

	/**
	 * @return the idProv
	 */
	public Integer getIdProv() {
		return idProv;
	}

	/**
	 * @param idProv the idProv to set
	 */
	public void setIdProv(Integer idProv) {
		this.idProv = idProv;
	}

	/**
	 * @return the idOferta
	 */
	public Integer getIdOferta() {
		return idOferta;
	}

	/**
	 * @param idOferta the idOferta to set
	 */
	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}
	
	/**
	 * @param idSIn the idSIn to set
	 */
	public void setIdSIn(Integer idSIn) {
		this.idSIn = idSIn;
	}
	

	}
