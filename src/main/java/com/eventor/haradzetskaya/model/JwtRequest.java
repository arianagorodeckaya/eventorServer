package com.eventor.haradzetskaya.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    //default constructor for JSON Parsing
    public JwtRequest()
    {
    }
}
