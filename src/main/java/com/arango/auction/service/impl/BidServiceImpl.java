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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private AuctionServiceImpl auctionService;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EmailService emailService;

    public Bid placeBid(Bid bid) {
        //TODO: Check for USer, Auction exists, already existing bid for same amount for same item
        Optional<User> optionalUser = userRepository.findById(bid.getUserId());
        if(optionalUser.isEmpty()){
        throw new NotFoundException("User not found with ID: " + bid.getUserId());
        }
        Optional<Auction> optionalAuction = auctionRepository.findById(bid.getAuctionId());
        if(optionalAuction.isEmpty()){
            throw new NotFoundException("Auction not found with ID: " + bid.getAuctionId());
        }else if(!optionalAuction.get().getAuctionStatus().equals(AuctionStatus.RUNNING)){
            throw new AuctionExceptions("Auction Ended!");
        }

        Auction auction = optionalAuction.get();
        Optional<Item> optionalItem = itemRepository.findById(bid.getItemId());
        if(optionalItem.isEmpty()){
            throw new AuctionExceptions("Item not present");
        }

        long highestBid = optionalAuction.get().getHighestBid();
        if(bid.getBidAmount() <= max(highestBid,auction.getBasePrice()) + auction.getStepRate()){
            bid.setBidStatus(BidStatus.REJECTED);
            bidRepository.save(bid);
            throw new AuctionExceptions("Bid amount not enough.");
        }

        auction.setHighestBid(bid.getBidAmount());
        auctionRepository.save(auction);
        bid.setBidStatus(BidStatus.ACCEPTED);
        bid.setBidTime(LocalDateTime.now());
        bidRepository.save(bid);

        emailService.notifyOfNewBid(auction);

        return bid;
    }

    @Override
    public List<Bid> getAllBids() {
        return (List<Bid>) bidRepository.findAll();
    }

    @Override
    public List<Bid> getAllBidsByUser(String userId) {
        return bidRepository.findByUserId(userId);
    }

    @Override
    public Bid getParticularBid(String bidId) {
        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (optionalBid.isPresent()) {
            return optionalBid.get();
        } else {
            throw new AuctionExceptions("Bid not found");
        }
    }


}
