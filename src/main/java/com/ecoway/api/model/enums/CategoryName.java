package com.ecoway.api.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryName {
    BASICO, COMPACTO, PLUS, SPORT, LUXO, SUV, CARGA_LEVE, CARGA_MAX, MOTO, SCOOTER;
    @JsonValue
    public String getValue(){
        return this.name();
    }
}
