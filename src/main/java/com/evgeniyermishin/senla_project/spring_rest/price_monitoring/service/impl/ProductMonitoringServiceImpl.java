package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.PeriodDTO;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductMonitoringServiceImpl implements ProductMonitoringService {

    private final ProductMonitoringMapper productMonitoringMapper;
    private final ProductMonitoringRepository productMonitoringRepository;

    private final ProductMapper productMapper;

    private final ShopMapper shopMapper;

    private final ShopRepository shopRepository;

    private final ProductRepository productRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public ProductMonitoringDTO editPrice(ProductMonitoringDTO productMonitoringDTO) {
        Shop shop = shopRepository.findByShopName(productMonitoringDTO.getShop());
        if (shop == null) {
            log.warn("Данный магазин ({}) не найден в БД", productMonitoringDTO.getShop());
            throw new ShopNotFoundException("Данный магазин не найден в БД");
        }
        Product product = productRepository.findByNameOfProduct(productMonitoringDTO.getProduct());
        if (product == null) {
            log.warn("Данный продукт ({}) не найден в БД", productMonitoringDTO.getProduct());
            throw new ProductNotFoundException("Данный продукт не найден в БД");
        }
        ProductMonitoring maybeProductMonitoring = productMonitoringRepository.findTopByProductAndShopOrderByLocalDateTimeDesc(product, shop);
        if (maybeProductMonitoring == null) {
            log.warn("Позиция с продуктом ({}) и магазином ({}) отсутствует в БД", product.getNameOfProduct(), shop.getShopName());
            throw new MonitoringNotFoundException("Позиция отсутствует в БД");
        }
        ProductMonitoring productMonitoring = productMonitoringMapper.toModel(productMonitoringDTO);
        productMonitoring.setShop(shop);
        productMonitoring.setProduct(product);
        productMonitoringRepository.saveAndFlush(productMonitoring);
        log.info("Создана позиция c id ({}) с новой ценой ({}) ", productMonitoring.getId(), productMonitoring.getPrice());
        return productMonitoringMapper.toDTO(productMonitoring);
    }

    @Override
    public ProductMonitoringDTO addProductShopPrice(ProductMonitoringDTO productMonitoringDTO) {
        Shop shop = shopRepository.findByShopName(productMonitoringDTO.getShop());
        if (shop == null) {
            log.warn("Данный магазин ({}) не найден в БД", productMonitoringDTO.getShop());
            throw new ShopNotFoundException("Данный магазин не найден в БД");
        }
        Product product = productRepository.findByNameOfProduct(productMonitoringDTO.getProduct());
        if (product == null) {
            log.warn("Данный продукт ({}) не найден в БД", productMonitoringDTO.getProduct());
            throw new ProductNotFoundException("Данный продукт не найден в БД");
        }
        ProductMonitoring productMonitoring = productMonitoringRepository.findTopByProductAndShop(product, shop);
        if (productMonitoring != null) {
            log.warn("Позиция с продуктом ({}) и магазином ({}) уже есть в БД", product.getNameOfProduct(), shop.getShopName());
            throw new ProductShopDuplicateException("Данная позиция уже есть");
        }
        productMonitoring = productMonitoringMapper.toModel(productMonitoringDTO);
        productMonitoring.setProduct(product);
        productMonitoring.setShop(shop);
        productMonitoringRepository.saveAndFlush(productMonitoring);
        log.info("Позиция с продуктом ({}) и магазином ({}) добавлена в БД", product.getNameOfProduct(), shop.getShopName());
        return productMonitoringMapper.toDTO(productMonitoring);
    }

    @Override
    public List<ProductMonitoring> getAllPositions() {
        return productMonitoringRepository.findAll();

    }

    @Override
    public List<ProductMonitoring> getAllByProductAndShop(String productName, String shopName) {
        Product product = productRepository.findByNameOfProduct(productName);
        if (product == null) {
            log.warn("Данный продукт ({}) не найден в БД", productName);
            throw new ProductNotFoundException("Данный продукт не найден в БД");
        }
        Shop shop=shopRepository.findByShopName(shopName);
        if (shop == null) {
            log.warn("Данный магазин ({}) не найден в БД", shopName);
            throw new ShopNotFoundException("Данный магазин не найден в БД");
        }
        List<ProductMonitoring> productMonitoringList = productMonitoringRepository.findAllByProductNameOfProductAndShopShopName(productName, shopName);
        if (productMonitoringList.isEmpty()) {
            log.warn("Позиция с продуктом ({}) и магазином ({}) не найдена в БД", productName, shopName);
            throw new MonitoringNotFoundException("Данная позиция не найдена в БД");
        }
        return productMonitoringList;
    }

    @Override
    public Set<ProductMonitoringDTO> findAllByProductNameOfProduct(String productName) {
        Product maybeProduct=productRepository.findByNameOfProduct(productName);
        if (maybeProduct == null) {
            log.warn("Данный продукт ({}) не найден в БД", productName);
            throw new ProductNotFoundException("Данный продукт не найден в БД");
        }
        Set<ProductMonitoring> productMonitoring = productMonitoringRepository.findAllByProductNameOfProduct(productName);
        if (productMonitoring.isEmpty()) {
            log.warn("Позиция с продуктом ({}) не найдена в БД", productName);
            throw new MonitoringNotFoundException("Продукт не найден в БД");
        }
        Set<ProductMonitoringDTO> productMonitoringDTO = productMonitoringMapper.toDTO(productMonitoring);
        Set<ProductMonitoringDTO> dtoList = new HashSet<>();
        for (ProductMonitoringDTO list : productMonitoringDTO) {
            Shop shop = shopRepository.findByShopName(list.getShop());
            Product product = productRepository.findByNameOfProduct(list.getProduct());
            ProductMonitoring productMonitoring1 = productMonitoringRepository.findTopByProductAndShopOrderByLocalDateTimeDesc(product, shop);
            dtoList.add(productMonitoringMapper.toDTO(productMonitoring1));

        }
        return dtoList;
    }

    @Override
    public List<ProductMonitoring> getAllByProductAndShopBetweenLocalDate(PeriodDTO periodDTO) {
        LocalDateTime startDate = LocalDateTime.parse(periodDTO.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(periodDTO.getEndDate(), formatter);
        List<ProductMonitoring> productMonitoringList = productMonitoringRepository.findAllByProductNameOfProductAndShopShopNameAndLocalDateTimeBetween(periodDTO.getNameOfProduct(), periodDTO.getShopName(), startDate, endDate);
        if (productMonitoringList.isEmpty()) {
            log.warn("Позиция с продуктом ({}) и магазином ({}) с периодами ({}) - ({}) не найдена в БД", periodDTO.getNameOfProduct(), periodDTO.getShopName(), periodDTO.getStartDate(), periodDTO.getEndDate());
            throw new MonitoringNotFoundException("Данная позиция не найдена в БД");
        }
        return productMonitoringList;


    }
}