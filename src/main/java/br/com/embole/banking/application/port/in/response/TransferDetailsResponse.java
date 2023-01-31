package br.com.embole.banking.application.port.in.response;

import br.com.embole.banking.domain.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetailsResponse {

    private BigDecimal transferAmount;
    private LocalDateTime transferDate;
    private String type;

    public TransferDetailsResponse(Transfer transfer){
        this.transferAmount = transfer.getTransferAmount();
        this.transferDate = transfer.getCreatedDate();
        this.type = transfer.getTransferType().toString();
    }

    public static List<TransferDetailsResponse> fromEntityToResponse(List<Transfer> transfers){
        return transfers.stream().map(TransferDetailsResponse::new).toList();
    }
}
