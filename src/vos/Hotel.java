package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Hotel extends Operador 
{
	@JsonProperty( value = "capacidad_Hotel")
	private int capacidad;
	

	@JsonProperty( value = "id_Hotel")
	private int id;
	
	
	public Hotel(@JsonProperty( value = "camara_Comercio")boolean camaraComercio,@JsonProperty( value = "nombre") String nombreOperador,@JsonProperty( value = "superIntendencia_Turismo") boolean superIntendenciaTurismo,@JsonProperty( value = "capacidad_operador") int capacidad,
			@JsonProperty( value = "id_hotel")int id_Hotel) {
		super(camaraComercio, nombreOperador, superIntendenciaTurismo);
		this.capacidad = capacidad;
		this.id = id_Hotel;
	}

	

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getIdHotel() {
		return id;
	}

	public void setIdHotel(int idHotel) {
		this.id = idHotel;
	}
}
