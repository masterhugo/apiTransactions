package co.neoris.apitransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {
	   private String name;
	   private String identification;
	   private String gender;
	   private Integer age;
	   private String phone;
	   private String address;
	   private String clientSecret;

	   private Boolean status;
}