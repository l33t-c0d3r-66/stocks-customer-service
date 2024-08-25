package org.demo.cp;

import org.demo.cp.domain.Ticker;
import org.demo.cp.domain.TradeAction;
import org.demo.cp.dto.StockTradeRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static org.demo.cp.domain.Ticker.GOOGLE;

@SpringBootTest
@AutoConfigureWebTestClient
class CustomerServiceApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceApplicationTests.class);

    @Autowired
    private WebTestClient client;

    @Test
    void contextLoads() {
    }


    @Test
    public void customerInformation() {
        getCustomer(1, HttpStatus.OK)
                .jsonPath("$.name").isEqualTo("Sam")
                .jsonPath("$.balance").isEqualTo("10000")
                .jsonPath("$.holdings").isEmpty();
    }

    @Test
    public void buyAndSell() {
        // buy
        var buyRequest = new StockTradeRequest(GOOGLE, 100, 5, TradeAction.BUY);
        trade(2, buyRequest, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(9500)
                .jsonPath("$.totalPrice").isEqualTo(500);


        var buyRequest2 = new StockTradeRequest(GOOGLE, 100, 10, TradeAction.BUY);
        trade(2, buyRequest2, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(8500)
                .jsonPath("$.totalPrice").isEqualTo(1000);

        // check holdings
        getCustomer(2, HttpStatus.OK)
                .jsonPath("$.holdings").isNotEmpty()
                .jsonPath("$.holdings.length()").isEqualTo(1)
                .jsonPath("$.holdings[0].ticker").isEqualTo("GOOGLE")
                .jsonPath("$.holdings[0].quantity").isEqualTo(15);

        // sell
        var sellRequest = new StockTradeRequest(GOOGLE, 110, 5, TradeAction.SELL);
        trade(2, sellRequest, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(9050)
                .jsonPath("$.totalPrice").isEqualTo(550);


        var sellRequest2 = new StockTradeRequest(GOOGLE, 110, 10, TradeAction.SELL);
        trade(2, sellRequest2, HttpStatus.OK)
                .jsonPath("$.balance").isEqualTo(10150)
                .jsonPath("$.totalPrice").isEqualTo(1100);

        getCustomer(2, HttpStatus.OK)
                .jsonPath("$.holdings").isNotEmpty()
                .jsonPath("$.holdings.length()").isEqualTo(1)
                .jsonPath("$.holdings[0].ticker").isEqualTo("GOOGLE")
                .jsonPath("$.holdings[0].quantity").isEqualTo(0);


    }

    @Test
    public void customerNotFound() {
        getCustomer(10, HttpStatus.NOT_FOUND)
                .jsonPath("$.detail").isEqualTo("Customer [id=10] is not found");


        // sell
        var sellRequest = new StockTradeRequest(GOOGLE, 110, 5, TradeAction.SELL);
        trade(10, sellRequest, HttpStatus.NOT_FOUND)
                .jsonPath("$.detail").isEqualTo("Customer [id=10] is not found");

    }

    @Test
    public void insufficientBalance() {
        var sellRequest = new StockTradeRequest(GOOGLE, 100, 1, TradeAction.SELL);

        trade(3, sellRequest, HttpStatus.BAD_REQUEST)
                .jsonPath("$.detail").isEqualTo("Customer [id=3] doesn't have enough shares to complete the transaction");
    }

    @Test
    public void insufficientShares() {
        var buyRequest1 = new StockTradeRequest(GOOGLE, 100, 101, TradeAction.BUY);
        trade(3, buyRequest1, HttpStatus.BAD_REQUEST)
                .jsonPath("$.detail").isEqualTo("Customer [id=3] doesn't have enough funds to complete the transaction");
    }


    private WebTestClient.BodyContentSpec getCustomer(Integer customerId, HttpStatus status) {
        return this.client.get()
                .uri("/customers/{customerId}", customerId)
                .exchange()
                .expectStatus().isEqualTo(status)
                .expectBody()
                .consumeWith(e -> log.info("{}", new String(Objects.requireNonNull(e.getResponseBody()))));
    }

    private WebTestClient.BodyContentSpec trade(Integer customerId, StockTradeRequest request, HttpStatus status) {
        return this.client.post()
                .uri("/customers/{customerId}/trade", customerId)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(status)
                .expectBody()
                .consumeWith(e -> log.info("{}", new String(Objects.requireNonNull(e.getResponseBody()))));
    }
}
