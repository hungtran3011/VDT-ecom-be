package com.hungng3011.ecom.auth;

import jakarta.persistence.*;
import com.hungng3011.ecom.user.User;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "authentication")
public class Auth {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;
    private String hashedPassword;
    private Date createdAt;
    private Date lastLoginAt;

}
