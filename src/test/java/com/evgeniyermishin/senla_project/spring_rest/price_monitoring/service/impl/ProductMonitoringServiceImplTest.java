package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.PeriodDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductMonitoringDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMonitoringMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Category;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ProductMonitoring;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Shop;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductMonitoringRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductMonitoringServiceImplTest {
    @Mock
    private ProductMonitoringMapper productMonitoringMapper;
    @Mock
    private ProductMonitoringRepository productMonitoringRepository;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductMonitoringServiceImpl productMonitoringService;

    String categoryName = "Cars";

    private Category category = new Category(1L, categoryName);

    private Shop shop = new Shop(1L, "Авито");

    private Shop shop1 = new Shop(2L, "Авто.ру");

    private Product product = new Product(1L, "BMW", category);

    private ProductMonitoring productMonitoring = new ProductMonitoring(1L, 50000, product, shop);

    private ProductMonitoring productMonitoring1 = new ProductMonitoring(3L, 10000, product, shop1);

    private ProductMonitoring productMonitoring3 = new ProductMonitoring(4L, 60000, product, shop);

    @Test
    void editPriceTest() {
        ProductMonitoringDTO productMonitoringDTO = new ProductMonitoringDTO();
        productMonitoringDTO.setProduct(productMonitoring.getProduct().getNameOfProduct());
        productMonitoringDTO.setShop(productMonitoring.getShop().getShopName());
        productMonitoringDTO.setPrice(60000);
        ProductMonitoring productMonitoring1 = new ProductMonitoring(2L, 60000, product, shop);


        when(shopRepository.findByShopName(productMonitoringDTO.getShop())).thenReturn(shop);
        when(productRepository.findByNameOfProduct(productMonitoringDTO.getProduct())).thenReturn(product);
        when(productMonitoringRepository.findTopByProductAndShopOrderByLocalDateTimeDesc(product, shop)).thenReturn(productMonitoring);
        when(productMonitoringMapper.toModel(productMonitoringDTO)).thenReturn(productMonitoring1);
        when(productMonitoringMapper.toDTO(productMonitoring1)).thenReturn(productMonitoringDTO);
        productMonitoringService.editPrice(productMonitoringDTO);

        verify(productMonitoringRepository).saveAndFlush(productMonitoring1);
    }

    @Test
    void addProductShopPriceTest() {
        ProductMonitoringDTO productMonitoringDTO = new ProductMonitoringDTO();
        productMonitoringDTO.setProduct(productMonitoring1.getProduct().getNameOfProduct());
        productMonitoringDTO.setShop(productMonitoring1.getShop().getShopName());
        productMonitoringDTO.setPrice(productMonitoring1.getPrice());

        when(shopRepository.findByShopName(productMonitoringDTO.getShop())).thenReturn(shop1);
        when(productRepository.findByNameOfProduct(productMonitoringDTO.getProduct())).thenReturn(product);
        when(productMonitoringRepository.findTopByProductAndShop(product, shop1)).thenReturn(null);
        when(productMonitoringMapper.toModel(productMonitoringDTO)).thenReturn(productMonitoring1);
        when(productMonitoringMapper.toDTO(productMonitoring1)).thenReturn(productMonitoringDTO);
        productMonitoringService.addProductShopPrice(productMonitoringDTO);

        verify(productMonitoringRepository).saveAndFlush(productMonitoring1);

    }

    @Test
    void getAllPositionsTest() {
        List<ProductMonitoring> monitoringList = new ArrayList<>();
        monitoringList.add(productMonitoring);
        monitoringList.add(productMonitoring1);

        when(productMonitoringRepository.findAll()).thenReturn(monitoringList);
        List<ProductMonitoring> result = productMonitoringService.getAllPositions();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllByProductAndShopTest() {
        List<ProductMonitoring> productMonitoringList = new ArrayList<>();
        productMonitoringList.add(productMonitoring);
        productMonitoringList.add(productMonitoring3);

        when(productRepository.findByNameOfProduct(product.getNameOfProduct())).thenReturn(product);
        when(shopRepository.findByShopName(shop.getShopName())).thenReturn(shop);
        when(productMonitoringRepository.findAllByProductNameOfProductAndShopShopName(product.getNameOfProduct(), shop.getShopName())).thenReturn(productMonitoringList);
        List<ProductMonitoring> result = productMonitoringService.getAllByProductAndShop(product.getNameOfProduct(), shop.getShopName());

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findAllByProductNameOfProductTest() {
        Set<ProductMonitoring> productMonitoringSet = new HashSet<>();
        productMonitoringSet.add(productMonitoring);
        productMonitoringSet.add(productMonitoring1);

        ProductMonitoringDTO productMonitoringDTO = new ProductMonitoringDTO();
        productMonitoringDTO.setId(productMonitoring.getId());
        productMonitoringDTO.setProduct(productMonitoring.getProduct().getNameOfProduct());
        productMonitoringDTO.setShop(productMonitoring.getShop().getShopName());
        productMonitoringDTO.setPrice(productMonitoring.getPrice());

        ProductMonitoringDTO productMonitoringDTO1 = new ProductMonitoringDTO();
        productMonitoringDTO1.setId(productMonitoring1.getId());
        productMonitoringDTO1.setProduct(productMonitoring1.getProduct().getNameOfProduct());
        productMonitoringDTO1.setShop(productMonitoring1.getShop().getShopName());
        productMonitoringDTO1.setPrice(productMonitoring1.getPrice());

        Set<ProductMonitoringDTO> productMonitoringDTOSet = new HashSet<>();
        productMonitoringDTOSet.add(productMonitoringDTO);
        productMonitoringDTOSet.add(productMonitoringDTO1);

        when(productRepository.findByNameOfProduct(product.getNameOfProduct())).thenReturn(product);
        when(productMonitoringRepository.findAllByProductNameOfProduct(product.getNameOfProduct())).thenReturn(productMonitoringSet);
        when(productMonitoringMapper.toDTO(productMonitoringSet)).thenReturn(productMonitoringDTOSet);
        when(shopRepository.findByShopName(shop.getShopName())).thenReturn(shop);
        when(productRepository.findByNameOfProduct(product.getNameOfProduct())).thenReturn(product);
        when(productMonitoringRepository.findTopByProductAndShopOrderByLocalDateTimeDesc(product, shop)).thenReturn(productMonitoring);
        when(productMonitoringMapper.toDTO(productMonitoring)).thenReturn(productMonitoringDTO);
        Set<ProductMonitoringDTO> result = productMonitoringService.findAllByProductNameOfProduct(product.getNameOfProduct());

        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    void getAllByProductAndShopBetweenLocalDateTest() {
        PeriodDTO periodDTO = new PeriodDTO();
        periodDTO.setNameOfProduct(product.getNameOfProduct());
        periodDTO.setShopName(shop.getShopName());

        periodDTO.setStartDate("2021-05-30 16:30");
        periodDTO.setEndDate("2022-05-30 16:30");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime creationDate = LocalDateTime.parse("2021-06-30 16:30", formatter);
        LocalDateTime creationDate1 = LocalDateTime.parse("2023-06-30 16:30", formatter);
        LocalDateTime startDate = LocalDateTime.parse(periodDTO.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(periodDTO.getEndDate(), formatter);

        ProductMonitoring productMonitoring5 = new ProductMonitoring(product, shop, creationDate);
        ProductMonitoring productMonitoring6 = new ProductMonitoring(product, shop, creationDate1);
        List<ProductMonitoring> productMonitoringList = new ArrayList<>();
        productMonitoringList.add(productMonitoring5);
        productMonitoringList.add(productMonitoring6);
        List<ProductMonitoring> filterList = productMonitoringList.stream().filter(p -> p.getLocalDateTime().isBefore(endDate) && p.getLocalDateTime().isAfter(startDate)).collect(Collectors.toList());

        when(productMonitoringRepository.findAllByProductNameOfProductAndShopShopNameAndLocalDateTimeBetween(periodDTO.getNameOfProduct(), periodDTO.getShopName(), startDate, endDate)).thenReturn(filterList);

        List<ProductMonitoring> result = productMonitoringService.getAllByProductAndShopBetweenLocalDate(periodDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
