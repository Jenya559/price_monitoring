package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ShopDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl.ShopServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShopController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ShopControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ShopServiceImpl shopService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAddShop() throws Exception {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(1L);
        shopDTO.setShopName("Лента");
        Mockito.when(shopService.addShop(ArgumentMatchers.any())).thenReturn(shopDTO);
        String json = mapper.writeValueAsString(shopDTO);
        mockMvc.perform(post("/api/v1/shop").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testGetAllShops() throws Exception {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(1L);
        shopDTO.setShopName("Лента");

        ShopDTO shopDTO1 = new ShopDTO();
        shopDTO1.setId(2L);
        shopDTO1.setShopName("Магнит");

        List<ShopDTO> shopDTOList = new ArrayList<>();
        shopDTOList.add(shopDTO);
        shopDTOList.add(shopDTO1);
        Mockito.when(shopService.getAllShops()).thenReturn(shopDTOList);
        mockMvc.perform(get("/api/v1/shop")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].shopName", Matchers.equalTo("Лента")));

    }

    @Test
    public void testDeleteShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shop/{id}", 10001L))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(1L);
        shopDTO.setShopName("Лента");
        Mockito.when(shopService.getById(1L)).thenReturn(shopDTO);
        mockMvc.perform(get("/api/v1/shop/{id}", 1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.shopName", Matchers.equalTo("Лента")));
    }

    @Test
    public void testEdit() throws Exception {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(2L);
        shopDTO.setShopName("Лента");
        Mockito.when(shopService.edit(ArgumentMatchers.any())).thenReturn(shopDTO);
        String json = mapper.writeValueAsString(shopDTO);
        mockMvc.perform(put("/api/v1/shop").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
