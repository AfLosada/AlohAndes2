package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServicioPublico 
{

	//
	// Atributos
	//

	@JsonProperty( value = "costo_servicio_publico")
	private double costo;

	@JsonProperty( value = "id_servicio_publico")
	private int id;

	@JsonProperty( value = "tipo_Servicio_Publico")
	private String tipo;
	
	
	//
	// Constructor
	//

	public ServicioPublico(
			@JsonProperty( value = "costo_servicio_publico")double costo, 
			@JsonProperty( value = "id_servicio_publico")int id,
			@JsonProperty( value = "tipo_servicio_publico") String tipo) {
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
