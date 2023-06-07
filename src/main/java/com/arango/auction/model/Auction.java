package com.arango.auction.model;

import com.arango.auction.constants.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "Auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

    @NotNull(message = "auctionName cannot be empty")
    @Column(columnDefinition = "varchar(255)")
    private String auctionName;

    @NotNull
    @Column(columnDefinition = "varchar(255)")
    private AuctionStatus auctionStatus;

    @NotNull(message = "startTime cannot be empty")
    private LocalDateTime startTime;

    @NotNull(message = "duration cannot be empty")
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long duration;

    @NotNull(message = "itemId cannot be empty")
    private Long itemId;

    @NotNull(message = "basePrice cannot be empty")
    private Long basePrice;

    @NotNull(message = "stepRate cannot be empty")
    private Long stepRate;

    @NotNull
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long highestBid;
}
