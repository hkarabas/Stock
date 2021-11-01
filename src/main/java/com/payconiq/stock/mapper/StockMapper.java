package com.payconiq.stock.mapper;


import com.payconiq.stock.enrtity.Stock;
import com.payconiq.stock.model.StockDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {
    private final static ModelMapper modelmapper  = new ModelMapper();
    public Stock toStock(StockDto stockDto) {
        if (stockDto ==  null) {
            return null;
        }
        return modelmapper.map(stockDto,Stock.class);
  }
    public StockDto toStockDto(Stock stock) {
        if (stock == null) {
            return null;
        }
        return modelmapper.map(stock, StockDto.class);

    }
}
