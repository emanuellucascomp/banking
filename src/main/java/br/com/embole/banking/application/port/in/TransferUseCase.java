package br.com.embole.banking.application.port.in;

import br.com.embole.banking.application.port.in.request.TransferRequest;
import br.com.embole.banking.application.port.in.response.TransferDetailsResponse;
import br.com.embole.banking.application.port.in.response.TransferResponse;

import java.util.List;

public interface TransferUseCase {
    TransferResponse transfer(TransferRequest transferRequest);

    List<TransferDetailsResponse> retrieveTransfersFromAccount(String accountNumber);
}
