package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class RequerimientosSQL 
{
	@JsonProperty("20ofertas")
	private List<Oferta> ofertasMásPopulares;
	
	
	public RequerimientosSQL()
	{
		
	}
	
	/**
	 * @return the ofertasMásPopulares
	 */
	public List<Oferta> getOfertasMásPopulares() {
		return ofertasMásPopulares;
	}


	/**
	 * @param ofertasMásPopulares the ofertasMásPopulares to set
	 */
	public void setOfertasMásPopulares(List<Oferta> ofertasMásPopulares) {
		this.ofertasMásPopulares = ofertasMásPopulares;
	}
	
	
}
