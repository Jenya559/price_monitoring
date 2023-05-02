package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ShopDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ShopMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ShopServiceImplTest {
    @Mock
    private ShopMapper shopMapper;
    @Mock
    private ShopRepository shopRepository;
    @InjectMocks
    private ShopServiceImpl shopService;

    private Shop shop = new Shop(1L, "Лента");
    private Shop shop1 = new Shop(2L, "Магнит");

    @Test
    void addShopTest() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(1L);
        shopDTO.setShopName("Лента");

        when(shopRepository.findByShopName(shopDTO.getShopName())).thenReturn(null);
        when(shopMapper.toModel(shopDTO)).thenReturn(shop);
        when(shopRepository.saveAndFlush(any(Shop.class))).thenReturn(shop);
        shopService.addShop(shopDTO);

        verify(shopRepository).saveAndFlush(any(Shop.class));
    }

    @Test
    void getAllShopsTest() {
        List<Shop> shops = new ArrayList<>();
        shops.add(shop);
        shops.add(shop1);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(shop.getId());
        shopDTO.setShopName(shop.getShopName());

        ShopDTO shopDTO1 = new ShopDTO();
        shopDTO1.setId(shop1.getId());
        shopDTO1.setShopName(shop1.getShopName());

        List<ShopDTO> shopDTOS = new ArrayList<>();
        shopDTOS.add(shopDTO);
        shopDTOS.add(shopDTO1);

        when(shopRepository.findAll()).thenReturn(shops);
        when(shopMapper.toDTO(shops)).thenReturn(shopDTOS);
        List<ShopDTO> result = shopService.getAllShops();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void deleteByIdTest() {
        Long id = 123L;
        shopService.deleteById(id);

        verify(shopRepository).deleteById(id);
    }

    @Test
    void getShopByIdTest() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopName(shop.getShopName());
        shopDTO.setId(shop.getId());

        when(shopRepository.findById(shop.getId())).thenReturn(Optional.ofNullable(shop));
        when(shopMapper.toDTO(shop)).thenReturn(shopDTO);
        ShopDTO result = shopService.getById(1L);

        assertNotNull(result);
        assertEquals("Лента",result.getShopName());
    }

    @Test
    void editTest(){
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopName("Пятёрочка");
        shopDTO.setId(shop.getId());
        Shop shop2=new Shop(shopDTO.getId(),shopDTO.getShopName());

        when(shopRepository.findById(shopDTO.getId())).thenReturn(Optional.ofNullable(shop));
        when(shopMapper.toModel(shopDTO)).thenReturn(shop2);
        when(shopMapper.toDTO(shop2)).thenReturn(shopDTO);
        shopService.edit(shopDTO);

        verify(shopRepository).saveAndFlush(shop2);


    }
}

