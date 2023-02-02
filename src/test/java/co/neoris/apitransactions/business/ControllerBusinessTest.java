package co.neoris.apitransactions.business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import co.neoris.apitransactions.model.PersonEntity;
import co.neoris.apitransactions.repository.AccountRepository;
import co.neoris.apitransactions.repository.CustomerRepository;
import co.neoris.apitransactions.repository.PersonRepository;
import co.neoris.apitransactions.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ControllerBusinessTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ControllerBusiness controllerBusiness;

    @BeforeEach
    void init() {
        when(personRepository.findById(any())).thenReturn(Optional.of(PersonEntity.builder()
                .personId(125456456)
                .name("/gxb/los")
                .build()));

    }
    @Test
    public void should_find_tutorial_by_id() throws Exception {


    }

    @AfterEach
    void afterMethod(){
        System.out.println("After each method");
    }

}
