package co.neoris.apitransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {
	   private String name;
	   private String identification;
	   private String gender;
	   private Integer age;
	   private String phone;
	   private String address;
}