package com.arango.auction.pojo;

import com.arango.auction.model.Auction;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private String message;
    private Object object;

    public GenericResponse(String message) {
        this.message = message;
    }

    public GenericResponse(Object object) {
        this.object = object;
    }

}
