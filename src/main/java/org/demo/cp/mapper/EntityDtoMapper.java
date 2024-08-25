package org.demo.cp.mapper;

import org.demo.cp.domain.Ticker;
import org.demo.cp.dto.CustomerInformation;
import org.demo.cp.dto.Holding;
import org.demo.cp.dto.StockTradeRequest;
import org.demo.cp.dto.StockTradeResponse;
import org.demo.cp.entity.Customer;
import org.demo.cp.entity.PortfolioItem;

import java.util.List;

public class EntityDtoMapper {
    public static CustomerInformation toCustomerInformation(Customer customer, List<PortfolioItem> items) {
        var holdings = items.stream()
                .map(i -> new Holding(i.getTicker(), i.getQuantity()))
                .toList();

        return new CustomerInformation(
                customer.getId(),
                customer.getName(),
                customer.getBalance(),
                holdings
        );
    }

    public static PortfolioItem toPortfolioItem(Integer customerId, Ticker ticker){
        var portfolioItem = new PortfolioItem();
        portfolioItem.setCustomerId(customerId);
        portfolioItem.setTicker(ticker);
        portfolioItem.setQuantity(0);
        return portfolioItem;
    }

    public static StockTradeResponse toStockTradeResponse(StockTradeRequest request, Integer customerId, Integer balance) {
        return new StockTradeResponse(customerId,
                request.ticker(),
                request.price(),
                request.quantity(),
                request.action(),
                request.totalPrice(),
                balance);
    }
}
