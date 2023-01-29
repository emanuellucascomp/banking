package br.com.embole.banking.application.port;

import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;

public interface CustomerUseCase {
    CustomerResponse insertCustomer(CustomerRequest request);
}
