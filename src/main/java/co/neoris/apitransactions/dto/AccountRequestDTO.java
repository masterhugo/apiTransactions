package co.neoris.apitransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequestDTO {
    private String accountNumber;
    private String accountType;
    private Long accountBalance;
    private Long accountInitialBalance;
    private String accountOwnerName;
    private String accountOwnerIdentification;
}
