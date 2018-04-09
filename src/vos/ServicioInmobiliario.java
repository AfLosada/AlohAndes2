package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServicioInmobiliario {

	//
	// Atributos
	//

	@JsonProperty( value = "costoServicioInmobiliario")
	private double costo;

	@JsonProperty( value = "idServicioInmobiliario")
	private int id;

	@JsonProperty( value = "tipoServicioInmobiliario")
	private String tipo;
	
	
	//
	// Constructor
	//

	public ServicioInmobiliario(
			@JsonProperty( value = "costoServicioInmobiliario")double costo,
			@JsonProperty( value = "idServicioInmobiliario") int id_servicio_inmobiliario, 
			@JsonProperty( value = "tipoServicioInmobiliario")String tipo) {
		super();
		this.costo = costo;
		this.id = id_servicio_inmobiliario;
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
