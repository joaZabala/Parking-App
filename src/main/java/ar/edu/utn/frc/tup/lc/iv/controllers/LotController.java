package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.models.Floor;
import ar.edu.utn.frc.tup.lc.iv.models.Lot;
import ar.edu.utn.frc.tup.lc.iv.models.LotTrace;
import ar.edu.utn.frc.tup.lc.iv.models.Vehicle;
import ar.edu.utn.frc.tup.lc.iv.services.LotService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/lots")
@CrossOrigin("*")
public class LotController {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    @Autowired
    private LotService lotService;

    @GetMapping("")
    public ResponseEntity<List<Lot>> getList(@RequestParam(required = false) Floor floor) {
        if (floor != null){
            return ResponseEntity.ok(lotService.findAllByFloor(floor));
        }
        List<Lot> list = lotService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{lotId}")
    public ResponseEntity<Lot> getById(@PathVariable Long lotId) {
        Lot lot = lotService.findById(lotId);
        return ResponseEntity.ok(lot);
    }

    @PostMapping("/{lotId}/sale")
    public ResponseEntity<LotTrace> saleLot(@PathVariable Long lotId,
                                            @RequestBody Vehicle vehicle,
                                            @RequestParam(required = false, value = "entry_date_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime entryDateTime) {
        if (entryDateTime == null) {
            entryDateTime = LocalDateTime.now();
        }
        LotTrace lotTrace = lotService.saleLot(lotId, vehicle, entryDateTime);
        return ResponseEntity.ok(lotTrace);
    }

    @PostMapping("/{lotId}/rent")
    public ResponseEntity<LotTrace> rentLot(@PathVariable Long lotId,
                                            @RequestBody Vehicle vehicle,
                                            @RequestParam(required = false, value = "entry_date_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime entryDateTime,
                                            @RequestParam(value = "months") Integer months) {
        if (entryDateTime == null) {
            entryDateTime = LocalDateTime.now();
        }
        LotTrace lotTrace = lotService.rentLot(lotId, vehicle, months, entryDateTime);
        return ResponseEntity.ok(lotTrace);
    }

    @PostMapping("/{lotId}/entry")
    public ResponseEntity<LotTrace> entryLot(@PathVariable Long lotId,
                                             @RequestBody Vehicle vehicle,
                                             @RequestParam(required = false, value = "entry_date_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime entryDateTime) {
        if (entryDateTime == null) {
            entryDateTime = LocalDateTime.now();
        }
        LotTrace lotTrace = lotService.entryLot(lotId, vehicle, entryDateTime);
        return ResponseEntity.ok(lotTrace);
    }

    @PutMapping("/{lotId}/exit")
    public ResponseEntity<LotTrace> exitTrace(@PathVariable Long lotId,
                                              @RequestParam(required = false, value = "exit_date_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime exitDateTime) {
        if (exitDateTime == null) {
            exitDateTime = LocalDateTime.now();
        }
        LotTrace lotTrace = lotService.exitTrace(lotId, exitDateTime);
        return ResponseEntity.ok(lotTrace);
    }

}
