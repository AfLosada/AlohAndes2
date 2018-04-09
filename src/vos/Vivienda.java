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
	private int capacidad;

	@JsonProperty( value = "caracteristicasSeguro")
	private String caracteristicasSeguro;

	@JsonProperty( value = "caracteristicasVivienda")
	private String caracteristicas;
	
	@JsonProperty( value = "idVivienda")
	private int idVivienda;
	
	@JsonProperty( value = "precioVivienda")
	private double precioVivienda;
	
	@JsonProperty( value = "idVecino")
	private int idVecino;
	
	
	//
	// Constructor
	//
	
	public Vivienda(
			@JsonProperty( value = "capacidad")int capacidad, 
			@JsonProperty( value = "caracteristicasSeguro")String caracteristicasSeguro,
			@JsonProperty( value = "caracteristicasVivienda") String caracteristicas, 
			@JsonProperty( value = "IidVivienda")int idVivienda, 
			@JsonProperty( value = "precioVivienda")double precioVivienda, 
			@JsonProperty( value = "idVecino")int idVecino){
	
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
	
	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
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

	public int getIdVivienda() {
		return idVivienda;
	}

	public void setIdVivienda(int idVivienda) {
		this.idVivienda = idVivienda;
	}

	public double getPrecioVivienda() {
		return precioVivienda;
	}

	public void setPrecioVivienda(double precioVivienda) {
		this.precioVivienda = precioVivienda;
	}

	public int getIdVecino() {
		return idVecino;
	}

	public void setIdVecino(int idVecino) {
		this.idVecino = idVecino;
	}
	
	
}
