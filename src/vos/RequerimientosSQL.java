package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class RequerimientosSQL 
{
	@JsonProperty("20ofertas")
	private List<Oferta> ofertasM�sPopulares;
	
	
	public RequerimientosSQL()
	{
		
	}
	
	/**
	 * @return the ofertasM�sPopulares
	 */
	public List<Oferta> getOfertasM�sPopulares() {
		return ofertasM�sPopulares;
	}


	/**
	 * @param ofertasM�sPopulares the ofertasM�sPopulares to set
	 */
	public void setOfertasM�sPopulares(List<Oferta> ofertasM�sPopulares) {
		this.ofertasM�sPopulares = ofertasM�sPopulares;
	}
	
	
}
