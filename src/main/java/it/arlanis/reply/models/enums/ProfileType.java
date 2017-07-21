package it.arlanis.reply.models.enums;

public enum ProfileType {

	ReadOnly("Solo lettura"), CommercialCatalogAdministrator(
			"Amministratore commerciale del Catalogo"), TechnicalCatalogAdministrator(
			"Amministratore tecnico del Catalogo"), SystemAdministrator(
			"Amministratore di Sistema"), Eov("EOV"), Support("Supporto");

	private String nome;

	private ProfileType(String name) {
		this.nome = name;
	}

	public String getNome() {
		return nome;
	}
	
	public static String getProfileTypeValue(ProfileType type) {
		for (ProfileType p : ProfileType.values()) {
			if (p.getNome().equalsIgnoreCase(type.getNome()))
				return p.getNome();
		}
		return null;
	}
																													
	public static ProfileType getProfileType(String value) {
		for (ProfileType type : ProfileType.values()) {
			if (value.equals(type.getNome())) {
				return type;
			}
		}
		return null;
	}

}
