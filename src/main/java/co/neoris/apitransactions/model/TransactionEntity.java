package co.neoris.apitransactions.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @Column(name = "transaction_id")
    private Integer transactionId;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "transaction_amount")
    private Long transactionAmount;
    @Column(name = "transaction_account")
    private Integer transactionAccount;
    @Column(name = "transaction_date")
    private Date transactionDate;
}
