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
@RequestMapping("/api")
public class ShopController {

  final  ShopService shopService;

    @PostMapping("/shop")
    @ApiOperation(value = "Добавление магазина")
    public ShopDTO saveShop(@RequestBody ShopDTO shopDTO) {
        shopService.addShop(shopDTO);
        return shopDTO;
    }

    @GetMapping("/shop")
    @ApiOperation(value = "Получение всех магазинов")
    public List<ShopDTO> getAllShops(){
        List<ShopDTO>shops= shopService.getAllShops();
        return shops;
    }

    @DeleteMapping("/shop/{id}")
    @ApiOperation(value = "Удаление магазина по id")
    public String deleteShop(@PathVariable Long id){
        shopService.deleteById(id);
        return "Магазин с id="+id+" удален";
    }

    @GetMapping("/shop/{id}")
    @ApiOperation(value = "Получение магазина по id")
    public ShopDTO getById(@PathVariable Long id){
        ShopDTO shopDTO=shopService.getById(id);
        return shopDTO;
    }

    @PutMapping("/shop")
    @ApiOperation(value = "")
    public ShopDTO edit(@RequestBody ShopDTO shopDTO){
        shopService.edit(shopDTO);
        return shopDTO;
    }
}
