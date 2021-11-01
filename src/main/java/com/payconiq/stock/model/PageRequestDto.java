package com.payconiq.stock.model;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PageRequestDto {
    private  int page;
    private  int size;
    private  String sortColumn;
}
