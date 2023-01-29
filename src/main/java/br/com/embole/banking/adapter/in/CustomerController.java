package br.com.embole.banking.adapter.in;

import br.com.embole.banking.application.port.CustomerUseCase;
import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    @PostMapping
    public ResponseEntity<CustomerResponse> insertCustomer(@RequestBody @Valid CustomerRequest request,
                                                           UriComponentsBuilder uriComponentsBuilder){
        CustomerResponse response = customerUseCase.insertCustomer(request);
        URI uri = uriComponentsBuilder.path("/customer/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
