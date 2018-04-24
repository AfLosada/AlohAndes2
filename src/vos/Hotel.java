package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Hotel extends Operador 
{
	@JsonProperty( value = "capacidad_Hotel")
	private Integer capacidad;
	

	@JsonProperty( value = "id_Hotel")
	private Integer id_Hotel;
	
	
	public Hotel(@JsonProperty( value = "camara_Comercio")boolean camaraComercio,@JsonProperty( value = "nombre") String nombreOperador,@JsonProperty( value = "superIntendencia_Turismo") boolean superIntendenciaTurismo,@JsonProperty( value = "capacidad_operador") Integer capacidad,
			@JsonProperty( value = "id_hotel")Integer id_Hotel) {
		super(camaraComercio, nombreOperador, superIntendenciaTurismo);
		this.capacidad = capacidad;
		this.id_Hotel = id_Hotel;
	}

	

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getIdHotel() {
		return id_Hotel;
	}

	public void setIdHotel(Integer idHotel) {
		this.id_Hotel = idHotel;
	}
}
