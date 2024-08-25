package org.demo.cp.advice;

import org.demo.cp.exceptions.CustomerNotFoundException;
import org.demo.cp.exceptions.InsufficientBalanceException;
import org.demo.cp.exceptions.InsufficientShareException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException ex) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, ex, problem -> {
            problem.setTitle("Customer not found");
            problem.setType(URI.create("http://example.com/problems/customer-not-found"));
        });
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleException(InsufficientBalanceException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, ex, problem -> {
            problem.setTitle("Insufficient Balance");
            problem.setType(URI.create("http://example.com/problems/insufficient-balance"));
        });
    }

    @ExceptionHandler(InsufficientShareException.class)
    public ProblemDetail handleException(InsufficientShareException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, ex, problem -> {
            problem.setTitle("Insufficient Share");
            problem.setType(URI.create("http://example.com/problems/insufficient-shares"));
        });
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }
}
