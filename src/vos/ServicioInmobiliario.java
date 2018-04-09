package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServicioInmobiliario {

	//
	// Atributos
	//

	@JsonProperty( value = "costo_servicio_inmobiliario")
	private double costo;

	@JsonProperty( value = "id_servicio_inmobiliario")
	private int id;

	@JsonProperty( value = "tipo_servicio_inmobiliario")
	private String tipo;
	
	
	//
	// Constructor
	//

	public ServicioInmobiliario(
			@JsonProperty( value = "costo_servicio_inmobiliario")double costo,
			@JsonProperty( value = "id_servicio_inmobiliario") int id_servicio_inmobiliario, 
			@JsonProperty( value = "tipo_servicio_inmobiliario")String tipo) {
		super();
		this.costo = costo;
		this.id = id;
		this.tipo = tipo;
	}
	
	//
	// Getters y Setters
	//

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
