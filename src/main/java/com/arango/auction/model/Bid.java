package com.arango.auction.model;

import com.arango.auction.constants.BidStatus;
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
@Table(name = "Bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;
    private Long itemId;
    private Long userId;
    private Long auctionId;
    private Long bidAmount;
    private LocalDateTime bidTime;
    private BidStatus bidStatus;
}