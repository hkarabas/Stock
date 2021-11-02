package com.payconiq.stock.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private LocalDateTime lastUpdate;
}
