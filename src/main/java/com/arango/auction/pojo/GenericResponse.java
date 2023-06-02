package com.arango.auction.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private String message;
    private Object object;

    public GenericResponse(String message) {
        this.message = message;
    }
}
