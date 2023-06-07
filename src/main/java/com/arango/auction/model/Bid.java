package com.arango.auction.model;

import com.arango.auction.constants.BidStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "userId cannot be empty")
    private Long userId;

    @NotNull(message = "auctionId cannot be empty")
    private Long auctionId;

    @NotNull(message = "bidAmount cannot be empty")
    private Long bidAmount;

    private LocalDateTime bidTime;

    private BidStatus bidStatus;
}