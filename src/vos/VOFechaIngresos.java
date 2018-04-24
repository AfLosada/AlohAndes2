package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOFechaIngresos {
	
	
	@JsonProperty("FECHA")
	private String FECHA;
	
	@JsonProperty("VALOR")
	private Integer VALOR;
	
	public VOFechaIngresos(String date, Integer val){
		FECHA = date;
		VALOR = val;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	public Integer getVALOR() {
		return VALOR;
	}

	public void setVALOR(Integer vALOR) {
		VALOR = vALOR;
	}
	
	
	


}
