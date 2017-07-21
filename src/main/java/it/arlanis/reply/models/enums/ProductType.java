package it.arlanis.reply.models.enums;

public enum ProductType {

	Service("Servizio"),
	/* f.onofrio@reply.it - Aggiunto May 2017. ST29184/Diritto di ripensamento */
	Optional("Servizio Opzionale"),
	Promo("Promo"),
	SpyderService("Servizio Spyder"),
	SpyderPromo("Promo Spyder");

	private String nome;

	private ProductType(String value) {
		this.nome = value;
	}

	public String getNome() {
		return nome;
	}

	public static String getProductTypeValue(ProductType type) {
		for (ProductType p : ProductType.values()) {
			if (p.getNome().equalsIgnoreCase(type.getNome()))
				return p.getNome();
		}
		return null;
	}

	public static ProductType getProductType(String value) {
		for (ProductType p : ProductType.values()) {
			if (value.equals(p.getNome())) {
				return p;
			}
		}
		return null;
	}

}
