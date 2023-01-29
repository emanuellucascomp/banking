package br.com.embole.banking.adapter.out;

import br.com.embole.banking.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
