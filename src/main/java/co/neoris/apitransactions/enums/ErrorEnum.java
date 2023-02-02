package co.neoris.apitransactions.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
	UNAUTHORIZED("001","No autorizado",HttpStatus.UNAUTHORIZED),
	INTERNAL_SERVER_ERROR("002","Error inesperado",HttpStatus.INTERNAL_SERVER_ERROR),
	CLIENT_NOT_FOUND("100","Cliente no encontrado" ,HttpStatus.NOT_FOUND ),
	CLIENT_ALREADY_EXISTS("101","Cliente ya fue creada",HttpStatus.CONFLICT),
	ACCOUNT_NOT_FOUND("102", "Cuenta no encontrada",HttpStatus.NOT_FOUND ),
	ACCOUNT_ALREADY_EXISTS("103","Cuenta ya fue creada" , HttpStatus.CONFLICT),
	INSUFFICIENT_FUNDS("104","Saldo no disponible" ,HttpStatus.BAD_REQUEST );
	
	private String code;
	private String description;
	private HttpStatus httpCode;
	
	
}



