package com.paf.paf.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class user {

    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String imageName;
    
}
