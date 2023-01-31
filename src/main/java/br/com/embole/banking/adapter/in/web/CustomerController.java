package br.com.embole.banking.adapter.in.web;

import br.com.embole.banking.application.port.in.CustomerUseCase;
import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

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

    @GetMapping
    public Page<CustomerResponse> listUsers(@RequestParam(required = false) String customerName,
                                            @PageableDefault(
                                                sort = "id",
                                                direction = Sort.Direction.ASC,
                                                page = 0,
                                                size = 10) Pageable pagination){
        if(customerName != null){
            return customerUseCase.listByCustomerName(customerName, pagination);
        }
        return customerUseCase.listCustomers(pagination);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<CustomerResponse> getUserByAccountNumber(@PathVariable String accountNumber){
        Optional<CustomerResponse> userByAccountNumber = customerUseCase.getUserByAccountNumber(accountNumber);
        return userByAccountNumber.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
