package br.com.embole.banking.adapter.in.web;

import br.com.embole.banking.application.port.in.TransferUseCase;
import br.com.embole.banking.application.port.in.request.TransferRequest;
import br.com.embole.banking.application.port.in.response.TransferDetailsResponse;
import br.com.embole.banking.application.port.in.response.TransferResponse;
import static org.hamcrest.CoreMatchers.is;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = TransferController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class TransferControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransferUseCase transferUseCase;

    @Test
    void shouldTransferMoney() throws Exception {
        TransferResponse response = TransferResponse.builder()
                .transferAmount(BigDecimal.valueOf(1000))
                .sourceAccount("0000001")
                .destinationAccount("0000002")
                .build();
        TransferRequest request = TransferRequest.builder()
                .transferAmount(BigDecimal.valueOf(1000))
                .sourceAccount("0000001")
                .destinationAccount("0000002")
                .build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(request);

        given(transferUseCase.transfer(Mockito.any())).willReturn(response);

        mvc.perform(post("/api/v1/transfer").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(transferUseCase, VerificationModeFactory.times(1)).transfer(Mockito.any());
    }

    @Test
    void shouldRetrieveTransfersFromAccount() throws Exception {
        TransferDetailsResponse response = TransferDetailsResponse.builder()
                .transferAmount(BigDecimal.valueOf(1000))
                .transferDate(LocalDateTime.now())
                .type("DEPOSIT")
                .build();

        given(transferUseCase.retrieveTransfersFromAccount(Mockito.any())).willReturn(List.of(response));

        mvc.perform(get("/api/v1/transfer/accounts/0000001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type",  is("DEPOSIT")));
        verify(transferUseCase, VerificationModeFactory.times(1)).retrieveTransfersFromAccount(Mockito.any());
    }
}