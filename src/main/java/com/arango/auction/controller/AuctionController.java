package com.arango.auction.controller;


import com.arango.auction.model.Auction;
import com.arango.auction.pojo.AuctionRequest;
import com.arango.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arango.auction.pojo.GenericResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/auctions")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @PostMapping
    public void createAuction(@RequestBody AuctionRequest auctionRequest) throws IOException {
        auctionService.createAuction(auctionRequest);
    }

    @PutMapping(value = "/start/{auction-id}")
    public void startAuction(@PathVariable(value = "auction-id")Long auctionId) {
        auctionService.startAuction(auctionId);
    }

    @PutMapping(value = "/stop/{auction-id}")
    public void stopAuction(@PathVariable(value = "auction-id")Long auctionId) {
         auctionService.stopAuction(auctionId);
    }

    @GetMapping
    public List<Auction> getAllAuction() {
        return auctionService.getAllAuctions();
    }

    @GetMapping(value = "/filter/{auction-status}")
    public List<Auction> getAuctionByStatus(@PathVariable(value = "auction-status")String auctionStatus) {
        return auctionService.getAuctionByStatus(auctionStatus);
    }

    @GetMapping(value = "/{auction-id}")
    public Auction getAuctionById(@PathVariable(value = "auction-id")Long auctionId) {
        return auctionService.getAuctionById(auctionId);
    }

}
