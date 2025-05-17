package com.hungng3011.ecom.user;

import lombok.*;

@AllArgsConstructor
public class User {
    private @Getter @Setter Long id = 0L;
    private @Getter @Setter String name = "";
    private @Getter @Setter String email = "";
    private @Getter @Setter String password = "";
    private @Getter @Setter String phone = "";
    private @Getter @Setter String address = "";
    private @Getter @Setter String role = "customer";
}
