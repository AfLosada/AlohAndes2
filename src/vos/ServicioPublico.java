package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServicioPublico 
{

	//
	// Atributos
	//

	@JsonProperty( value = "costoServicioPublico")
	private double costo;

	@JsonProperty( value = "idServicioPublico")
	private int id;

	@JsonProperty( value = "tipoServicioPublico")
	private String tipo;
	
	
	//
	// Constructor
	//

	public ServicioPublico(
			@JsonProperty( value = "costoServicioPublico")double costo, 
			@JsonProperty( value = "idServicioPublico")int id,
			@JsonProperty( value = "tipoServicioPublico") String tipo) {
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
