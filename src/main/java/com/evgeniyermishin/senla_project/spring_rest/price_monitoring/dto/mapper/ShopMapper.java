package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ShopDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    ShopDTO toDTO(Shop shop);

    Shop toModel(ShopDTO shopDTO);

    List<ShopDTO>toDTO(List<Shop>shops);
}
