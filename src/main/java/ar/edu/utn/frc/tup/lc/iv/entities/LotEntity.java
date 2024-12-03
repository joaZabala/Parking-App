package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.models.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotEntity {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Floor floor;

    @Enumerated(EnumType.STRING)
    private Section section;

    @Enumerated(EnumType.STRING)
    private LotType type;

    @Enumerated(EnumType.STRING)
    private LotStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private LotTraceEntity occupiedBy;
}
