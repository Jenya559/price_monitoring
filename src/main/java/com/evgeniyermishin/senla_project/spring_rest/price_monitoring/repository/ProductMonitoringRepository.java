package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMonitoringRepository extends JpaRepository<ProductMonitoring, Long> {


    ProductMonitoring findProductMonitoringByProductAndShop(Product product, Shop shop);

    List<ProductMonitoring> findAllByProductNameOfProductAndShopShopName(String productName, String shopName);

    ProductMonitoring findTopByProductAndShopOrderByLocalDateTimeDesc(Product product, Shop shop);

    List<ProductMonitoring> findAllByProductNameOfProduct(String productName);
}
