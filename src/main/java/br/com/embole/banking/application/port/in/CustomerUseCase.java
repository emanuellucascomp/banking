package br.com.embole.banking.application.port.in;

import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerUseCase {
    CustomerResponse insertCustomer(CustomerRequest request);

    Page<CustomerResponse> listByCustomerName(String customerName, Pageable pagination);

    Page<CustomerResponse> listCustomers(Pageable pagination);
    Optional<CustomerResponse> getUserByAccountNumber(String accountNumber);
}
