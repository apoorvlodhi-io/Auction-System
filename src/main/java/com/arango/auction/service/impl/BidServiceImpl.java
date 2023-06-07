package com.arango.auction.service.impl;


import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.constants.BidStatus;
import com.arango.auction.constants.NotFoundException;
import com.arango.auction.model.Auction;
import com.arango.auction.model.Bid;
import com.arango.auction.model.Item;
import com.arango.auction.model.User;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.repository.BidRepository;
import com.arango.auction.repository.ItemRepository;
import com.arango.auction.repository.UserRepository;
import com.arango.auction.service.BidService;
import com.arango.auction.service.EmailService;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.max;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final EmailService emailService;
    private final DSLContext dslContext;

    @Override
    public Bid placeBid(Bid bid) {
        //TODO: Check for USer, Auction exists, already existing bid for same amount for same item
        userRepository.findById(bid.getUserId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with ID: " + bid.getUserId()));

        Auction auction = auctionRepository.findByIdAndStatus(bid.getAuctionId(),AuctionStatus.RUNNING).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Auction not found with ID: " + bid.getAuctionId()));

        long currHighestBid = auction.getHighestBid();
        if(bid.getBidAmount() < max(currHighestBid , auction.getBasePrice()) + auction.getStepRate()){
            bid.setBidStatus(BidStatus.REJECTED);
        }else{
            bid.setBidStatus(BidStatus.ACCEPTED);
            dslContext.transaction(() -> auctionRepository.updateHighestbid(auction.getAuctionId(), bid.getBidAmount()));
        }
        bid.setBidTime(LocalDateTime.now());
        return dslContext.transactionResult(()-> bidRepository.insert(bid));
    }

    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    @Override
    public List<Bid> getAllBidsByUser(Long userId) {
        return bidRepository.findByUserId(userId);
    }

    @Override
    public Bid getParticularBid(Long bidId) {
        return bidRepository.findById(bidId).orElseThrow(()-> new AuctionExceptions("Bid not found"));
    }


}
