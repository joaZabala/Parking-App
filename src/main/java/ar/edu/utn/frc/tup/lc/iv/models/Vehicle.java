package ar.edu.utn.frc.tup.lc.iv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    /**
     IMPORTANTE: No modificar esta clase. NO ES NECESARIO
     */

    private String id;
    private String brand;
    private String model;
    private String color;
    private String type;

}
