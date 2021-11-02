package com.payconiq.stock.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class PageRequestDto implements Serializable {

    private int page;
    private int size;
    private String sortColumn;
}
