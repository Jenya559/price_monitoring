package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ProductMonitoringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@Api(tags = "product-monitoring-rest-controller")
@RequestMapping("/api")
public class ProductMonitoringController {

    final ProductRepository productRepository;
    final ProductMonitoringService productMonitoringService;

    @GetMapping("/monitoring")
    @ApiOperation(value = "Получение всех позиций для мониторинга цен")
    public List<ProductMonitoring> getAllPositions() {
        List<ProductMonitoring> monitoringList = productMonitoringService.getAllPositions();
        return monitoringList;
    }

    @PutMapping("/monitoring")
    @ApiOperation(value = "Изменение цены на продукт в определённом магазине")
    public ProductMonitoringDTO updatePrice(@RequestBody ProductMonitoringDTO productMonitoringDTO) {
        productMonitoringService.editPrice(productMonitoringDTO);
        return productMonitoringDTO;
    }

    @PostMapping("/monitoring")
    @ApiOperation(value = "Добавление позиции для мониторинга цен")
    public ProductMonitoringDTO addProductShopPrice(@RequestBody ProductMonitoringDTO productMonitoringDTO) {
        productMonitoringService.addProductShopPrice(productMonitoringDTO);
        return productMonitoringDTO;
    }

    @GetMapping("/monitoring/dynamic/{productName}/{shopName}")
    @ApiOperation(value = "Динамика изменения цен ")
    public List<ProductMonitoring> getAllByProductAndShop(@PathVariable String productName, @PathVariable String shopName) {
        return productMonitoringService.getAllByProductAndShop(productName, shopName);
    }

    @GetMapping("/monitoring/price_compare/{productName}")
    @ApiOperation(value = "Сравнение цен по позициям в различных магазинах")
    public Set<ProductMonitoringDTO> getCompareBetweenShops(@PathVariable String productName) {
        return productMonitoringService.findAllByProductNameOfProduct(productName);
    }
}