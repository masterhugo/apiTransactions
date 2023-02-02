package co.neoris.apitransactions.api;

import java.util.List;

import co.neoris.apitransactions.business.ControllerBusiness;
import co.neoris.apitransactions.dto.AccountRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

	@Autowired
    ControllerBusiness controllerBusiness;
	
	@Value("${spring.application.version}")
	private String version;

	@GetMapping("version")
    public ResponseEntity<String> version() {
        return new ResponseEntity(version, HttpStatus.OK);
    }
	
	@GetMapping("/cuentas/{accountNumber}/{accountType}/{accountOwnerIdentification}")
    public ResponseEntity<?> getAccounts(String accountNumber, String accountType, String accountOwnerIdentification) {
		return new ResponseEntity(controllerBusiness.getAccount(accountNumber,accountType,accountOwnerIdentification), HttpStatus.OK);
	}
	@PostMapping("/cuentas")
	public ResponseEntity<?> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
		controllerBusiness.createAccount(accountRequestDTO);
		return new ResponseEntity(HttpStatus.OK);
	}
	@PutMapping("/cuentas/{accountNumber}/{accountType}/{accountOwnerIdentification}")
	public ResponseEntity<?> updateAccount(@PathVariable("accountNumber") String accountNumber,
										   @PathVariable("accountType") String accountType,
										   @PathVariable("accountOwnerIdentification") String accountOwnerIdentification,
										   @RequestBody AccountRequestDTO accountRequestDTO) {
		controllerBusiness.updateAccount(accountNumber,accountType,accountOwnerIdentification,accountRequestDTO);
		return new ResponseEntity(HttpStatus.OK);
	}
	@DeleteMapping("/cuentas/{accountNumber}/{accountType}/{accountOwnerIdentification}")
	public ResponseEntity<?> deleteAccount(@PathVariable("accountNumber") String accountNumber,
										   @PathVariable("accountType") String accountType,
										   @PathVariable("accountOwnerIdentification") String accountOwnerIdentification) {
		controllerBusiness.deleteAccount(accountNumber,accountType,accountOwnerIdentification);
		return new ResponseEntity(HttpStatus.OK);
	}
	@GetMapping("/clientes/{customerIdentification}")
	public ResponseEntity<?> getCustomers(@PathVariable("customerIdentification") String customerIdentification) {
		return new ResponseEntity(controllerBusiness.getCustomer(customerIdentification), HttpStatus.OK);
	}


}
