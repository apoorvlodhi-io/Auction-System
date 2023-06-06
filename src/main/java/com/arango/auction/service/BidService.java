package com.arango.auction.service;

import com.arango.auction.model.Bid;

import java.util.List;

public interface BidService {
    Bid placeBid(Bid bid);

    List<Bid> getAllBids();

    List<Bid> getAllBidsByUser(Long userId);

    Bid getParticularBid(Long bidId);
}
