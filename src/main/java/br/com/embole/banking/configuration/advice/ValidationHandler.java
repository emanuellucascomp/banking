package br.com.embole.banking.configuration.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationHandler {

    private final MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationResponse> handle(MethodArgumentNotValidException exception){
        List<ValidationResponse> responses = new ArrayList<>();
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        errors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ValidationResponse response = ValidationResponse.builder()
                    .field(e.getField())
                    .erro(message)
                    .build();
            responses.add(response);
        });

        return responses;
    }
}
