package com.arango.auction.service;

import com.arango.auction.model.Auction;
public interface EmailService {
    void sendEmail(String to, String subject, String text);
    void scheduleEmail(Auction auction);
}
