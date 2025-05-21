package com.hungng3011.ecom.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private @Getter @Setter Long id;
    private @Getter @Setter String name = "";
    private @Getter @Setter String email = "";
//    private @Getter @Setter String password = "";
    private @Getter @Setter String phone = "";
    private @Getter @Setter String address = "";
    private @Getter @Setter UserRole role = UserRole.CUSTOMER;

    public User(long id,
                String name,
                String email,
//                String password,
                String phone,
                String address,
                UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public User() {

    }
}
