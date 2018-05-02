package br.inpe.climaespacial.swd.values.enums;

public enum ValuesEnum {
    
	BT("Valor"),
	AVERAGE_BT("Media"),
	BY("Valor"),
	AVERAGE_BY("Media"),
	BX("Valor"),
	AVERAGE_BX("Media"),
	BZ("Valor"),
	AVERAGE_BZ("Media"),
	DENSITY("Valor"),
	AVERAGE_DENSITY("Media"),
	SPEED("Valor"),
	AVERAGE_SPEED("Media"),
	TEMPERATURE("Valor"),
	AVERAGE_TEMPERATURE("Media"),
	EY("Valor"),
	AVERAGE_EY("Media"),
	DPR("Valor"),
	AVERAGE_DPR("Media"),
	RMP("Valor"),
	AVERAGE_RMP("Media");
        
    private ValuesEnum(String valor) {
        this.valor = valor;
    }

    private String valor;

    public String getValor() {
        return valor;
    }
}
