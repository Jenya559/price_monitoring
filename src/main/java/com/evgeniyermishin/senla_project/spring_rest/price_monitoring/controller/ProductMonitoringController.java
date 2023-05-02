package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.PeriodDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.MonitoringNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ProductNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ProductShopDuplicateException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ShopNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ProductMonitoringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@Api(tags = "product-monitoring-rest-controller")
@RequestMapping("/api/v1/monitoring")
public class ProductMonitoringController {

    private final ProductRepository productRepository;
    private final ProductMonitoringService productMonitoringService;

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Получение всех позиций для мониторинга цен")
    public ResponseEntity<?> getAllPositions() {
        List<ProductMonitoring> monitoringList = productMonitoringService.getAllPositions();
        return new ResponseEntity<>(monitoringList, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @ApiOperation(value = "Изменение цены на продукт в определённом магазине")
    public ResponseEntity<?> updatePrice(@RequestBody ProductMonitoringDTO productMonitoringDTO) {
        try {
            ProductMonitoringDTO productMonitoringDTO1 = productMonitoringService.editPrice(productMonitoringDTO);
            return new ResponseEntity<>(productMonitoringDTO1, HttpStatus.OK);
        } catch (ShopNotFoundException e) {
            return new ResponseEntity<>("Магазин " + productMonitoringDTO.getShop() + " не найден в БД", HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Продукт " + productMonitoringDTO.getProduct() + " не найден в БД", HttpStatus.OK);
        } catch (MonitoringNotFoundException e) {
            return new ResponseEntity<>("Позиция с продуктом " + productMonitoringDTO.getProduct() + " и магазином " + productMonitoringDTO.getShop() + " отсутствует", HttpStatus.OK);
        }

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Добавление позиции для мониторинга цен")
    public ResponseEntity<?> addProductShopPrice(@RequestBody ProductMonitoringDTO productMonitoringDTO) {
        try {
            ProductMonitoringDTO productMonitoringDTO1 = productMonitoringService.addProductShopPrice(productMonitoringDTO);
            return new ResponseEntity<>(productMonitoringDTO1, HttpStatus.OK);
        } catch (ShopNotFoundException e) {
            return new ResponseEntity<>("Магазин " + productMonitoringDTO.getShop() + " не найден в БД", HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Продукт " + productMonitoringDTO.getProduct() + " не найден в БД", HttpStatus.OK);
        } catch (ProductShopDuplicateException e) {
            return new ResponseEntity<>("Позиция с продуктом " + productMonitoringDTO.getProduct() + " и магазином " + productMonitoringDTO.getShop() + " уже есть в БД", HttpStatus.OK);
        }
    }

    @GetMapping("/dynamic")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Динамика изменения цен")
    public ResponseEntity<?> getAllByProductAndShop(@RequestParam String productName, @RequestParam String shopName) {
        try {
            List<ProductMonitoring> list = productMonitoringService.getAllByProductAndShop(productName, shopName);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Продукт " + productName + " не найден в БД", HttpStatus.OK);
        } catch (ShopNotFoundException e) {
            return new ResponseEntity<>("Магазин " + shopName + " не найден в БД", HttpStatus.OK);
        } catch (MonitoringNotFoundException e) {
            return new ResponseEntity<>("Позиций с магазином " + shopName + " и продуктом " + productName + " не найдено", HttpStatus.OK);
        }
    }

    @GetMapping("/price_compare")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Сравнение цен по позициям в различных магазинах")
    public ResponseEntity<?> getCompareBetweenShops(@RequestParam String productName) {
        try {
            Set<ProductMonitoringDTO> productMonitoringDTOSet = productMonitoringService.findAllByProductNameOfProduct(productName);
            return new ResponseEntity<>(productMonitoringDTOSet, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Продукт " + productName + " не найден в БД", HttpStatus.OK);
        } catch (MonitoringNotFoundException e) {
            return new ResponseEntity<>("Позиция с продуктом " + productName + "не найден в БД", HttpStatus.OK);
        }
    }


    @PostMapping("/dynamic")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Динамика изменения цен за определенный период ")
    public ResponseEntity<?> getAllByProductAndShopBetweenLocalDate
            (@RequestBody PeriodDTO periodDTO) {
        try {
            List<ProductMonitoring> list = productMonitoringService.getAllByProductAndShopBetweenLocalDate(periodDTO);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (MonitoringNotFoundException e) {
            return new ResponseEntity<>("Позиции с продуктом " + periodDTO.getNameOfProduct() +
                                        " и магазином " + periodDTO.getShopName() +
                                        " с датами добавления " + periodDTO.getStartDate() +
                                        " - " + periodDTO.getEndDate() +
                                        " не найдены в БД", HttpStatus.OK);
        }
    }
}
