package com.payconiq.stock.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stock.entity.Stock;
import com.payconiq.stock.exception.StockNotFoundException;
import com.payconiq.stock.handler.Loggable;
import com.payconiq.stock.mapper.StockMapper;
import com.payconiq.stock.model.PageRequestDto;
import com.payconiq.stock.model.StockDto;
import com.payconiq.stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Loggable
public class StockService {

    private StockRepository stockRepository;
    private StockMapper stockMapper;
    private ObjectMapper objectMapper;

    public StockDto getStock(Long id) {
        Stock stock = stockRepository.getStocksById(id).orElseThrow(() -> new StockNotFoundException(id));
        System.out.println(stock);
        return stockMapper.toStockDto(stock);
    }

    @SneakyThrows
    @Transactional
    public StockDto updatePriceStock(Long Id, BigDecimal price) {
        Optional<Stock> stockOptional = stockRepository.getStocksById(Id);
        if (stockOptional.isEmpty()) {
            throw new StockNotFoundException();
        }
        Stock stock = stockOptional.get();
        stock.setCurrentPrice(price);
        stock.setLastUpdate(LocalDateTime.now());
        Stock stockUpdated = stockRepository.save(stock);
        return stockMapper.toStockDto(stockUpdated);
    }

    @Transactional
    public StockDto newStock(StockDto stockDto) {
        Stock stock = stockMapper.toStock(stockDto);
        if (stock == null) {
            throw new StockNotFoundException();
        }
        stock.setLastUpdate(LocalDateTime.now());
        Stock stockUpdated = stockRepository.save(stock);
        System.out.println(stockUpdated);
        return stockMapper.toStockDto(stockUpdated);
    }

    @Transactional
    public void deleteStock(Long id) {
        Stock stock = stockRepository.getStocksById(id)
                .orElseThrow(() -> new StockNotFoundException(id));
        stockRepository.delete(stock);
    }

    public Page<StockDto> getListStocks(PageRequestDto pageRequest) {
        Pageable pageable = this.getPePageable(pageRequest);
        Page<Stock> stockPage  = stockRepository.findAll(pageable);
        List<StockDto> stockDtoList = stockPage.getContent().stream().map(stockMapper::toStockDto).collect(Collectors.toList());
        return new PageImpl<>(stockDtoList,pageable,stockPage.getSize());
    }

    private Pageable getPePageable(PageRequestDto pageRequestDto) {
        if (pageRequestDto.getSortColumn() == null || pageRequestDto.getSortColumn().isEmpty()) {
            return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize());
        } else {
            return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize(), Sort.by(pageRequestDto.getSortColumn()));
        }
    }


}
