package com.vivatech.jwtModel;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtRequest {
    String email;
    String password;
}
