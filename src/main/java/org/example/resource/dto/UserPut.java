package org.example.resource.dto;

import lombok.Data;

@Data
public class UserPut {
    public String username;

    public String email;

    public String password;

    public String image;
}
