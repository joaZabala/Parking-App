package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LotType {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    @JsonProperty("ALQUILER MENSUAL")
    ALQUILER_MENSUAL,
    @JsonProperty("TEMPORARIO")
    TEMPORARIO,
    @JsonProperty("PRIVADO")
    PRIVADO;
}
