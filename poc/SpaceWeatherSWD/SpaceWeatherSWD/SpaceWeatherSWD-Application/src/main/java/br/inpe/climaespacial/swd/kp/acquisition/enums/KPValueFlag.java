package br.inpe.climaespacial.swd.kp.acquisition.enums;

public enum KPValueFlag {
    UP,
    DOWN,
    ZERO;
    
    public static KPValueFlag getEnumByValue(String value) {
        switch (value) {
            case "+": return UP;
            case "-": return DOWN;
            case "o": return ZERO;
            default : return null;
        } 
    }

}
