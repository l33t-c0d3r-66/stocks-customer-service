package org.demo.cp.dto;

import org.demo.cp.domain.Ticker;
import org.demo.cp.domain.TradeAction;

public record StockTradeRequest(Ticker ticker,
                                Integer price,
                                Integer quantity,
                                TradeAction action) {

    public Integer totalPrice() {
        return price * quantity;
    }
}
