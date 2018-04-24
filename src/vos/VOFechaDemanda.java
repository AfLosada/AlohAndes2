package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOFechaDemanda {
	
	
	@JsonProperty("CANTIDAD_OCUPADA")
	private Integer CANTIDAD_OCUPADA;
	
	@JsonProperty("FECHA")
	private String FECHA;

	public VOFechaDemanda(Integer cant, String date){
		CANTIDAD_OCUPADA = cant;
		FECHA = date;
	}

	public Integer getCANTIDAD_OCUPADA() {
		return CANTIDAD_OCUPADA;
	}

	public void setCANTIDAD_OCUPADA(Integer cANTIDAD_OCUPADA) {
		CANTIDAD_OCUPADA = cANTIDAD_OCUPADA;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}
	
	

}
