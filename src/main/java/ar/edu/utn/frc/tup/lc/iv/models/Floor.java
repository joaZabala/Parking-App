package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Floor {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    @JsonProperty("SUBSUELO")
    SUBSUELO,
    @JsonProperty("PLANTA BAJA")
    PLANTA_BAJA,
    @JsonProperty("PRIMER PISO")
    PRIMER_PISO;
}
