package com.arango.auction.model;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;


@Data

@Document("Users")
public class User {
    @ArangoId
    private String userId;

    private String name;
    private String email;
}
