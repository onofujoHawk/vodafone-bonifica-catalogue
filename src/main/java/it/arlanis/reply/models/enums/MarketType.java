package it.arlanis.reply.models.enums;

public enum MarketType {
	SinglePlay("Single Play"),
	Mobile("Mobile");
	
	private String nome;

	private MarketType(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public static String getMarketTypeValue(MarketType type) {
		for (MarketType p : MarketType.values()) {
			if (p.getNome().equalsIgnoreCase(type.getNome()))
				return p.getNome();
		}
		return null;
	}

	public static MarketType getMarketType(String value) {
		for (MarketType p : MarketType.values()) {
			if (value.equals(p.getNome())) {
				return p;
			}
		}
		return null;
	}

}
