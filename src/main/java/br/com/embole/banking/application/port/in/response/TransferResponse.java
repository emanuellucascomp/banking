package br.com.embole.banking.application.port.in.response;

import br.com.embole.banking.domain.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {

    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal transferAmount;

}
