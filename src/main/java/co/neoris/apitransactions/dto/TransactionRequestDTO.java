package co.neoris.apitransactions.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    private String transactionType;
    private Long transactionAmount;
    private String transactionClientIdentification;
    private String transactionAccountNumber;
    private String transactionAccountType;
}
