package br.com.embole.banking.adapter.out.persistence;

import br.com.embole.banking.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    @Query("SELECT t FROM Transfer t INNER JOIN Customer c on t.customer.id = c.id WHERE c.accountNumber = ?1 ORDER BY t.createdDate DESC")
    List<Transfer> findTransfersByAccountNumberOrderByCreatedDateDesc(String accountNumber);
}
