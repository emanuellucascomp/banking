package br.com.embole.banking.adapter.in.web;

import br.com.embole.banking.application.port.in.TransferUseCase;
import br.com.embole.banking.application.port.in.request.TransferRequest;
import br.com.embole.banking.application.port.in.response.TransferDetailsResponse;
import br.com.embole.banking.application.port.in.response.TransferResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferUseCase transferUseCase;

    @PostMapping
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody @Valid TransferRequest transferRequest){
        TransferResponse transferResponse = transferUseCase.transfer(transferRequest);
        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<List<TransferDetailsResponse>> retrieveTransfersFromAccount(@PathVariable String accountNumber){
        List<TransferDetailsResponse> transferResponse = transferUseCase.retrieveTransfersFromAccount(accountNumber);
        return ResponseEntity.ok(transferResponse);
    }
}
