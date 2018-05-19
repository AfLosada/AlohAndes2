package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class HabitacionesServicioPublico 
{
	
	//
	// Atributos
	//
	@JsonProperty(value = "id_servicio_publico")
	private Integer idServicioPublico;

	@JsonProperty(value = "id_habitacion")
	private Integer idHabitacion;
	
	
	//
	// Constructor
	//
	
	/**
	 * @param idServicioPublico
	 * @param idHabitacion
	 */
	public HabitacionesServicioPublico(
			@JsonProperty(value = "ID_SERVICIO_PUBLICO")Integer idServicioPublico, 
			@JsonProperty(value = "ID_HABITACION")Integer idHabitacion) {
		this.idServicioPublico = idServicioPublico;
		this.idHabitacion = idHabitacion;
	}
	
	//
	// Getters y Setters
	//
	
	/**
	 * @return the idServicioPublico
	 */
	public Integer getIdServicioPublico() {
		return idServicioPublico;
	}
	/**
	 * @param idServicioPublico the idServicioPublico to set
	 */
	public void setIdServicioPublico(Integer idServicioPublico) {
		this.idServicioPublico = idServicioPublico;
	}
	/**
	 * @return the idHabitacion
	 */
	public Integer getIdHabitacion() {
		return idHabitacion;
	}
	/**
	 * @param idHabitacion the idHabitacion to set
	 */
	public void setIdHabitacion(Integer idHabitacion) {
		this.idHabitacion = idHabitacion;
	}
	
}
