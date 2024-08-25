package org.demo.cp.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> customerNotFound(Integer customerId) {
        return Mono.error(new CustomerNotFoundException(customerId));
    }

    public static <T> Mono<T> insufficientBalanceException(Integer customerId) {
        return Mono.error(new InsufficientBalanceException(customerId));
    }

    public static <T> Mono<T> insufficientShareException(Integer customerId) {
        return Mono.error(new InsufficientShareException(customerId));
    }
}
