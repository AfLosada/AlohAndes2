package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOUsoHotel {

	@JsonProperty("NOMBRE_CLIENTE")
	private String NOMBRE_CLIENTE;

	@JsonProperty("TIPO_CLIENTE")
	private String TIPO_CLIENTE;

	@JsonProperty("ID_HOTEL")
	private String ID_HOTEL;

	public VOUsoHotel(String nom, String tip, String id){
		NOMBRE_CLIENTE = nom;
		TIPO_CLIENTE = tip;
		ID_HOTEL = id;
	}

	public String getNOMBRE_CLIENTE() {
		return NOMBRE_CLIENTE;
	}

	public void setNOMBRE_CLIENTE(String nOMBRE_CLIENTE) {
		NOMBRE_CLIENTE = nOMBRE_CLIENTE;
	}

	public String getTIPO_CLIENTE() {
		return TIPO_CLIENTE;
	}

	public void setTIPO_CLIENTE(String tIPO_CLIENTE) {
		TIPO_CLIENTE = tIPO_CLIENTE;
	}

	public String getID_HOTEL() {
		return ID_HOTEL;
	}

	public void setID_HOTEL(String iD_HOTEL) {
		ID_HOTEL = iD_HOTEL;
	}




}
