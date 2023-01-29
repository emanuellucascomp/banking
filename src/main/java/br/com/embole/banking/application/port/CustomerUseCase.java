package br.com.embole.banking.application.port;

import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerUseCase {
    CustomerResponse insertCustomer(CustomerRequest request);

    Page<CustomerResponse> listByCustomerName(String customerName, Pageable pagination);

    Page<CustomerResponse> listCustomers(Pageable pagination);
}
