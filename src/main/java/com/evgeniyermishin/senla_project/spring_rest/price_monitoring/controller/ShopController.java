package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ShopDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "shop-rest-controller")
@RequestMapping("/api/v1/shop")
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Добавление магазина")
    public ResponseEntity<?> addShop(@RequestBody ShopDTO shopDTO) {
        try {
            shopService.addShop(shopDTO);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Магазин с названием " + shopDTO.getShopName() + " уже есть в БД", HttpStatus.OK);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Получение всех магазинов")
    public ResponseEntity<?> getAllShops() {
        List<ShopDTO> shops = shopService.getAllShops();
        return new ResponseEntity<>(shops, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Удаление магазина по id")
    public void deleteShop(@PathVariable Long id) {
        shopService.deleteById(id);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Получение магазина по id")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            ShopDTO shopDTO = shopService.getById(id);
            return new ResponseEntity<>(shopDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Магазин с id " + id + " не найден в БД", HttpStatus.OK);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @ApiOperation(value = "Изменение наименования магазина")
    public ResponseEntity<?> edit(@RequestBody ShopDTO shopDTO) {
        try {
            shopService.edit(shopDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Магазин с id " + shopDTO.getId() + " не существует", HttpStatus.OK);
        }
    }
}
