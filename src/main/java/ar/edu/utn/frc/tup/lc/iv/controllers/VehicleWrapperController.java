package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.clients.VehicleRestClient;
import ar.edu.utn.frc.tup.lc.iv.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin("*")
public class VehicleWrapperController {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    @Autowired
    private VehicleRestClient vehicleRestClient;

    @GetMapping("")
    public ResponseEntity<Vehicle> getList(@RequestParam String patente) {
        return vehicleRestClient.getVehicleById(patente);
    }
}
