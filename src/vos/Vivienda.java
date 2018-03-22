package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.*;

import org.codehaus.jackson.annotate.JsonProperty;

public class Vivienda 
{

	//
	// Atributos
	//
	@JsonProperty( value = "CAPACIDAD")
	private int capacidad;

	@JsonProperty( value = "CARACTERISTICAS_SEGURO")
	private String caracteristicasSeguro;

	@JsonProperty( value = "CARACTERISTICAS_VIVIENDA")
	private String caracteristicas;
	
	@JsonProperty( value = "ID_VIVIENDA")
	private int idVivienda;
	
	@JsonProperty( value = "PRECIO_VIVIENDA")
	private double precioVivienda;
	
	@JsonProperty( value = "ID_VECINO")
	private int idVecino;
	
	
	//
	// Constructor
	//
	
	public Vivienda(int capacidad, String caracteristicasSeguro, String caracteristicas, int idVivienda, 
			double precioVivienda, int idVecino){
	
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
