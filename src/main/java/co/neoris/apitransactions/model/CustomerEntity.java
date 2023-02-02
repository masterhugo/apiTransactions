package co.neoris.apitransactions.model;


import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "customer")
public class CustomerEntity extends PersonEntity{
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_secret")
    private String customerSecret;
    @Column(name = "customer_status")
    private Boolean customerStatus;

}
