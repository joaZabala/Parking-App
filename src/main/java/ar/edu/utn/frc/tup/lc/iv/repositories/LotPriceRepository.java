package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.LotPriceEntity;
import ar.edu.utn.frc.tup.lc.iv.models.LotType;
import ar.edu.utn.frc.tup.lc.iv.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotPriceRepository extends JpaRepository<LotPriceEntity, Long> {

    LotPriceEntity findAllByTypeAndSectionAndActiveTrue(LotType type, Section section);
}
