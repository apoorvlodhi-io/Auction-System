package com.arango.auction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidRequest {
    private Long userId;
    private Long auctionId;
    private Long bidAmount;
}
