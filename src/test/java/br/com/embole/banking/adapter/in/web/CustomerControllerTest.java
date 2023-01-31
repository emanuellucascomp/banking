package br.com.embole.banking.adapter.in.web;

import br.com.embole.banking.application.port.in.CustomerUseCase;
import br.com.embole.banking.application.port.in.request.CustomerRequest;
import br.com.embole.banking.application.port.in.request.TransferRequest;
import br.com.embole.banking.application.port.in.response.CustomerResponse;
import br.com.embole.banking.application.port.in.response.TransferDetailsResponse;
import br.com.embole.banking.application.port.in.response.TransferResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CustomerController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CustomerUseCase customerUseCase;

    @Test
    void shouldInsertCustomer() throws Exception{
        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID())
                .name("Emanuel")
                .accountNumber("0000001")
                .balance(BigDecimal.ZERO)
                .build();
        CustomerRequest request = CustomerRequest.builder()
                .name("Emanuel")
                .accountNumber("0000001")
                .build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(request);

        given(customerUseCase.insertCustomer(Mockito.any())).willReturn(response);

        mvc.perform(post("/api/v1/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        verify(customerUseCase, VerificationModeFactory.times(1)).insertCustomer(Mockito.any());
    }

    @Test
    void shouldRetrieveCustomerFromAccount() throws Exception {
        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID())
                .name("Emanuel")
                .accountNumber("0000001")
                .balance(BigDecimal.ZERO)
                .build();

        given(customerUseCase.getUserByAccountNumber(Mockito.any())).willReturn(Optional.of(response));

        mvc.perform(get("/api/v1/customer/0000001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name",  is("Emanuel")));
        verify(customerUseCase, VerificationModeFactory.times(1)).getUserByAccountNumber(Mockito.any());
    }
}