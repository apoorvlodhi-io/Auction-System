package com.arango.auction.service;


import com.arango.auction.model.Auction;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

public interface AuctionService {
    Auction createAuction(Auction auction) throws IOException;
    String startAuction(String auctionId) ;

    String stopAuction(String auctionId);

    List<Auction> getAllAuctions();

    Auction getAuctionById(String id);

    List<Auction> getAuctionByStatus(String auctionStatus);

}
