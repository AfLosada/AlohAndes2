package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ViviendaUniversitaria extends Operador
{
	
	//
	// Atributos
	//
	
	@JsonProperty( value = "capacidadViviendau")
	private int capacidad;
	@JsonProperty( value = "idViviendaU")
	private int id;

	@JsonProperty( value = "duracionServicio")
	private double duracion;
	
	@JsonProperty( value = "amoblamiento")
	private boolean amoblamiento;

	//
	// Constructor
	//
	
	public ViviendaUniversitaria(
			@JsonProperty( value = "camaraComercio")boolean camaraComercio,
			@JsonProperty( value = "nombre") String nombreOperador,
			@JsonProperty( value = "superintendeciaTurismo") boolean superIntendenciaTurismo, 
			@JsonProperty( value = "capacidadViviendau")int capacidad,
			@JsonProperty( value = "idViviendau") int id,
			@JsonProperty( value = "duracionServicio")double duracion,
			@JsonProperty( value = "amoblamiento") boolean amoblamiento) {
		super(camaraComercio, nombreOperador, superIntendenciaTurismo);
		this.capacidad = capacidad;
		this.id = id;
		this.duracion = duracion;
		this.amoblamiento = amoblamiento;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public boolean isAmoblamiento() {
		return amoblamiento;
	}

	public void setAmoblamiento(boolean amoblamiento) {
		this.amoblamiento = amoblamiento;
	}	
}
