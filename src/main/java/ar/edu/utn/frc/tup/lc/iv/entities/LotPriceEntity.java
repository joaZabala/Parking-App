package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.models.LotType;
import ar.edu.utn.frc.tup.lc.iv.models.Section;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lot_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotPriceEntity {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LotType type;

    @Enumerated(EnumType.STRING)
    private Section section;

    private BigDecimal price;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private Boolean active;
}
