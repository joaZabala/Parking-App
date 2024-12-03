package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotTrace {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    private Long id;
    private Vehicle vehicle;
    @JsonProperty("date_from")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryDateTime;
    @JsonProperty("date_to")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime exitDateTime;
    private BigDecimal amount;
    private LotPrice lotPrice;
}
