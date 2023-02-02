package com.vivatech.dataClass;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    String name;
    String mobile;
    String email;
    String password;
}
