package br.com.embole.banking.application.service;

import br.com.embole.banking.adapter.out.persistence.CustomerRepository;
import br.com.embole.banking.adapter.out.persistence.TransferRepository;
import br.com.embole.banking.application.port.in.request.TransferRequest;
import br.com.embole.banking.application.port.in.response.TransferDetailsResponse;
import br.com.embole.banking.application.port.in.response.TransferResponse;
import br.com.embole.banking.domain.Customer;
import br.com.embole.banking.domain.Transfer;
import br.com.embole.banking.domain.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;


    @Test
    @DisplayName("Deve transferir dinheiro entre contas com sucesso")
    void shouldTransferWithSuccess(){
        TransferRequest request = TransferRequest.builder()
                .transferAmount(BigDecimal.valueOf(1000))
                .sourceAccount("0000001")
                .destinationAccount("0000002")
                .build();
        Customer oneCustomer = new Customer(UUID.randomUUID(), "Emanuel", "0000001", BigDecimal.valueOf(1000), null);
        Customer secondCustomer = new Customer(UUID.randomUUID(), "Lucas", "0000002", BigDecimal.valueOf(1000), null);

        when(customerRepository.findByAccountNumber("0000001")).thenReturn(Optional.of(oneCustomer));
        when(customerRepository.findByAccountNumber("0000002")).thenReturn(Optional.of(secondCustomer));

        TransferResponse response = transferService.transfer(request);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(1000), response.getTransferAmount());
    }

    @Test
    @DisplayName("Deve lançar excessão ao não achar conta durante transferência")
    void shouldTransferWithError(){
        TransferRequest request = TransferRequest.builder()
                .transferAmount(BigDecimal.valueOf(1000))
                .sourceAccount("0000001")
                .destinationAccount("0000002")
                .build();
        Customer oneCustomer = new Customer(UUID.randomUUID(), "Emanuel", "0000001", BigDecimal.valueOf(1000), null);

        when(customerRepository.findByAccountNumber("0000001")).thenReturn(Optional.of(oneCustomer));
        when(customerRepository.findByAccountNumber("0000002")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transferService.transfer(request));
    }

    @Test
    @DisplayName("Deve recuperar transferências com sucesso")
    void shouldRetrieveTransfersWithSuccess(){
        Customer oneCustomer = new Customer(UUID.randomUUID(), "Emanuel", "0000001", BigDecimal.valueOf(1000), null);
        Transfer transfer = Transfer.builder()
                .transferAmount(BigDecimal.valueOf(1000))
                .transferType(Type.DEPOSIT)
                .createdDate(LocalDateTime.now())
                .customer(oneCustomer)
                .build();

        when(transferRepository.findTransfersByAccountNumberOrderByCreatedDateDesc("0000001")).thenReturn(List.of(transfer));

        List<TransferDetailsResponse> response = transferService.retrieveTransfersFromAccount("0000001");

        assertNotNull(response);
        assertEquals(1, response.size());
    }
}