package com.arango.auction.model;

import com.arango.auction.constants.BidStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    private Long bidId;
    private Long userId;
    private Long auctionId;
    private Long bidAmount;
    private LocalDateTime bidTime;
    private BidStatus bidStatus;
}