package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ProductMonitoringRepository extends JpaRepository<ProductMonitoring, Long> {


    ProductMonitoring findTopByProductAndShop(Product product, Shop shop);

    List<ProductMonitoring> findAllByProductNameOfProductAndShopShopName(String productName, String shopName);

    ProductMonitoring findTopByProductAndShopOrderByLocalDateTimeDesc(Product product, Shop shop);

    Set<ProductMonitoring> findAllByProductNameOfProduct(String productName);

//    @Query(value = "SELECT p from ProductMonitoring p where" +
//                   " p.product.nameOfProduct=:productName and" +
//                   " p.shop.shopName=:shopName and p.localDateTime >:start and p.localDateTime <:end")
//    List<ProductMonitoring>findAllByProductNameOfProductAndShopShopNameAndLocalDateTime(
//            @Param("productName") String productName, @Param("shopName") String shopName,
//            @Param("start")  LocalDateTime start, @Param("end")LocalDateTime end);

    List<ProductMonitoring>findAllByProductNameOfProductAndShopShopNameAndLocalDateTimeBetween(String productName, String shopName, LocalDateTime start,LocalDateTime end);
}
