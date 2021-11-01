package com.payconiq.stock.repository;

import com.payconiq.stock.enrtity.Stock;
import com.payconiq.stock.handler.Loggable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Loggable
public interface StockRepository extends JpaRepository<Stock,Long> {
    Optional<Stock> getStocksById(Long id);
}
