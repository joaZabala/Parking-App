package ar.edu.utn.frc.tup.lc.iv.services.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.VehicleRestClient;
import ar.edu.utn.frc.tup.lc.iv.entities.LotEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.LotPriceEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.LotTraceEntity;
import ar.edu.utn.frc.tup.lc.iv.models.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.LotPriceRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.LotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.LotTraceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class LotServiceImplTest {

    @MockBean
    private LotRepository lotRepository;

    @MockBean
    private LotTraceRepository lotTraceRepository;

    @MockBean
    private LotPriceRepository lotPriceRepository;

    @MockBean
    private VehicleRestClient vehicleRestClient;

    @SpyBean
    LotServiceImpl lotService;
    @Test
    void findAll() {

        LotTraceEntity lotTraceEntity = new LotTraceEntity();
        lotTraceEntity.setLotPrice(new LotPriceEntity());
        lotTraceEntity.setAmount(BigDecimal.valueOf(10.0));
        lotTraceEntity.setVehicleId("1234");

        LotEntity lot = new LotEntity(1L ,Floor.PRIMER_PISO, Section.AUTOS, LotType.TEMPORARIO, LotStatus.LIBRE,lotTraceEntity);
        LotEntity lot1 = new LotEntity(2L ,Floor.PRIMER_PISO, Section.AUTOS, LotType.TEMPORARIO, LotStatus.OCUPADO,lotTraceEntity);

        List<LotEntity>lotEntities = new ArrayList<>();
        lotEntities.add(lot);
        lotEntities.add(lot1);

        Vehicle vehicle = new Vehicle();

        when(vehicleRestClient.getVehicleById("1234")).thenReturn(ResponseEntity.ok(vehicle));
        when(lotRepository.findAll()).thenReturn(lotEntities);

        List<Lot> lots = lotService.findAll();

        assertEquals(2, lots.size());
        assertEquals(lots.get(0).getType() , lot.getType());
        assertEquals(lots.get(0).getFloor() , Floor.PRIMER_PISO);
        assertEquals(lots.get(1).getType() , lot1.getType());
        assertEquals(lots.get(1).getStatus() , LotStatus.OCUPADO);
    }

    @Test
    void findAllByFloor() {

        LotTraceEntity lotTraceEntity = new LotTraceEntity();
        lotTraceEntity.setLotPrice(new LotPriceEntity());
        lotTraceEntity.setAmount(BigDecimal.valueOf(10.0));
        lotTraceEntity.setVehicleId("1234");

        LotEntity lot = new LotEntity(1L ,Floor.PRIMER_PISO, Section.CAMIONETAS, LotType.PRIVADO, LotStatus.LIBRE,lotTraceEntity);
        LotEntity lot1 = new LotEntity(2L ,Floor.PRIMER_PISO, Section.MOTOS, LotType.TEMPORARIO, LotStatus.OCUPADO,lotTraceEntity);

        List<LotEntity>lotEntities = new ArrayList<>();
        lotEntities.add(lot);
        lotEntities.add(lot1);

        Vehicle vehicle = new Vehicle();

        when(vehicleRestClient.getVehicleById("1234")).thenReturn(ResponseEntity.ok(vehicle));
        when(lotRepository.findAllByFloor(Floor.PRIMER_PISO)).thenReturn(lotEntities);

        List<Lot> lots = lotService.findAllByFloor(Floor.PRIMER_PISO);

        assertEquals(2, lots.size());
    }

    @Test
    void findById() {
        LotTraceEntity lotTraceEntity = new LotTraceEntity();
        lotTraceEntity.setLotPrice(new LotPriceEntity());
        lotTraceEntity.setAmount(BigDecimal.valueOf(10.0));
        lotTraceEntity.setVehicleId("1234");

        LotEntity lot = new LotEntity(1L ,Floor.PRIMER_PISO, Section.AUTOS, LotType.ALQUILER_MENSUAL, LotStatus.OCUPADO,lotTraceEntity);

        Vehicle vehicle = new Vehicle();

        when(vehicleRestClient.getVehicleById("1234")).thenReturn(ResponseEntity.ok(vehicle));
        when(lotRepository.findById(1L)).thenReturn(Optional.of(lot));


        Lot lotResponse = lotService.findById(1L);

        assertEquals(lot.getId(), lotResponse.getId());
        assertEquals(lot.getType(), LotType.ALQUILER_MENSUAL);
        assertEquals(lot.getStatus() , LotStatus.OCUPADO);
    }

    @Test
    void saleLot() {

    }

    @Test
    void rentLot() {

        LotTraceEntity lotTraceEntity = new LotTraceEntity();
        lotTraceEntity.setAmount(BigDecimal.valueOf(10.0));
        lotTraceEntity.setVehicleId("1234");

        LotEntity lot = new LotEntity(1L ,Floor.PRIMER_PISO, Section.AUTOS, LotType.ALQUILER_MENSUAL, LotStatus.LIBRE,lotTraceEntity);

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("1234");
        vehicle.setType("AUTOS");

        LotPriceEntity lotPriceEntity =
                new LotPriceEntity(1L , LotType.ALQUILER_MENSUAL, Section.AUTOS,BigDecimal.valueOf(10.0) , LocalDateTime.now() , LocalDateTime.now() , true);

        lotTraceEntity.setLotPrice(lotPriceEntity);

        when(lotPriceRepository.findAllByTypeAndSectionAndActiveTrue(LotType.ALQUILER_MENSUAL, Section.AUTOS))
                .thenReturn(lotPriceEntity);

        when(vehicleRestClient.getVehicleById("1234")).thenReturn(ResponseEntity.ok(vehicle));
        when(lotRepository.findById(1L)).thenReturn(Optional.of(lot));
        when(lotRepository.save(any(LotEntity.class))).thenReturn(lot);
        when(lotTraceRepository.save(any(LotTraceEntity.class))).thenReturn(lotTraceEntity);

        LocalDateTime entry = LocalDateTime.now();
        LotTrace lotResponse = lotService.rentLot(1L , vehicle, 3 , entry);

        assertEquals(lotResponse.getAmount() , BigDecimal.valueOf(10.0) );
        assertEquals(lotResponse.getVehicle().getBrand() , "1234" );
        assertEquals(lotResponse.getEntryDateTime() , entry);
    }


    @Test
    void rentLot() {

        LotTraceEntity lotTraceEntity = new LotTraceEntity();
        lotTraceEntity.setAmount(BigDecimal.valueOf(10.0));
        lotTraceEntity.setVehicleId("1234");

        LotEntity lot = new LotEntity(1L ,Floor.PRIMER_PISO, Section.AUTOS, LotType.ALQUILER_MENSUAL, LotStatus.LIBRE,lotTraceEntity);

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("1234");
        vehicle.setType("AUTOS");

        LotPriceEntity lotPriceEntity =
                new LotPriceEntity(1L , LotType.ALQUILER_MENSUAL, Section.AUTOS,BigDecimal.valueOf(10.0) , LocalDateTime.now() , LocalDateTime.now() , true);

        lotTraceEntity.setLotPrice(lotPriceEntity);

        when(lotPriceRepository.findAllByTypeAndSectionAndActiveTrue(LotType.ALQUILER_MENSUAL, Section.AUTOS))
                .thenReturn(lotPriceEntity);

        when(vehicleRestClient.getVehicleById("1234")).thenReturn(ResponseEntity.ok(vehicle));
        when(lotRepository.findById(1L)).thenReturn(Optional.of(lot));
        when(lotRepository.save(any(LotEntity.class))).thenReturn(lot);
        when(lotTraceRepository.save(any(LotTraceEntity.class))).thenReturn(lotTraceEntity);

        LocalDateTime entry = LocalDateTime.now();
        LotTrace lotResponse = lotService.rentLot(1L , vehicle, 1 , entry);

        assertEquals(lotResponse.getAmount() , BigDecimal.valueOf(10.0) );
        assertEquals(lotResponse.getVehicle().getBrand() , "1234" );
        assertEquals(lotResponse.getEntryDateTime() , entry);
    }
    @Test
    void entryLot() {
    }

    @Test
    void exitTrace() {
    }
}