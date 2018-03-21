package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Hostal extends Operador
{

	@JsonProperty( value = "capacidad")
	private int capacidad;

	@JsonProperty( value = "id_hostal")
	private int id;

	@JsonProperty( value = "hora_Apertura")
	private String horaApertura;

	@JsonProperty( value = "hora_Cierre")
	private String horaCierre;

	@JsonProperty( value = "recepcion24_horas")
	private boolean recepcion24horas;
	

	//
	// Constructor
	//


	public Hostal(@JsonProperty( value = "camara_comercio")boolean camaraComercio,@JsonProperty( value = "nombre") String nombreOperador,@JsonProperty( value = "superintendencia_turismo") boolean superIntendenciaTurismo,@JsonProperty( value = "capacidad_hostal") int capacidad,@JsonProperty( value = "id_Hostal") int id,
			@JsonProperty( value = "hora_Apertura")String horaApertura,@JsonProperty( value = "hora_cierre") String horaCierre,@JsonProperty( value = "recepcion24_horas") boolean recepcion24horas) {
		super(camaraComercio, nombreOperador, superIntendenciaTurismo);
		this.capacidad = capacidad;
		this.id = id;
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.recepcion24horas = recepcion24horas;
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
	

	public String getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(String horaApertura) {
		this.horaApertura = horaApertura;
	}

	public String getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(String horaCierre) {
		this.horaCierre = horaCierre;
	}

	public boolean isRecepcion24horas() {
		return recepcion24horas;
	}

	public void setRecepcion24horas(boolean recepcion24horas) {
		this.recepcion24horas = recepcion24horas;
	}
	
}
