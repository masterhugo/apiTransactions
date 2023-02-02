package co.neoris.apitransactions.business;

import co.neoris.apitransactions.dto.AccountRequestDTO;
import co.neoris.apitransactions.dto.CustomerRequestDTO;
import co.neoris.apitransactions.dto.CustomerResponseDTO;
import co.neoris.apitransactions.dto.TransactionRequestDTO;
import co.neoris.apitransactions.enums.ErrorEnum;
import co.neoris.apitransactions.exceptions.ApiException;
import co.neoris.apitransactions.model.AccountEntity;
import co.neoris.apitransactions.model.CustomerEntity;
import co.neoris.apitransactions.model.PersonEntity;
import co.neoris.apitransactions.model.TransactionEntity;
import co.neoris.apitransactions.repository.AccountRepository;
import co.neoris.apitransactions.repository.CustomerRepository;
import co.neoris.apitransactions.repository.PersonRepository;
import co.neoris.apitransactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ControllerBusiness {

    // CRUD ZuulRoute
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    public CustomerResponseDTO getCustomer(String clientIdentification) throws ApiException{
        CustomerEntity customerEntity = findClient(clientIdentification);
        return CustomerResponseDTO.builder()
                .identification(customerEntity.getIdentification())
                .name(customerEntity.getName())
                .gender(customerEntity.getGender())
                .age(customerEntity.getAge())
                .phone(customerEntity.getPhone())
                .address(customerEntity.getAddress())
                .build();
    }
    public void createCustomer(CustomerRequestDTO customerRequestDTO) throws ApiException{
        try{
            CustomerEntity customerEntity = findClient(customerRequestDTO.getIdentification());
            throw new ApiException(ErrorEnum.CLIENT_ALREADY_EXISTS, "Client already exists");
        }catch (ApiException e){
            saveClient(customerRequestDTO);
        }
    }

    public void updateCustomer(CustomerRequestDTO customerRequestDTO) throws ApiException{
        try{
            CustomerEntity customerEntity = findClient(customerRequestDTO.getIdentification());
            saveClient(customerRequestDTO);
        }catch (ApiException e){
            throw new ApiException(ErrorEnum.CLIENT_NOT_FOUND, "Client not found");
        }
    }

    public void deleteCustomer(CustomerRequestDTO customerRequestDTO) throws ApiException{
        try{
            CustomerEntity customerEntity = findClient(customerRequestDTO.getIdentification());
            customerRepository.delete(customerEntity);
            personRepository.deleteById(customerEntity.getCustomerId());
        }catch (ApiException e){
            throw new ApiException(ErrorEnum.CLIENT_NOT_FOUND, "Client not found");
        }
    }

    public AccountRequestDTO getAccount(String accountNumber, String accountType, String clientIdentification) throws ApiException{
        PersonEntity personEntity = findPersonByIdentification(clientIdentification);
        AccountEntity accountEntity = accountRepository.findAccountByAccountNumberAAndAccountTypeAAndAccountOwner(accountNumber,accountType,personEntity.getPersonId()).orElseThrow(() -> new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found"));
        return AccountRequestDTO.builder()
                .accountNumber(accountEntity.getAccountNumber())
                .accountType(accountEntity.getAccountType())
                .accountBalance(accountEntity.getAccountBalance())
                .accountInitialBalance(accountEntity.getAccountInitialBalance())
                .accountOwnerName(personEntity.getName())
                .accountOwnerIdentification(personEntity.getIdentification())
                .build();
    }

    public void createAccount(AccountRequestDTO accountRequestDTO) throws ApiException{
        PersonEntity personEntity = findPersonByIdentification(accountRequestDTO.getAccountOwnerIdentification());
        try{
            accountRepository.findAccountByAccountNumberAAndAccountTypeAAndAccountOwner(accountRequestDTO.getAccountNumber(),accountRequestDTO.getAccountType(),personEntity.getPersonId()).orElseThrow(() -> new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found"));
            throw new ApiException(ErrorEnum.ACCOUNT_ALREADY_EXISTS, "Account already exists");
        }catch (ApiException e){

            accountRepository.save(AccountEntity.builder()
                    .accountNumber(accountRequestDTO.getAccountNumber())
                    .accountType(accountRequestDTO.getAccountType())
                    .accountBalance(accountRequestDTO.getAccountBalance())
                    .accountInitialBalance(accountRequestDTO.getAccountBalance())
                    .accountOwner(personEntity.getPersonId())
                    .build());
        }
    }

    public void updateAccount(String accountNumber,String accountType,String accountOwnerIdentification, AccountRequestDTO accountRequestDTO) throws ApiException{
        PersonEntity personEntity = findPersonByIdentification(accountOwnerIdentification);
        try{
            AccountEntity accountEntity = accountRepository.findAccountByAccountNumberAAndAccountTypeAAndAccountOwner(accountNumber,accountType,personEntity.getPersonId()).orElseThrow(() -> new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found"));
            accountRepository.save(AccountEntity.builder()
                    .accountNumber(accountRequestDTO.getAccountNumber())
                    .accountType(accountRequestDTO.getAccountType())
                    .accountBalance(accountRequestDTO.getAccountBalance())
                    .accountInitialBalance(accountRequestDTO.getAccountBalance())
                    .accountOwner(personEntity.getPersonId())
                    .build());
        }catch (ApiException e){
            throw new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found");
        }
    }

    public void deleteAccount(String accountNumber,String accountType, String accountOwnerIdentification) throws ApiException{
        PersonEntity personEntity = findPersonByIdentification(accountOwnerIdentification);
        try{
            AccountEntity accountEntity = accountRepository.findAccountByAccountNumberAAndAccountTypeAAndAccountOwner(accountNumber,accountType,personEntity.getPersonId()).orElseThrow(() -> new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found"));
            accountRepository.delete(accountEntity);
        }catch (ApiException e){
            throw new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found");
        }
    }

    public List getTransactions(String accountNumber, String accountType, String clientIdentification) throws ApiException{
        PersonEntity personEntity = findPersonByIdentification(clientIdentification);
        AccountEntity accountEntity = accountRepository.findAccountByAccountNumberAAndAccountTypeAAndAccountOwner(accountNumber,accountType,personEntity.getPersonId()).orElseThrow(() -> new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found"));
        List<TransactionEntity> transactionEntityList = transactionRepository.findTransactionsByAccount(accountEntity.getAccountId());
        return transactionEntityList;
    }

    public void createTransaction(TransactionRequestDTO transactionRequestDTO) throws ApiException{
        PersonEntity personEntity = findPersonByIdentification(transactionRequestDTO.getTransactionClientIdentification());
        AccountEntity accountEntity = accountRepository.findAccountByAccountNumberAAndAccountTypeAAndAccountOwner(transactionRequestDTO.getTransactionAccountNumber(),transactionRequestDTO.getTransactionType(),personEntity.getPersonId()).orElseThrow(() -> new ApiException(ErrorEnum.ACCOUNT_NOT_FOUND, "Account not found"));
        if(transactionRequestDTO.getTransactionType().equals("Debit")) {
            if (accountEntity.getAccountBalance() < transactionRequestDTO.getTransactionAmount()) {
                throw new ApiException(ErrorEnum.INSUFFICIENT_FUNDS, "Insufficient funds");
            }
            accountEntity.setAccountBalance(accountEntity.getAccountBalance() - transactionRequestDTO.getTransactionAmount());
            accountRepository.save(accountEntity);
        }else if(transactionRequestDTO.getTransactionType().equals("Credit")){
            accountEntity.setAccountBalance(accountEntity.getAccountBalance() + transactionRequestDTO.getTransactionAmount());
            accountRepository.save(accountEntity);
        }
        transactionRepository.save(TransactionEntity.builder()
                .transactionAmount(transactionRequestDTO.getTransactionAmount())
                .transactionDate(Date.from(Instant.now()))
                .transactionType(transactionRequestDTO.getTransactionType())
                .transactionAccount(accountEntity.getAccountId())
                .build());
    }



    private void saveClient(CustomerRequestDTO customerRequestDTO) {
        PersonEntity personEntity = personRepository.save(PersonEntity.builder()
                .identification(customerRequestDTO.getIdentification())
                .address(customerRequestDTO.getAddress())
                .name(customerRequestDTO.getName())
                .age(customerRequestDTO.getAge())
                .gender(customerRequestDTO.getGender())
                .phone(customerRequestDTO.getPhone())
                .build());
        customerRepository.save(CustomerEntity.builder()
                .customerId(personEntity.getPersonId())
                .customerStatus(customerRequestDTO.getStatus())
                .customerSecret(customerRequestDTO.getClientSecret())
                .build());
    }

    private PersonEntity findPersonByIdentification(String clientIdentification){
        return personRepository.findByIdentification(clientIdentification).orElseThrow(() -> new ApiException(ErrorEnum.CLIENT_NOT_FOUND, "Client not found"));
    }

    private CustomerEntity findClient(String clientIdentification){
        PersonEntity person = findPersonByIdentification(clientIdentification);
        return customerRepository.findById(person.getPersonId()).orElseThrow(() -> new ApiException(ErrorEnum.CLIENT_NOT_FOUND, "Client not found"));
    }

}
