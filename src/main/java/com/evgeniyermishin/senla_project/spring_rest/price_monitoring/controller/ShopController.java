package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ShopDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "shop-rest-controller")
@RequestMapping("/api/v1/shop")
public class ShopController {

 private final  ShopService shopService;

    @PostMapping
    @ApiOperation(value = "Добавление магазина")
    public ShopDTO addShop(@RequestBody ShopDTO shopDTO) {
        shopService.addShop(shopDTO);
        return shopDTO;
    }

    @GetMapping
    @ApiOperation(value = "Получение всех магазинов")
    public List<ShopDTO> getAllShops(){
        List<ShopDTO>shops= shopService.getAllShops();
        return shops;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление магазина по id")
    public void deleteShop(@PathVariable Long id){
        shopService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение магазина по id")
    public ShopDTO getById(@PathVariable Long id){
        ShopDTO shopDTO=shopService.getById(id);
        return shopDTO;
    }

    @PutMapping
    @ApiOperation(value = "Изменение наименования магазина")
    public ShopDTO edit(@RequestBody ShopDTO shopDTO){
        shopService.edit(shopDTO);
        return shopDTO;
    }
}
