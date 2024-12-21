package ar.edu.utn.frc.tup.lc.iv.clients;

import ar.edu.utn.frc.tup.lc.iv.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VehicleRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${clients.vehicle.base.url}")
    String baseResourceUrl;

    public ResponseEntity<Vehicle> getVehicleById(String id) {
        return restTemplate.getForEntity(baseResourceUrl + "/" + id, Vehicle.class);
    }
}
