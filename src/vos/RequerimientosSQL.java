package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class RequerimientosSQL 
{
	@JsonProperty("20ofertas")
	private List<Oferta> ofertasMasPopulares;
	
	
	public RequerimientosSQL()
	{
		
	}
	
	/**
	 * @return the ofertasM�sPopulares
	 */
	public List<Oferta> getOfertasMasPopulares() {
		return ofertasMasPopulares;
	}


	/**
	 * @param ofertasM�sPopulares the ofertasM�sPopulares to set
	 */
	public void setOfertasMasPopulares(List<Oferta> ofertasMasPopulares) {
		this.ofertasMasPopulares = ofertasMasPopulares;
	}
	
	
}
