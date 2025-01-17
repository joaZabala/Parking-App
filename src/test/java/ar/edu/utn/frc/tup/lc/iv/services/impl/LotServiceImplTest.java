package ar.edu.utn.frc.tup.lc.iv.services.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.VehicleRestClient;
import ar.edu.utn.frc.tup.lc.iv.entities.LotEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.LotPriceEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.LotTraceEntity;
import ar.edu.utn.frc.tup.lc.iv.models.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.LotPriceRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.LotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.LotTraceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private LotServiceImpl lotService;

    private LotTraceEntity lotTraceEntity;

    private LotEntity lotEntity;
    private Vehicle vehicle;
    @BeforeEach
    public void setUp(){
        vehicle = new Vehicle();
        vehicle.setId("aa123");
        vehicle.setType("AUTOS");
        vehicle.setBrand("aa123");
        vehicle.setColor("negro");

        lotTraceEntity = new LotTraceEntity();
        lotTraceEntity.setId(1L);
        lotTraceEntity.setLotPrice(new LotPriceEntity());
        lotTraceEntity.setEntryDateTime(LocalDateTime.now().minusHours(1));
        lotTraceEntity.setVehicleId(vehicle.getId());

        lotEntity = new LotEntity();
        lotEntity.setOccupiedBy(lotTraceEntity);
        lotEntity.setStatus(LotStatus.LIBRE);
        lotEntity.setFloor(Floor.PRIMER_PISO);
        lotEntity.setSection(Section.AUTOS);
        lotEntity.setType(LotType.TEMPORARIO);
        lotEntity.setId(1L);


        when(vehicleRestClient.getVehicleById("aa123")).thenReturn(ResponseEntity.ok(vehicle));

    }
    @Test
    void findAll() {
        when(lotRepository.findAll()).thenReturn(List.of(lotEntity));

        List<Lot> lots = lotService.findAll();

        assertNotNull(lots);
        assertEquals(lots.get(0).getType() , LotType.TEMPORARIO );
        assertEquals(lots.size() , 1);
    }

    @Test
    void findAllByFloor() {

        when(lotRepository.findAllByFloor(Floor.PRIMER_PISO)).thenReturn(List.of(lotEntity));
        List<Lot> lots = lotService.findAllByFloor(Floor.PRIMER_PISO);
        assertNotNull(lots);
        assertEquals(lots.size() , 1);
        assertEquals(lots.get(0).getFloor() , Floor.PRIMER_PISO);
    }

    @Test
    void findById() {

        when(lotRepository.findById(1L)).thenReturn(Optional.of(lotEntity));
        Lot lot = lotService.findById(1L);
        assertNotNull(lot);
        assertEquals(lot.getType() , LotType.TEMPORARIO );
    }

    @Test
    void saleLot() {

        lotEntity.setOccupiedBy(null);

        LotPriceEntity lotPriceEntity = new LotPriceEntity();
        lotPriceEntity.setPrice(BigDecimal.valueOf(1000));
        lotPriceEntity.setType(LotType.PRIVADO);
        lotPriceEntity.setSection(Section.AUTOS);

        lotEntity.setType(LotType.PRIVADO);
        when(lotRepository.findById(1L)).thenReturn(Optional.of(lotEntity));
        when(lotPriceRepository.findAllByTypeAndSectionAndActiveTrue(LotType.PRIVADO , Section.AUTOS)).thenReturn(lotPriceEntity);
        when(lotTraceRepository.save(any(LotTraceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(lotRepository.save(any(LotEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        LotTrace lotTrace = lotService.saleLot(1L, vehicle , LocalDateTime.now());
        assertNotNull(lotTrace);
        assertEquals(lotTrace.getAmount() , BigDecimal.valueOf(1000));
    }

    @Test
    void rentLot() {
        LotPriceEntity lotPriceEntity = new LotPriceEntity();
        lotPriceEntity.setPrice(BigDecimal.valueOf(1000));
        lotPriceEntity.setType(LotType.ALQUILER_MENSUAL);
        lotPriceEntity.setSection(Section.AUTOS);

        lotEntity.setType(LotType.ALQUILER_MENSUAL);
        when(lotRepository.findById(1L)).thenReturn(Optional.of(lotEntity));
        when(lotPriceRepository.findAllByTypeAndSectionAndActiveTrue(LotType.ALQUILER_MENSUAL,Section.AUTOS)).thenReturn(lotPriceEntity);
        when(lotTraceRepository.save(any(LotTraceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(lotRepository.save(any(LotEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        LotTrace lotTrace = lotService.rentLot(1L,vehicle,3,LocalDateTime.now());

        assertNotNull(lotTrace);
        assertEquals(lotTrace.getAmount() , BigDecimal.valueOf(3000));
        assertNull(lotTrace.getExitDateTime());
        assertEquals(lotTrace.getLotPrice().getPrice(), BigDecimal.valueOf(1000));
    }

    @Test
    void entryLot() {
        LotPriceEntity lotPriceEntity = new LotPriceEntity();
        lotPriceEntity.setPrice(BigDecimal.valueOf(1000));
        lotPriceEntity.setType(LotType.TEMPORARIO);
        lotPriceEntity.setSection(Section.AUTOS);

        lotEntity.setType(LotType.TEMPORARIO);
        when(lotRepository.findById(1L)).thenReturn(Optional.of(lotEntity));
        when(lotPriceRepository.findAllByTypeAndSectionAndActiveTrue(LotType.TEMPORARIO,Section.AUTOS)).thenReturn(lotPriceEntity);
        when(lotTraceRepository.save(any(LotTraceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(lotRepository.save(any(LotEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        LotTrace lotTrace = lotService.entryLot(1L,vehicle,LocalDateTime.now());

        assertNotNull(lotTrace);
        assertNull(lotTrace.getAmount());
        assertEquals(lotTrace.getLotPrice().getPrice(), BigDecimal.valueOf(1000));
        assertNull(lotTrace.getExitDateTime());

    }

    @Test
    void exitTrace() {
        LotPriceEntity lotPriceEntity = new LotPriceEntity();
        lotPriceEntity.setPrice(BigDecimal.valueOf(1000));
        lotPriceEntity.setType(LotType.TEMPORARIO);
        lotPriceEntity.setSection(Section.AUTOS);

        LocalDateTime entry = LocalDateTime.of(2024 ,12,16,18,0);
        lotEntity.setType(LotType.TEMPORARIO);
        lotEntity.setStatus(LotStatus.OCUPADO);
        lotEntity.getOccupiedBy().setEntryDateTime(entry);

        when(lotRepository.findById(1L)).thenReturn(Optional.of(lotEntity));
        when(lotPriceRepository.findAllByTypeAndSectionAndActiveTrue(LotType.TEMPORARIO,Section.AUTOS)).thenReturn(lotPriceEntity);
        when(lotTraceRepository.save(any(LotTraceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(lotRepository.save(any(LotEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        LotTrace lotTrace = lotService.exitTrace(1L,LocalDateTime.of(2024 ,12,16,21,5));

        assertNotNull(lotTrace);
        assertEquals(lotTrace.getAmount() , BigDecimal.valueOf(4000.0));
        assertEquals(lotTrace.getLotPrice().getPrice(), BigDecimal.valueOf(1000));
        assertNotNull(lotTrace.getExitDateTime());

    }
}