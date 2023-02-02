package co.neoris.apitransactions.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "person")
public class PersonEntity {
    @Id
    @Column(name = "person_id")
    private Integer personId;
    @Column(name = "name")
    private String name;
    @Column(name = "identification")
    private String identification;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private Integer age;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
}
