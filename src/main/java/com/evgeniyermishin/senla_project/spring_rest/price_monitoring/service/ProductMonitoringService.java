package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service;


import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;

import java.util.List;

public interface ProductMonitoringService {

    ProductMonitoringDTO editPrice(ProductMonitoringDTO productMonitoringDTO);

    ProductMonitoringDTO addProductShopPrice(ProductMonitoringDTO productMonitoringDTO);

    List<ProductMonitoring> getAllPositions();

    List<ProductMonitoring> getAllByProductAndShop(String productName, String shopName);

   List<ProductMonitoringDTO> findAllByProductNameOfProduct(String productName);

}
