package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class HabitacionesServiciosInmobiliarios 
{
	//
	// Atributos
	//
	@JsonProperty( value = "id_servicioIn")
	private Integer idServicioInmobiliario;
	@JsonProperty ( value = "id_hab")
	private Integer idHabitacion;
	
	
	//
	// Constructor
	//
	
	/**
	 * @param idServicioInmobiliario
	 * @param idHabitacion
	 */
	public HabitacionesServiciosInmobiliarios(
			@JsonProperty( value = "id_servicioIn") Integer idServicioInmobiliario, 
			@JsonProperty ( value = "id_hab")Integer idHabitacion) {
		super();
		this.idServicioInmobiliario = idServicioInmobiliario;
		this.idHabitacion = idHabitacion;
	}
	
	//
	// Getters y Setters
	//
	
	/**
	 * @return the idServicioInmobiliario
	 */
	public int getIdServicioInmobiliario() {
		return idServicioInmobiliario;
	}
	/**
	 * @param idServicioInmobiliario the idServicioInmobiliario to set
	 */
	public void setIdServicioInmobiliario(int idServicioInmobiliario) {
		this.idServicioInmobiliario = idServicioInmobiliario;
	}
	/**
	 * @return the idHabitacion
	 */
	public int getIdHabitacion() {
		return idHabitacion;
	}
	/**
	 * @param idHabitacion the idHabitacion to set
	 */
	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}
	
}
