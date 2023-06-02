package com.arango.auction.model;

import com.arango.auction.constants.BidStatus;
import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.google.protobuf.Timestamp;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Document("Bids")
public class Bid {
    @ArangoId
    private String bidId;
    private String itemId;
    private String userId;
    private String auctionId;
    private long bidAmount;
    private LocalDateTime bidTime;
    private BidStatus bidStatus;
}