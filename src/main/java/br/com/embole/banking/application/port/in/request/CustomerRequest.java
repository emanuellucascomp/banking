package br.com.embole.banking.application.port.in.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String accountNumber;
}
