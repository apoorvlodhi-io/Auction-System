package com.arango.auction.repository;

import com.arango.auction.constants.BidStatus;
import com.arango.auction.model.Bid;
import com.arangodb.springframework.repository.ArangoRepository;

import java.util.List;

public interface BidRepository extends ArangoRepository<Bid, String> {
    List<Bid> findByUserId(String userId);

    Bid findByBidAmountAndBidStatus(long bidAmount, BidStatus bidStatus);

    List<Bid> findByAuctionIdAndBidStatus(String auctionId, BidStatus bidStatus);
}
