package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.*;

import org.codehaus.jackson.annotate.JsonProperty;

public class Vivienda 
{

	//
	// Atributos
	//
	@JsonProperty( value = "capacidad")
	private Integer capacidad;

	@JsonProperty( value = "caracteristicasSeguro")
	private String caracteristicasSeguro;

	@JsonProperty( value = "caracteristicasVivienda")
	private String caracteristicas;
	
	@JsonProperty( value = "idVivienda")
	private Integer idVivienda;
	
	@JsonProperty( value = "precioVivienda")
	private double precioVivienda;
	
	@JsonProperty( value = "idVecino")
	private Integer idVecino;
	
	
	//
	// Constructor
	//
	
	public Vivienda(
			@JsonProperty( value = "capacidad")Integer capacidad, 
			@JsonProperty( value = "caracteristicasSeguro")String caracteristicasSeguro,
			@JsonProperty( value = "caracteristicasVivienda") String caracteristicas, 
			@JsonProperty( value = "IidVivienda")Integer idVivienda, 
			@JsonProperty( value = "precioVivienda")double precioVivienda, 
			@JsonProperty( value = "idVecino")Integer idVecino){
	
		this.capacidad = capacidad;
		this.caracteristicasSeguro = caracteristicasSeguro;
		this.caracteristicas = caracteristicas;
		this.idVivienda = idVivienda;
		this.precioVivienda = precioVivienda;
		this.idVecino = idVecino;
	}

	//
	// Getters y Setters 
	//
	
	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getCaracteristicasSeguro() {
		return caracteristicasSeguro;
	}

	public void setCaracteristicasSeguro(String caracteristicasSeguro) {
		this.caracteristicasSeguro = caracteristicasSeguro;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public Integer getIdVivienda() {
		return idVivienda;
	}

	public void setIdVivienda(Integer idVivienda) {
		this.idVivienda = idVivienda;
	}

	public double getPrecioVivienda() {
		return precioVivienda;
	}

	public void setPrecioVivienda(double precioVivienda) {
		this.precioVivienda = precioVivienda;
	}

	public Integer getIdVecino() {
		return idVecino;
	}

	public void setIdVecino(Integer idVecino) {
		this.idVecino = idVecino;
	}
	
	
}
