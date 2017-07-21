package it.arlanis.reply.models.enums;

public enum ChannelType {
	OTO("OTO"),
	Teleselling("Teleselling"),
	Web("Web");
	
	
	private String nome;

	private ChannelType(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public static String getChannelTypeValue(ChannelType type) {
		for (ChannelType c : ChannelType.values()) {
			if (c.getNome().equalsIgnoreCase(type.getNome()))
				return c.getNome();
		}
		return null;
	}

	public static ChannelType getChannelType(String value) {
		for (ChannelType c : ChannelType.values()) {
			if (value.equals(c.getNome())) {
				return c;
			}
		}
		return null;
	}

}
