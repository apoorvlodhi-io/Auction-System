package com.arango.auction.controller;


import com.arango.auction.model.Auction;
import com.arango.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @PostMapping(value = "/create")
    public Auction createAuction(@RequestBody Auction auction) throws IOException {
        return auctionService.createAuction(auction);
    }

    @PostMapping(value = "/start")
    public String startAuction(@RequestParam(value = "auctionId") Long auctionId) {
        return auctionService.startAuction(auctionId);
    }

    @PostMapping("/stop")
    public String stopAuction(@RequestParam(value = "auctionId") Long auctionId) {
        return auctionService.stopAuction(auctionId);
    }

    @GetMapping(value = "/all")
    public List<Auction> getAllAuction() {
        return auctionService.getAllAuctions();
    }

    @GetMapping(value = "/filter")
    public List<Auction> getAuctionByStatus(@RequestParam(value = "auctionStatus")String auctionStatus) {
        return auctionService.getAuctionByStatus(auctionStatus);
    }

    @GetMapping
    public Auction getAuctionById(@RequestParam(value = "auctionId")Long auctionId) {
        return auctionService.getAuctionById(auctionId);
    }

}
