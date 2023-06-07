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

    @GetMapping(value = "/all")
    public List<Bid> getAllBids(){
        return bidService.getAllBids();
    }

    @GetMapping(value = "/all/user")
    public ResponseEntity getBidsByUser(@RequestParam(value = "userId") Long userId){
        return new ResponseEntity(bidService.getAllBidsByUser(userId),HttpStatus.OK);
    }

    @GetMapping(value = "/one")
    public Bid getParticularBid(@RequestParam(value = "bidId") Long bidId){
        return bidService.getParticularBid(bidId);
    }
}