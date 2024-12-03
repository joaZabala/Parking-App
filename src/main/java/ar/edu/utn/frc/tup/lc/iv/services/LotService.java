package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.models.Floor;
import ar.edu.utn.frc.tup.lc.iv.models.Lot;
import ar.edu.utn.frc.tup.lc.iv.models.LotTrace;
import ar.edu.utn.frc.tup.lc.iv.models.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface LotService {

    List<Lot> findAll();

    List<Lot> findAllByFloor(Floor floor);

    Lot findById(Long id);

    LotTrace saleLot(Long lotId, Vehicle vehicle, LocalDateTime entryDateTime);

    LotTrace rentLot(Long lotId, Vehicle vehicle, Integer months, LocalDateTime entryDateTime);

    LotTrace entryLot(Long lotId, Vehicle vehicle, LocalDateTime entryDateTime);

    LotTrace exitTrace(Long lotId, LocalDateTime exitDateTime);
}
