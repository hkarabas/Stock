package com.payconiq.stock.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stock.entity.Stock;
import com.payconiq.stock.exception.StockNotFoundException;
import com.payconiq.stock.mapper.StockMapper;
import com.payconiq.stock.model.PageRequestDto;
import com.payconiq.stock.model.StockDto;
import com.payconiq.stock.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
class StockServiceTestIT {

    @Autowired
    StockService stockService;

    @Mock
    StockRepository stockRepository;

    @Mock
    StockMapper stockMapper;

    @Mock
    ObjectMapper objectMapper;

    @Test
    public void getStock() {
        Long id = 1L;
        StockDto stockDto = stockService.getStock(id);
        Assertions.assertEquals(id, stockDto.getId());
    }

    @Test
    public void getStock_StockNotFoundException() {
        Long id = -1L;
        Assertions.assertThrows(StockNotFoundException.class,
                () -> {
                    stockService.getStock(id);
                });
    }

    @Test
    void get_list_pegable() {
        int page = 0;
        int size = 5;
        PageRequestDto pageRequest = PageRequestDto.builder()
                .size(size)
                .page(page)
                .build();
        List<StockDto> stockDtoList = stockService.getListStocks(pageRequest).toList();
        Assertions.assertEquals(stockDtoList.size(), size);
    }

    @Test
    void newStock() {

        BigDecimal price = new BigDecimal(300);
        String name = "Coat XL";

        StockDto stockDto = StockDto.builder()
                .currentPrice(price)
                .name(name)
                .lastUpdate(LocalDateTime.now()).build();

        StockDto stockDto11 = stockService.newStock(stockDto);
        Assertions.assertEquals(stockDto11.getName(), name);
        Assertions.assertEquals(stockDto11.getCurrentPrice(), price);
    }

    @Test
    void updateStock() {
        Long id = 1L;
        BigDecimal price = new BigDecimal(200);
        StockDto stockDto = stockService.updatePriceStock(id, price);
        Assertions.assertEquals(stockDto.getCurrentPrice(), price);
    }

    @Test
    void deleteStock() {
        Long id = 1L;
        stockService.deleteStock(id);
        Optional<Stock> stockOptional2 = stockRepository.getStocksById(id);
        Assertions.assertFalse(stockOptional2.isPresent());
    }


}
