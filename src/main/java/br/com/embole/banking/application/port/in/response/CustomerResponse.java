package br.com.embole.banking.application.port.in.response;

import br.com.embole.banking.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private UUID id;
    private String name;
    private String accountNumber;
    private BigDecimal balance;

    public CustomerResponse(Customer customer){
        this.id = customer.getId();
        this.name = customer.getName();
        this.accountNumber = customer.getAccountNumber();
        this.balance = customer.getBalance();
    }

    public static Page<CustomerResponse> fromEntityToResponse(Page<Customer> customers){
        return customers.map(CustomerResponse::new);
    }
}
