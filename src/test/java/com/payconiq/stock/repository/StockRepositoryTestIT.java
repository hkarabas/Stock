package com.payconiq.stock.repository;

import com.payconiq.stock.entity.Stock;
import com.payconiq.stock.exception.StockNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
class StockRepositoryTestIT {

    @Autowired
    StockRepository stockRepository;

    @Test
    void newStock() {

        BigDecimal price = new BigDecimal(300);
        String name = "Coat XL";

        Stock stock = Stock.builder()
                .currentPrice(price)
                .name(name)
                .lastUpdate(LocalDateTime.now()).build();

        Stock stock1 = stockRepository.save(stock);
        Assertions.assertEquals(stock1.getName(), name);
        Assertions.assertEquals(stock1.getCurrentPrice(), price);
    }

    @Test
    void updateStock() {
        Long id = 1L;
        BigDecimal price = new BigDecimal(200);
        Optional<Stock> stockOptional = stockRepository.getStocksById(id);
        Stock stock = stockOptional.get();
        stock.setCurrentPrice(price);
        Stock stock1 = stockRepository.save(stock);
        Assertions.assertEquals(stock1.getCurrentPrice(), price);
    }

    @Test
    void deleteStock() {
        Long id = 1L;
        Optional<Stock> stockOptional = stockRepository.getStocksById(id);
        Stock stock = stockOptional.get();
        stockRepository.delete(stock);
        Optional<Stock> stockOptional2 = stockRepository.getStocksById(id);
        Assertions.assertFalse(stockOptional2.isPresent());
    }

    @Test
    void throw_stock_found_exception() {
        Long id = -1L;
        Assertions.assertThrows(StockNotFoundException.class,
                () -> {
                    stockRepository.getStocksById(id).orElseThrow(() -> new StockNotFoundException(id));
                });
    }

    @Test
    void get_stock_byId() {
        Long id = 1L;
        Optional<Stock> stockOptional = stockRepository.getStocksById(id);
        Assertions.assertTrue(stockOptional.isPresent());
        Assertions.assertEquals(stockOptional.get().getId(), id);
    }

    @Test
    void get_list_pegable() {
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Stock> page1 = stockRepository.findAll(pageable);
        Assertions.assertEquals(page1.getContent().size(), size);
    }


}
