package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ShopDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ShopMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ShopNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ShopRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    final ShopMapper shopMapper;
    final ShopRepository shopRepository;

    @Override
    public ShopDTO addShop(ShopDTO shopDTO) {
        Shop shop = shopMapper.toModel(shopDTO);
        shopRepository.saveAndFlush(shop);
        log.info("Магазин " + shop.getShopName() + " сохранен");
        return shopDTO;
    }

    @Override
    public List<ShopDTO> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        return shopMapper.toDTO(shops);
    }

    @Override
    public void deleteById(Long id) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop == null) {
            log.warn("Магазина с данным id не существует");
            throw new ShopNotFoundException("Магазина с данным id не существует");
        }
        shopRepository.deleteById(id);
        log.info("Магазин удалён");
    }

    @Override
    public ShopDTO getById(Long id) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop == null) {
            log.warn("Магазина с данным id не существует");
            throw new ShopNotFoundException("Магазина с данным id не существует");
        }
        return shopMapper.toDTO(shop);
    }

    @Override
    public ShopDTO edit(ShopDTO shopDTO) {
        Shop maybeShop = shopRepository.findById(shopDTO.getId()).orElse(null);
        if (maybeShop == null) {
            log.warn("Данного магазина не существует");
            throw new ShopNotFoundException("Данного магазина не существует");
        }
        Shop shop = shopMapper.toModel(shopDTO);
        shopRepository.saveAndFlush(shop);
        log.info("Магазин " + shop.getShopName() + " изменён");
        return shopMapper.toDTO(shop);
    }

}
