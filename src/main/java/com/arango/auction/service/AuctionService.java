package com.arango.auction.service;


import com.arango.auction.model.Auction;

import java.io.IOException;
import java.util.List;

public interface AuctionService {
    Auction createAuction(Auction auction) throws IOException;
    String startAuction(Long auctionId) ;

    String stopAuction(Long auctionId);

    List<Auction> getAllAuctions();

    Auction getAuctionById(Long id);

    List<Auction> getAuctionByStatus(String auctionStatus);

}
