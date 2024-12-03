package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lot_traces")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotTraceEntity {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleId;

    private LocalDateTime entryDateTime;

    private LocalDateTime exitDateTime;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "price_id")
    private LotPriceEntity lotPrice;
}
