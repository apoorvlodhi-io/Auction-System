package com.arango.auction.service;

import com.arango.auction.model.Bid;
import com.arango.auction.pojo.BidRequest;

import java.util.List;
public interface BidService {
    void placeBid(BidRequest bidRequest);
    List<Bid> getAllBids();
    List<Bid> getAllBidsByUser(Long userId);
    Bid getParticularBid(Long bidId);
}
