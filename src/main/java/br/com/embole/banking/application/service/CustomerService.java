package br.com.embole.banking.application.service;

import br.com.embole.banking.adapter.out.persistence.CustomerRepository;
import br.com.embole.banking.application.port.in.CustomerUseCase;
import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import br.com.embole.banking.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                .balance(savedCustomer.getBalance())
                .build();
    }

    @Override
    public Page<CustomerResponse> listByCustomerName(String customerName, Pageable pagination) {
        Page<Customer> customersByName = customerRepository.findByNameContaining(customerName, pagination);
        return CustomerResponse.fromEntityToResponse(customersByName);
    }

    @Override
    public Page<CustomerResponse> listCustomers(Pageable pagination) {
        Page<Customer> customersByPage = customerRepository.findAll(pagination);
        return CustomerResponse.fromEntityToResponse(customersByPage);
    }

    @Override
    public Optional<CustomerResponse> getUserByAccountNumber(String accountNumber) {
        Optional<Customer> savedCustomer = customerRepository.findByAccountNumber(accountNumber);
        if(savedCustomer.isEmpty()){
            return Optional.empty();
        }

        CustomerResponse customer = CustomerResponse.builder()
                .id(savedCustomer.get().getId())
                .accountNumber(savedCustomer.get().getAccountNumber())
                .name(savedCustomer.get().getName())
                .balance(savedCustomer.get().getBalance())
                .build();

        return Optional.of(customer);
    }
}
