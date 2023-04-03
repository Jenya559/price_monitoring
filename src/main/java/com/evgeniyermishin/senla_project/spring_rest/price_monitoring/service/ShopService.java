package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ShopDTO;

import java.util.List;

public interface ShopService {

    ShopDTO addShop(ShopDTO shopDTO);

    List<ShopDTO> getAllShops();

    void deleteById(Long id);

    ShopDTO getById(Long id);

    ShopDTO edit(ShopDTO shopDTO);
}
