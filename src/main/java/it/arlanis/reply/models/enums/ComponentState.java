package it.arlanis.reply.models.enums;

public enum ComponentState {
	New("Nuovo"), Activating("Attivabile"), Active("Attivo"), Disabled(
			"Disattivo");

	private String nome;

	private ComponentState(String nome) {
		this.nome = nome;
	}

	public String nome() {
		return nome;
	}

	public static String getComponentStateValue(ComponentState state) {
		for (ComponentState c : ComponentState.values()) {
			if (c.nome().equalsIgnoreCase(state.nome()))
				return c.nome();
		}
		return null;

	}

	public static ComponentState getState(String nome) {
		for (ComponentState cs : ComponentState.values()) {
			if (nome.equals(cs.nome())) {
				return cs;
			}
		}
		return null;
	}
}
