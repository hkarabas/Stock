package com.payconiq.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.payconiq.stock.enrtity.Stock;
import com.payconiq.stock.exception.StockNotFoundException;
import com.payconiq.stock.handler.Loggable;
import com.payconiq.stock.mapper.StockMapper;
import com.payconiq.stock.model.PageRequestDto;
import com.payconiq.stock.model.StockDto;
import com.payconiq.stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Stock stock = stockRepository.getStocksById(id)
                .orElseThrow(() -> new StockNotFoundException(id));
        return stockMapper.toStockDto(stock);
    }

    @SneakyThrows
    @Transactional
    public StockDto updateStock(Long Id, JsonPatch jsonPatch) {
        Optional<Stock> stockOptional = stockRepository.getStocksById(Id);
        if (stockOptional.isEmpty()) {
            throw  new StockNotFoundException();
        }
        Stock stock  = applyPatchToStockDto(jsonPatch,stockOptional.get());
        stock.setId(Id);
        Stock stockUpdated = stockRepository.save(stock);
        return stockMapper.toStockDto(stockUpdated);
    }

    @Transactional
    public StockDto newStock(StockDto stockDto) {
        Stock stock = stockMapper.toStock(stockDto);
        if (stock == null) {
            throw  new StockNotFoundException();
        }
        Stock stockUpdated = stockRepository.save(stock);
        return stockMapper.toStockDto(stockUpdated);
    }
    @Transactional
    public void deleteStock(Long id) {
        Stock stock = stockRepository.getStocksById(id)
                .orElseThrow(() -> new StockNotFoundException(id));
        stockRepository.delete(stock);
    }

    public List<StockDto> getListStocks(PageRequestDto pageRequest) {
        return stockRepository.findAll(this.getPePageable(pageRequest)).getContent().stream()
                .map(stockMapper::toStockDto).collect(Collectors.toList());
    }
    private  Pageable getPePageable(PageRequestDto pageRequestDto) {
        if (pageRequestDto.getSortColumn() == null || pageRequestDto.getSortColumn().isEmpty()) {
            return  PageRequest.of(pageRequestDto.getPage(),pageRequestDto.getSize());
        } else {
            return PageRequest.of(pageRequestDto.getPage(),pageRequestDto.getSize(),Sort.by(pageRequestDto.getSortColumn()));
        }
    }

    private Stock applyPatchToStockDto(JsonPatch patch, Stock targetStock) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetStock, JsonNode.class));
        return objectMapper.treeToValue(patched, Stock.class);
    }

}
