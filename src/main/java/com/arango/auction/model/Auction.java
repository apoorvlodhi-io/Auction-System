package com.arango.auction.model;

import com.arango.auction.constants.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

    private String auctionName;
    private AuctionStatus auctionStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private Long itemId;
    private Long basePrice;
    private Long stepRate;
    private Long highestBid;
}
