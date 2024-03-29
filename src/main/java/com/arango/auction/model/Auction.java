package com.arango.auction.model;

import com.arango.auction.constants.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    private Long auctionId;
    private String auctionName;
    private AuctionStatus auctionStatus;
    private LocalDateTime startTime;
    private Long duration;
    private Long itemId;
    private Long basePrice;
    private Long stepRate;
    private Long highestBid;
    private Long revisionVersion;
}
