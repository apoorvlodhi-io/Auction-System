package com.arango.auction.service;

import com.arango.auction.model.Auction;
import com.arango.auction.pojo.AuctionRequest;
import java.io.IOException;
import java.util.List;
public interface AuctionService {
    void createAuction(AuctionRequest auction) throws IOException;
    void startAuction(Long auctionId) ;
    void stopAuction(Long auctionId);
    List<Auction> getAllAuctions();
    Auction getAuctionById(Long id);
    List<Auction> getAuctionByStatus(String auctionStatus);
}
