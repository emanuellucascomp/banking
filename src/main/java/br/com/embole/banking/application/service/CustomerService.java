package br.com.embole.banking.application.service;

import br.com.embole.banking.adapter.out.CustomerRepository;
import br.com.embole.banking.application.port.CustomerUseCase;
import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import br.com.embole.banking.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse insertCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setAccountNumber(request.getAccountNumber());

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerResponse.builder()
                .id(savedCustomer.getId())
                .accountNumber(savedCustomer.getAccountNumber())
                .name(savedCustomer.getName())
                .build();
    }
}
