package org.demo.cp.controller;

import org.demo.cp.dto.CustomerInformation;
import org.demo.cp.dto.StockTradeRequest;
import org.demo.cp.dto.StockTradeResponse;
import org.demo.cp.service.CustomerService;
import org.demo.cp.service.TradeService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;
    private final TradeService tradeService;

    public CustomerController(CustomerService customerService, TradeService tradeService) {
        this.customerService = customerService;
        this.tradeService = tradeService;
    }

    @RequestMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId) {
        return this.customerService.getCustomerInformation(customerId);
    }


    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable Integer customerId, @RequestBody Mono<StockTradeRequest> mono) {
        return mono.flatMap(req -> this.tradeService.trade(customerId, req));
    }

}
