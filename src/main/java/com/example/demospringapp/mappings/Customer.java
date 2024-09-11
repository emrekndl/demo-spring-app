package com.example.demospringapp.mappings;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

// import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="first_name")
    private String firtsName;
    
    @Column(name="last_name")
    private String lastName;
    
    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "address_id")
    // private Address address;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "customer_id")
    
    @ManyToAny
    @JoinTable(
        name = "customer_address",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses;

}
