package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOUsoPersona {
	
	@JsonProperty("NOMBRE_CLIENTE")
	private String NOMBRE_CLIENTE;

	@JsonProperty("TIPO_CLIENTE")
	private String TIPO_CLIENTE;

	@JsonProperty("ID_PERSONA")
	private String ID_PERSONA;

	public VOUsoPersona(String nom, String tip, String id){
		NOMBRE_CLIENTE = nom;
		TIPO_CLIENTE = tip;
		ID_PERSONA = id;
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

	public String getID_PERSONA() {
		return ID_PERSONA;
	}

	public void setID_PERSONA(String iD_HOSTAL) {
		ID_PERSONA = iD_HOSTAL;
	}


}
