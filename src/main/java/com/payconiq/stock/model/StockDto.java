package com.payconiq.stock.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class StockDto {
    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private LocalDateTime lastUpdate;
}
