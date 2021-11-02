package com.payconiq.stock.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@ApiModel(value = "Stock Api model documentation", description = "Model")
public class StockDto {

    @ApiModelProperty(value = "Unique id field of stock object")
    private Long id;

    @ApiModelProperty(value = "Name field of stock object")
    private String name;

    @ApiModelProperty(value = "Price field of stock object")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "LastUpdate field of stock object")
    private LocalDateTime lastUpdate;
}
