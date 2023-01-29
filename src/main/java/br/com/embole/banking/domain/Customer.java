package br.com.embole.banking.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String accountNumber;
    @Column(name="balance", nullable = false, columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal balance = new BigDecimal("0");
    @OneToMany(mappedBy = "customer")
    private List<Transfer> transfers;
}
