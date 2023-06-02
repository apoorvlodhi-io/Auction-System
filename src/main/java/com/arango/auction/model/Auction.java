package com.arango.auction.model;

import com.arango.auction.constants.AuctionStatus;
import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Document("Auction")
@Data
public class Auction {
    @ArangoId
    private String auctionId;

    private String auctionName;
    private AuctionStatus auctionStatus;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long duration;
    private String itemId;
    private long basePrice;
    private long stepRate;
    private long highestBid;
}
