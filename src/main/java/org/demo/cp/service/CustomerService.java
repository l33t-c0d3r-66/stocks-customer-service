package org.demo.cp.service;

import org.demo.cp.dto.CustomerInformation;
import org.demo.cp.entity.Customer;
import org.demo.cp.exceptions.ApplicationExceptions;
import org.demo.cp.mapper.EntityDtoMapper;
import org.demo.cp.repository.CustomerRepository;
import org.demo.cp.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public CustomerService(CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return this.customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
                .flatMap(this::buildCustomerInformation);
    }

    private Mono<CustomerInformation> buildCustomerInformation(Customer customer) {
       return this.portfolioItemRepository.findAllByCustomerId(customer.getId())
               .collectList()
               .map(list -> EntityDtoMapper.toCustomerInformation(customer, list));
    }
}
