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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StockServiceTest {

    @InjectMocks
    private StockService subject;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockMapper stockMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void getStocksPageable() {
        StockDto stockDto = mock(StockDto.class);
        Stock stock = mock(Stock.class);
        PageRequestDto request = PageRequestDto.builder()
                .page(0)
                .size(5)
                .build();

        List<Stock> stockEntities = Arrays.asList(stock);
        Pageable pageable = PageRequest.of(1, 5);
        Page<Stock> stocks = new PageImpl<>(stockEntities, pageable, 5);
        request.setPage(1);
        request.setSize(5);
        when(stockRepository.findAll(any(Pageable.class))).thenReturn(stocks);
        when(stockMapper.toStockDto(stock)).thenReturn(stockDto);
        // when(subject.getListStocks(request)).thenReturn(stockEntities);

        subject.getListStocks(request);

        verify(stockRepository).findAll(pageable);
        verify(stockMapper).toStockDto(stock);
    }

    @Test
    void getStockTest() {
        Long id = 1L;
        StockDto stockDto = mock(StockDto.class);
        Stock stock = mock(Stock.class);

        when(stockRepository.getStocksById(id)).thenReturn(Optional.ofNullable(stock));
        when(stockMapper.toStockDto(stock)).thenReturn(stockDto);

        subject.getStock(id);

        verify(stockRepository).getStocksById(id);
        verify(stockMapper).toStockDto(stock);
    }

    @Test
    void should_be_subjectShouldThrowStockNotFoundException() {
        Long id = 1L;
        Stock stock = null;

        when(stockRepository.getStocksById(id)).thenReturn(Optional.ofNullable(stock));

        Assertions.assertThrows(StockNotFoundException.class, () -> {
            subject.getStock(id);
        });
        verify(stockRepository).getStocksById(id);
    }

    @Test
    void updateStockTest() throws IOException {
        Long id = 1L;
        BigDecimal price = new BigDecimal(20);
        LocalDateTime timestamp = LocalDateTime.now();
        Stock stock = mock(Stock.class);
        Optional<Stock> stockOptional = Optional.ofNullable(stock);
        StockDto stockDto = mock(StockDto.class);
        Stock stock1 = mock(Stock.class);

        when(stockRepository.getStocksById(id)).thenReturn(stockOptional);
        assert stock != null;
        when(stockRepository.save(stock)).thenReturn(stock1);
        when(stockMapper.toStockDto(stock)).thenReturn(stockDto);

        subject.updatePriceStock(id, price);

        verify(stockRepository).save(stock);
        verify(stockMapper).toStockDto(stock1);

    }

    @Test
    void newStockTest() {
        StockDto stockDto = mock(StockDto.class);
        Stock stock = mock(Stock.class);


        when(stockMapper.toStock(stockDto)).thenReturn(stock);
        when(stockMapper.toStockDto(stock)).thenReturn(stockDto);
        when(stockRepository.save(stock)).thenReturn(stock);

        subject.newStock(stockDto);


        verify(stockMapper).toStockDto(stock);
        verify(stockMapper).toStock(stockDto);
        verify(stockRepository).save(stock);
    }

    @Test
    void deleteStock() {
        Long id = 1L;
        Stock stock = mock(Stock.class);
        when(stockRepository.getStocksById(id)).thenReturn(Optional.of(stock));
        subject.deleteStock(id);
        verify(stockRepository).delete(stock);
    }
}
