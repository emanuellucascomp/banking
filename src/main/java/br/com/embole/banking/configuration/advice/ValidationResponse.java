package br.com.embole.banking.configuration.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationResponse {

    private String field;
    private String erro;
}
