package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.clients.VehicleRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lc.iv.models.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestExamen {

    // List<Lot> lotsTest = objectMapper.readValue(ResourceUtils.getFile("classpath:lots.json"), new TypeReference<>(){});

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleRestClient vehicleRestClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void pingShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/ping"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }

    @Test
    void lotsShouldReturnLotListWhitVehicles() throws Exception {
        Vehicle vehicle = objectMapper.readValue(ResourceUtils.getFile("classpath:vehicle15.json"), Vehicle.class);
        when(vehicleRestClient.getVehicleById(anyString())).thenReturn(ResponseEntity.ok(vehicle));
        MvcResult result = this.mockMvc.perform(get("/lots")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<Lot> lots = objectMapper.readValue(json, new TypeReference<>(){});
        Assertions.assertEquals(186, lots.size());
        Vehicle vehicle15 = objectMapper.readValue(ResourceUtils.getFile("classpath:vehicle15.json"), Vehicle.class);
        Assertions.assertEquals(vehicle15, lots.stream().filter(lot -> lot.getId().equals(15L)).findFirst().get().getOccupiedBy().getVehicle());
    }

    // Sale Tests
    @Test
    void saleShouldReturnLotUnavailable() throws Exception {
        Vehicle vehicle = objectMapper.readValue(ResourceUtils.getFile("classpath:vehicle15.json"), Vehicle.class);
        when(vehicleRestClient.getVehicleById("SL204VJ")).thenReturn(ResponseEntity.ok(vehicle));
        MvcResult result = this.mockMvc.perform(post("/lots/149/sale?entry_date_time=2024-01-01 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Lot id 149 is already occupied.", error.getMessage());
    }

    @Test
    void saleShouldReturnLotSectionNotMatch() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/134/sale?entry_date_time=2024-01-01 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Vehicle type does not match lot section.", error.getMessage());
    }

    @Test
    void saleShouldReturnLotIsNotForSale() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/100/sale?entry_date_time=2024-01-01 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Lot id 100 is not for sale.", error.getMessage());
    }

    @Test
    void saleShouldReturnSuccessful() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/144/sale?entry_date_time=2024-01-01 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        LotTrace trace = objectMapper.readValue(json, LotTrace.class);
        Assertions.assertEquals("AD045FT", trace.getVehicle().getId());
        Assertions.assertEquals(new BigDecimal("10000000.00"), trace.getAmount());
        Assertions.assertEquals(new BigDecimal("10000000.00"), trace.getLotPrice().getPrice());
        Assertions.assertEquals(LotType.PRIVADO, trace.getLotPrice().getType());
        Assertions.assertEquals(Section.AUTOS, trace.getLotPrice().getSection());
        Assertions.assertEquals(LocalDateTime.of(2024, 1, 1, 0,0,0), trace.getEntryDateTime());
        Assertions.assertNull(trace.getExitDateTime());
    }


    // RENT Tests
    @Test
    void rentShouldReturnLotUnavailable() throws Exception {
        Vehicle vehicle = objectMapper.readValue(ResourceUtils.getFile("classpath:vehicle15.json"), Vehicle.class);
        when(vehicleRestClient.getVehicleById("AM900UP")).thenReturn(ResponseEntity.ok(vehicle));
        MvcResult result = this.mockMvc.perform(post("/lots/23/rent?entry_date_time=2024-01-01 00:00:00&months=3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Lot id 23 is already occupied.", error.getMessage());
    }

    @Test
    void rentShouldReturnLotSectionNotMatch() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/50/rent?entry_date_time=2024-01-01 00:00:00&months=3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Vehicle type does not match lot section.", error.getMessage());
    }

    @Test
    void rentShouldReturnLotIsNotForRent() throws Exception {
        Vehicle vehicle = objectMapper.readValue(ResourceUtils.getFile("classpath:vehicle15.json"), Vehicle.class);
        when(vehicleRestClient.getVehicleById("AM900UP")).thenReturn(ResponseEntity.ok(vehicle));
        MvcResult result = this.mockMvc.perform(post("/lots/100/rent?entry_date_time=2024-01-01 00:00:00&months=3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Lot id 100 is not for rent.", error.getMessage());
    }

    @Test
    void rentShouldReturnSuccessful() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/40/rent?entry_date_time=2024-01-01 00:00:00&months=3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        LotTrace trace = objectMapper.readValue(json, LotTrace.class);
        Assertions.assertEquals("AD045FT", trace.getVehicle().getId());
        Assertions.assertEquals(new BigDecimal("150000.00"), trace.getAmount());
        Assertions.assertEquals(new BigDecimal("50000.00"), trace.getLotPrice().getPrice());
        Assertions.assertEquals(LotType.ALQUILER_MENSUAL, trace.getLotPrice().getType());
        Assertions.assertEquals(Section.AUTOS, trace.getLotPrice().getSection());
        Assertions.assertEquals(LocalDateTime.of(2024, 1, 1, 0,0,0), trace.getEntryDateTime());
        Assertions.assertNull(trace.getExitDateTime());
    }

    // Entry Tests
    @Test
    void entryShouldReturnLotUnavailable() throws Exception {
        Vehicle vehicle = objectMapper.readValue(ResourceUtils.getFile("classpath:vehicle15.json"), Vehicle.class);
        when(vehicleRestClient.getVehicleById("TM900UP")).thenReturn(ResponseEntity.ok(vehicle));
        MvcResult result = this.mockMvc.perform(post("/lots/79/entry?entry_date_time=2024-01-01 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Lot id 79 is already occupied.", error.getMessage());
    }

    @Test
    void entryShouldReturnLotSectionNotMatch() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/70/entry?entry_date_time=2024-01-01 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Vehicle type does not match lot section.", error.getMessage());
    }

    @Test
    void entryShouldReturnLotIsNotForTemporary() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/150/entry?entry_date_time=2024-01-01 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ErrorApi error = objectMapper.readValue(json, ErrorApi.class);
        Assertions.assertEquals(400, error.getStatus());
        Assertions.assertEquals("Lot id 150 is not for temporary use.", error.getMessage());
    }

    @Test
    void entryShouldReturnSuccessful() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/lots/101/entry?entry_date_time=2024-01-01 00:00:00&months=3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                                "  \"id\": \"AD045FT\",\n" +
                                "  \"brand\": \"Lexus\",\n" +
                                "  \"model\": \"CT\",\n" +
                                "  \"color\": \"Blanco\",\n" +
                                "  \"type\": \"AUTOS\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        LotTrace trace = objectMapper.readValue(json, LotTrace.class);
        Assertions.assertEquals("AD045FT", trace.getVehicle().getId());
        Assertions.assertNull(trace.getAmount());
        Assertions.assertEquals(new BigDecimal("4500.00"), trace.getLotPrice().getPrice());
        Assertions.assertEquals(LotType.TEMPORARIO, trace.getLotPrice().getType());
        Assertions.assertEquals(Section.AUTOS, trace.getLotPrice().getSection());
        Assertions.assertEquals(LocalDateTime.of(2024, 1, 1, 0,0,0), trace.getEntryDateTime());
        Assertions.assertNull(trace.getExitDateTime());
    }
}