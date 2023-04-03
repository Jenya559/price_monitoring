package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findByShopName(String shopName);
}
