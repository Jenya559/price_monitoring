package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {ShopMapper.class, ProductMapper.class})
public interface ProductMonitoringMapper {

    @Mapping(source = "product.nameOfProduct",target = "product")
    @Mapping(source = "shop.shopName",target="shop")
    ProductMonitoringDTO toDTO(ProductMonitoring productMonitoring);

    @Mapping(source = "product",target = "product.nameOfProduct")
    @Mapping(source = "shop",target="shop.shopName")
    ProductMonitoring toModel(ProductMonitoringDTO productMonitoringDTO);

    @Mapping(source = "product.nameOfProduct",target = "product")
    @Mapping(source = "shop.shopName",target="shop")
    @Mapping(source = "localDateTime",target = "localDateTime",dateFormat = "yyyy-MM-dd HH:mm")
    List<ProductMonitoringDTO> toDTO(List<ProductMonitoring>productMonitoring);

    @Mapping(source = "product.nameOfProduct",target = "product")
    @Mapping(source = "shop.shopName",target="shop")
    @Mapping(source = "localDateTime",target = "localDateTime",dateFormat = "yyyy-MM-dd HH:mm")
    Set<ProductMonitoringDTO> toDTO(Set<ProductMonitoring>productMonitoring);


}
