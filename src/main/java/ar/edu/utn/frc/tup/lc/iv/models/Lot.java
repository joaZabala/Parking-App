package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lot {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    private Long id;
    private Floor floor;
    private Section section;
    private LotType type;
    private LotStatus status;
    @JsonProperty("occupied_by")
    private LotTrace occupiedBy;
}
