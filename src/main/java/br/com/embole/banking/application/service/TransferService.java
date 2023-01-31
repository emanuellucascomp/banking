package br.com.embole.banking.application.service;

import br.com.embole.banking.adapter.out.persistence.CustomerRepository;
import br.com.embole.banking.adapter.out.persistence.TransferRepository;
import br.com.embole.banking.application.port.in.TransferUseCase;
import br.com.embole.banking.application.port.in.request.TransferRequest;
import br.com.embole.banking.application.port.in.response.TransferDetailsResponse;
import br.com.embole.banking.application.port.in.response.TransferResponse;
import br.com.embole.banking.domain.Customer;
import br.com.embole.banking.domain.Transfer;
import br.com.embole.banking.domain.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService implements TransferUseCase {

    private final CustomerRepository customerRepository;
    private final TransferRepository transferRepository;

    @Override
    public TransferResponse transfer(TransferRequest transferRequest) {
        Optional<Customer> sourceCustomer = customerRepository.findByAccountNumber(transferRequest.getSourceAccount());
        Optional<Customer> destinationCustomer = customerRepository.findByAccountNumber(transferRequest.getDestinationAccount());

        if(sourceCustomer.isEmpty() || destinationCustomer.isEmpty()){
            throw new IllegalArgumentException("Customer not found.");
        }

        transferMoneyBetweenAccounts(sourceCustomer.get(), destinationCustomer.get(), transferRequest.getTransferAmount());

        return TransferResponse.builder()
                .transferAmount(transferRequest.getTransferAmount())
                .destinationAccount(destinationCustomer.get().getAccountNumber())
                .sourceAccount(sourceCustomer.get().getAccountNumber())
                .build();
    }

    @Override
    public List<TransferDetailsResponse> retrieveTransfersFromAccount(String accountNumber) {
        List<Transfer> trasfers = transferRepository.findTransfersByAccountNumberOrderByCreatedDateDesc(accountNumber);

        return TransferDetailsResponse.fromEntityToResponse(trasfers);
    }

    private void transferMoneyBetweenAccounts(Customer sourceCustomer, Customer destinationCustomer, BigDecimal amount){
        if(!isTransferValid(sourceCustomer, amount)) throw new IllegalArgumentException("Customer doesn't have enough balance to transfer.");

        BigDecimal updatedSourceAmount = sourceCustomer.getBalance().subtract(amount);
        sourceCustomer.setBalance(updatedSourceAmount);
        Transfer sourceTransfer = new Transfer();
        sourceTransfer.setTransferAmount(amount);
        sourceTransfer.setTransferType(Type.WITHDRAW);
        sourceTransfer.setCustomer(sourceCustomer);

        BigDecimal updatedDestinationAmount = destinationCustomer.getBalance().add(amount);
        destinationCustomer.setBalance(updatedDestinationAmount);
        Transfer destinationTransfer = new Transfer();
        destinationTransfer.setTransferAmount(amount);
        destinationTransfer.setTransferType(Type.DEPOSIT);
        destinationTransfer.setCustomer(destinationCustomer);

        customerRepository.saveAll(List.of(sourceCustomer, destinationCustomer));
        transferRepository.saveAll(List.of(sourceTransfer, destinationTransfer));
    }

    private boolean isTransferValid(Customer sourceCustomer,  BigDecimal amount){
        return sourceCustomer.getBalance().compareTo(amount) >= 0;
    }
}
