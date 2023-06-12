package com.arango.auction.controller;

import com.arango.auction.model.Bid;
import com.arango.auction.model.User;
import com.arango.auction.pojo.BidRequest;
import com.arango.auction.pojo.GenericResponse;
import com.arango.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @PostMapping
    public void placeBid(@RequestBody BidRequest bidRequest) {
        bidService.placeBid(bidRequest);
    }

    @GetMapping
    public List<Bid> getAllBids(){
        return bidService.getAllBids();
    }

    @GetMapping(value = "/users/{user-id}")
    public List<Bid> getBidsByUser(@PathVariable(value = "user-id") Long userId){
        return bidService.getAllBidsByUser(userId);
    }

    @GetMapping(value = "/{bid-id}")
    public Bid getParticularBid(@PathVariable(value = "bid-id") Long bidId){
        return bidService.getParticularBid(bidId);
    }
}