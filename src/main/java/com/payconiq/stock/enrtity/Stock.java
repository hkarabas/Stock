package com.payconiq.stock.enrtity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "currentPrice")
    private BigDecimal currentPrice;

    @Column(name="lastupdate")
    private LocalDateTime lastUpdate;


}
