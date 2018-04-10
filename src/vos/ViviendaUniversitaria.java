package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ViviendaUniversitaria extends Operador
{
	
	//
	// Atributos
	//
	
	@JsonProperty( value = "capacidadViviendau")
	private Integer capacidad;
	@JsonProperty( value = "idViviendaU")
	private Integer id;

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
			@JsonProperty( value = "superIntegerendeciaTurismo") boolean superIntegerendenciaTurismo, 
			@JsonProperty( value = "capacidadViviendau")Integer capacidad,
			@JsonProperty( value = "idViviendau") Integer id,
			@JsonProperty( value = "duracionServicio")double duracion,
			@JsonProperty( value = "amoblamiento") boolean amoblamiento) {
		super(camaraComercio, nombreOperador, superIntegerendenciaTurismo);
		this.capacidad = capacidad;
		this.id = id;
		this.duracion = duracion;
		this.amoblamiento = amoblamiento;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
