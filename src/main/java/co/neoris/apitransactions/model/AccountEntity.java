package co.neoris.apitransactions.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "account_id")
    private Integer accountId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "account_status")
    private Boolean accountStatus;
    @Column(name = "account_balance")
    private Long accountBalance;
    @Column(name = "account_balance")
    private Long accountInitialBalance;
    @Column(name = "account_owner")
    private Integer accountOwner;
}
