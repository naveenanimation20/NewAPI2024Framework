package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data               // Generates getters, setters, equals, hash, and toString methods
@NoArgsConstructor  // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields as parameters
@Builder
public class User {
    private String name;
    private String email;
    private String gender;
    private String status;
}

