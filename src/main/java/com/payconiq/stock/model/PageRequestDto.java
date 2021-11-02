package com.payconiq.stock.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "PageRequest Api model for Get Stock list documentation", description = "Model")
public class PageRequestDto implements Serializable {

    @ApiModelProperty(value = "Page Number field of stock object")
    private int page;
    @ApiModelProperty(value = "Page Size field of stock object")
    private int size;
    @ApiModelProperty(value = "Sort Column(Optional) field of stock object")
    private String sortColumn;
}
