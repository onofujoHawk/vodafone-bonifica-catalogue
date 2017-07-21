package it.arlanis.reply.models.enums;

public enum ParameterType {
	Static("Static"),
	LOV("LOV"),
	Reference("Reference");
	
	private String nome;

	private ParameterType(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public static String getParameterTypeValue(ParameterType type) {
		for (ParameterType p : ParameterType.values()) {
			if (p.getNome().equalsIgnoreCase(type.getNome()))
				return p.getNome();
		}
		return null;
	}

	public static ParameterType getParameterType(String value) {
		for (ParameterType p : ParameterType.values()) {
			if (value.equals(p.getNome())) {
				return p;
			}
		}
		return null;
	}

}
