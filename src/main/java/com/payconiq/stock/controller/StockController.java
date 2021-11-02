package com.payconiq.stock.controller;


import com.payconiq.stock.model.PageRequestDto;
import com.payconiq.stock.model.StockDto;
import com.payconiq.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/stock")
@Api(value = "Stock Api documentation")
public class StockController {

    private StockService stockService;

    @PostMapping
    @ApiOperation(value = "New Stock adding method")
    public ResponseEntity<StockDto> newStock(StockDto stockDto) {
        return new ResponseEntity<>(stockService.newStock(stockDto), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}/{price}")
    @ApiOperation(value = "Update Stock  method")
    public ResponseEntity<StockDto> updatePriceStock(@PathVariable Long id, @PathVariable String price) {
        return new ResponseEntity<>(stockService.updatePriceStock(id, new BigDecimal(price)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete Stock  method")
    public ResponseEntity<Void> deleteStock(@PathVariable long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get Stock By Id  method")
    public ResponseEntity<StockDto> getStock(@PathVariable long id) {
        return new ResponseEntity<>(stockService.getStock(id), HttpStatus.OK);
    }

    @GetMapping("/stocks")
    @ApiOperation(value = "Get Stock List by page,size  method")
    public ResponseEntity<Page<StockDto>> getListStocks(@RequestBody PageRequestDto pageRequest) {
        Page<StockDto> stockDtoList = stockService.getListStocks(pageRequest);
        return new ResponseEntity<Page<StockDto>>(stockDtoList, HttpStatus.OK);
    }
}
