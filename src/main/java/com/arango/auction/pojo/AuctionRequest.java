package com.arango.auction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionRequest {
    private String auctionName;
    private LocalDateTime startTime;
    private Long duration;
    private Long itemId;
    private Long basePrice;
    private Long stepRate;
}
