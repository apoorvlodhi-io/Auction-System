package com.arango.auction.controller;

import com.arango.auction.model.Bid;
import com.arango.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @PostMapping(value = "/place",consumes = "application/json")
    public Bid placeBid(@RequestBody Bid bid) {
        return bidService.placeBid(bid);
    }

//    @PostMapping(value = "/newBid/{auctionId}")
//    public Bid placenewBid(@PathVariable(value = "auctionId")String auctionId,@RequestBody Bid bid) {
//        bid.setAuctionId(auctionId);
//        return bidService.placeBid(bid);
//    }

    @GetMapping(value = "/all")
    public List<Bid> getAllBids(){
        return bidService.getAllBids();
    }

    @GetMapping(value = "/all/user")
    public List<Bid> getBidsByUser(@RequestParam(value = "userId") String userId){
        return bidService.getAllBidsByUser(userId);
    }

    @GetMapping(value = "/one")
    public Bid getParticularBid(@RequestParam(value = "bidId") String bidId){
        return bidService.getParticularBid(bidId);
    }
}