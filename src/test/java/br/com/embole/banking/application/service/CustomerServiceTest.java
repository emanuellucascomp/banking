package br.com.embole.banking.application.service;

import br.com.embole.banking.adapter.out.persistence.CustomerRepository;
import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import br.com.embole.banking.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;


    @Test
    @DisplayName("Deve inserir cliente com sucesso")
    void shouldInsertCustomerWithSuccess(){
        CustomerRequest request = CustomerRequest.builder().accountNumber("0000001").name("Emanuel").build();
        Customer oneCustomer = new Customer(UUID.randomUUID(), "Emanuel", "0000001", BigDecimal.ZERO, null);

        when(customerRepository.save(any())).thenReturn(oneCustomer);

        CustomerResponse customerResponse = customerService.insertCustomer(request);

        assertEquals("Emanuel", customerResponse.getName());
        assertEquals("0000001", customerResponse.getAccountNumber());
        assertEquals(BigDecimal.ZERO, customerResponse.getBalance());
    }

    @Test
    @DisplayName("Deve listar clientes pelo nome com sucesso")
    void shouldListCustomersByNameWithSuccess(){
        Customer oneCustomer = new Customer(UUID.randomUUID(), "Emanuel", "0000001", BigDecimal.ZERO, null);
        Pageable pageable = PageRequest.of(0, 5);;
        List<Customer> customers = List.of(oneCustomer);
        long totalCharacters = 1;
        Page<Customer> page = new PageImpl<>(customers, pageable, totalCharacters);

        when(customerRepository.findByNameContaining(any(), any())).thenReturn(page);

        Page<CustomerResponse> customerResponse = customerService.listByCustomerName(any(), any());

        assertNotNull(customerResponse);
        assertEquals(1, customerResponse.getTotalElements());
    }

    @Test
    @DisplayName("Deve listar cliente pelo número da conta com sucesso")
    void shouldListCustomerByAccountNumberWithSuccess(){
        Customer oneCustomer = new Customer(UUID.randomUUID(), "Emanuel", "0000001", BigDecimal.ZERO, null);

        when(customerRepository.findByAccountNumber(any())).thenReturn(Optional.of(oneCustomer));

        Optional<CustomerResponse> customerResponse = customerService.getUserByAccountNumber(any());

        assertNotNull(customerResponse);
        assertEquals("0000001", customerResponse.get().getAccountNumber());
    }

    @Test
    @DisplayName("Deve retornar vazio ao não encontrar cliente pela conta")
    void shouldNotListCustomerByAccountNumberWithSuccess(){
        when(customerRepository.findByAccountNumber(any())).thenReturn(Optional.empty());

        Optional<CustomerResponse> customerResponse = customerService.getUserByAccountNumber(any());

        assertNotNull(customerResponse);
        assertTrue(customerResponse.isEmpty());
    }

}