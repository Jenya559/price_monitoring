package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.PeriodDTO;
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
@RequestMapping("/api/v1/monitoring")
public class ProductMonitoringController {

   private final ProductRepository productRepository;
   private final ProductMonitoringService productMonitoringService;

    @GetMapping
    @ApiOperation(value = "Получение всех позиций для мониторинга цен")
    public List<ProductMonitoring> getAllPositions() {
        List<ProductMonitoring> monitoringList = productMonitoringService.getAllPositions();
        return monitoringList;
    }

    @PutMapping
    @ApiOperation(value = "Изменение цены на продукт в определённом магазине")
    public ProductMonitoringDTO updatePrice(@RequestBody ProductMonitoringDTO productMonitoringDTO) {
        productMonitoringService.editPrice(productMonitoringDTO);
        return productMonitoringDTO;
    }

    @PostMapping
    @ApiOperation(value = "Добавление позиции для мониторинга цен")
    public ProductMonitoringDTO addProductShopPrice(@RequestBody ProductMonitoringDTO productMonitoringDTO) {
        productMonitoringService.addProductShopPrice(productMonitoringDTO);
        return productMonitoringDTO;
    }

    @GetMapping("/dynamic/{productName}/{shopName}")
    @ApiOperation(value = "Динамика изменения цен")
    public List<ProductMonitoring> getAllByProductAndShop(@PathVariable String productName, @PathVariable String shopName) {
        return productMonitoringService.getAllByProductAndShop(productName, shopName);
    }

    @GetMapping("/price_compare/{productName}")
    @ApiOperation(value = "Сравнение цен по позициям в различных магазинах")
    public Set<ProductMonitoringDTO> getCompareBetweenShops(@PathVariable String productName) {
        return productMonitoringService.findAllByProductNameOfProduct(productName);
    }


    @PostMapping("/period")
    @ApiOperation(value = "Динамика изменения цен за определенный период ")
    public List<ProductMonitoring> getAllByProductAndShopBetweenLocalDate
            (@RequestBody PeriodDTO periodDTO) {
        return productMonitoringService.getAllByProductAndShopBetweenLocalDate(periodDTO);
    }

}
