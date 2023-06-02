package com.arango.auction.model;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;



@Data
@Document("Items")
public class Item {
    @ArangoId
    private String itemId;
    private String itemName;
}
