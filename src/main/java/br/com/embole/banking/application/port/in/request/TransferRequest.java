package br.com.embole.banking.application.port.in.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    @NotBlank
    private String sourceAccount;
    @NotBlank
    private String destinationAccount;
    @NotNull @Min(0) @Max(1000)
    private BigDecimal transferAmount;
}
