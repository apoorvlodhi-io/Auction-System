package com.arango.auction.service;

import com.arango.auction.model.Auction;
import com.arango.auction.model.Bid;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void scheduleEmail(Auction auction);

    void notifyOfNewBid(Auction auction, Bid bid);
}
