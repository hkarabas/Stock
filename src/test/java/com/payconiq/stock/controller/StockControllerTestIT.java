package com.payconiq.stock.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stock.model.PageRequestDto;
import com.payconiq.stock.model.StockDto;
import com.payconiq.stock.repository.StockRepository;
import com.payconiq.stock.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StockController.class)
class StockControllerTestIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @MockBean
    private StockRepository stockRepository;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = standaloneSetup(new StockController(stockService)).build();
    }

    @Test
    void getStocks_pegaAble_Test() throws Exception {
        int page = 1;
        int size = 5;
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(page)
                .size(size)
                .build();

        Page<StockDto> stockDtoPage = new PageImpl<>(Collections.EMPTY_LIST);
        when(stockService.getListStocks(pageRequestDto)).thenReturn(stockDtoPage);

        mockMvc.perform(get("/stock/stocks")
                .content(OBJECT_MAPPER.writeValueAsString(pageRequestDto))
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(stockService).getListStocks(pageRequestDto);
    }

    @Test
    void getStockTest() throws Exception {
        Long id = 1L;
        StockDto stockDto = new StockDto();

        when(stockService.getStock(id)).thenReturn(stockDto);

        mockMvc.perform(get("/stock/" + id)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(stockService).getStock(id);
    }


    @Test
    void updateStockTest() throws Exception {
        Long id = 1L;
        BigDecimal currentPrice = new BigDecimal(210);
        mockMvc.perform(patch("/stock/" + id + "/" + currentPrice.toString())
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(stockService).updatePriceStock(id, currentPrice);
    }

    @Test
    void newStockTest() throws Exception {

        BigDecimal currentPrice = new BigDecimal(250);
        String name = "Coat-xll";

        StockDto stockDto = new StockDto();
        stockDto.setName(name);
        stockDto.setCurrentPrice(currentPrice);

        StockDto stockDto1 = new StockDto();
        stockDto.setName(name);
        stockDto.setCurrentPrice(currentPrice);

        when(stockService.newStock(stockDto)).thenReturn(stockDto1);
        mockMvc.perform(post("/stock")
                .content(OBJECT_MAPPER.writeValueAsString(stockDto))
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        verify(stockService).newStock(stockDto1);
    }

    @Test
    void delete_StockTest() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/stock/" + id)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

    }
}
