package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMonitoringMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ShopMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.MonitoringNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ProductNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ProductShopDuplicateException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ShopNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductMonitoringRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ShopRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ProductMonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductMonitoringServiceImpl implements ProductMonitoringService {

    final ProductMonitoringMapper productMonitoringMapper;
    final ProductMonitoringRepository productMonitoringRepository;

    final ProductMapper productMapper;

    final ShopMapper shopMapper;

    final ShopRepository shopRepository;

    final ProductRepository productRepository;

    @Override
    public ProductMonitoringDTO editPrice(ProductMonitoringDTO productMonitoringDTO) {
        Shop shop = shopRepository.findByShopName(productMonitoringDTO.getShop());
        Product product = productRepository.findByNameOfProduct(productMonitoringDTO.getProduct());
        ProductMonitoring maybeProductMonitoring = productMonitoringRepository.findTopByProductAndShopOrderByLocalDateTimeDesc(product, shop);
        if (maybeProductMonitoring == null) {
            log.warn("Данная позиция отсутствует в списке");
            throw new MonitoringNotFoundException("Данная позиция отсутствует в списке");
        }
        ProductMonitoring productMonitoring = productMonitoringMapper.toModel(productMonitoringDTO);
        productMonitoring.setShop(shop);
        productMonitoring.setProduct(product);
        productMonitoringRepository.saveAndFlush(productMonitoring);
        log.info("Добавлена запись с новой ценой продукта");
        return productMonitoringMapper.toDTO(productMonitoring);
    }

    @Override
    public ProductMonitoringDTO addProductShopPrice(ProductMonitoringDTO productMonitoringDTO) {
        Shop shop = shopRepository.findByShopName(productMonitoringDTO.getShop());
        Product product = productRepository.findByNameOfProduct(productMonitoringDTO.getProduct());
        if (product == null) {
            log.warn("Данного продукта нет в списке продуктов");
            throw new ProductNotFoundException("Данного продукта нет в списке продуктов");
        }
        if (shop == null) {
            log.warn("Данного магазина нет в списке магазинов");
            throw new ShopNotFoundException("Данного магазина нет в списке магазинов");
        }
        ProductMonitoring productMonitoring = productMonitoringRepository.findProductMonitoringByProductAndShop(product, shop);
        if (productMonitoring != null) {
            log.warn("Такой продукт уже есть в магазине");
            throw new ProductShopDuplicateException("Такой продукт уже есть в магазине");
        }
        productMonitoring = productMonitoringMapper.toModel(productMonitoringDTO);
        productMonitoring.setProduct(product);
        productMonitoring.setShop(shop);
        productMonitoringRepository.saveAndFlush(productMonitoring);
        log.info("Продукт добавлен в магазин/назначена цена");
        return productMonitoringMapper.toDTO(productMonitoring);
    }

    @Override
    public List<ProductMonitoring> getAllPositions() {
        return productMonitoringRepository.findAll();

    }

    @Override
    public List<ProductMonitoring> getAllByProductAndShop(String productName, String shopName) {
        List<ProductMonitoring> productMonitoringList = productMonitoringRepository.findAllByProductNameOfProductAndShopShopName(productName, shopName);
        if (productMonitoringList == null) {
            log.warn("Данная позиция отсутствует в списке");
            throw new MonitoringNotFoundException("Данная позиция отсутствует в списке");
        }
        return productMonitoringList;
    }

    @Override
    public List<ProductMonitoringDTO> findAllByProductNameOfProduct(String productName) {
        List<ProductMonitoring> productMonitoring = productMonitoringRepository.findAllByProductNameOfProduct(productName);
        List<ProductMonitoringDTO> productMonitoringDTO = productMonitoringMapper.toDTO(productMonitoring);
        List<ProductMonitoringDTO>dtoList=new ArrayList<>();
        for (ProductMonitoringDTO list : productMonitoringDTO) {
            Shop shop = shopRepository.findByShopName(list.getShop());
            Product product = productRepository.findByNameOfProduct(list.getProduct());
            ProductMonitoring productMonitoring1 = productMonitoringRepository.findTopByProductAndShopOrderByLocalDateTimeDesc(product, shop);
            dtoList.add(productMonitoringMapper.toDTO(productMonitoring1));
        }
        return  dtoList;
    }


}
