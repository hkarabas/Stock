package com.payconiq.stock.controller;


import com.github.fge.jsonpatch.JsonPatch;
import com.payconiq.stock.model.PageRequestDto;
import com.payconiq.stock.model.StockDto;
import com.payconiq.stock.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/stock")
public class StockController {

    private StockService stockService;

    @PostMapping
    public ResponseEntity<StockDto> newStock(StockDto stockDto) {
       return new ResponseEntity<>(stockService.newStock(stockDto),HttpStatus.CREATED);
    }
    @PatchMapping(path="/{id}")
    public ResponseEntity<StockDto> updateStock(@PathVariable long id,@RequestBody JsonPatch patch ) {
        return new ResponseEntity<>(stockService.updateStock(id,patch),HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<StockDto>  getStock(@PathVariable long id){
        return new ResponseEntity<>(stockService.getStock(id),HttpStatus.FOUND);
    }
    @GetMapping("/stocks")
    public ResponseEntity<List<StockDto>> getListStocks(@RequestBody PageRequestDto pageRequest) {
        List<StockDto> stockDtoList = stockService.getListStocks(pageRequest);
        return new  ResponseEntity<List<StockDto>>(stockDtoList,HttpStatus.FOUND);
    }
}
