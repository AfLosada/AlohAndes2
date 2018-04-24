package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOUsoVivienda {
	
	@JsonProperty("NOMBRE_CLIENTE")
	private String NOMBRE_CLIENTE;

	@JsonProperty("TIPO_CLIENTE")
	private String TIPO_CLIENTE;

	@JsonProperty("ID_VIVIENDAU")
	private String ID_VIVIENDAU;

	public VOUsoVivienda(String nom, String tip, String id){
		NOMBRE_CLIENTE = nom;
		TIPO_CLIENTE = tip;
		ID_VIVIENDAU = id;
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

	public String getID_VIVIENDAU() {
		return ID_VIVIENDAU;
	}

	public void setID_VIVIENDAU(String iD_HOSTAL) {
		ID_VIVIENDAU = iD_HOSTAL;
	}

}
