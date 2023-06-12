package com.arango.auction.service.impl;


import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.constants.BidStatus;
import com.arango.auction.model.Auction;
import com.arango.auction.model.Bid;
import com.arango.auction.pojo.BidRequest;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.repository.BidRepository;
import com.arango.auction.repository.ItemRepository;
import com.arango.auction.repository.UserRepository;
import com.arango.auction.service.BidService;
import com.arango.auction.service.EmailService;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static com.arango.auction.utils.AuctionUtils.BidBuilder;
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
    @Transactional
    public void placeBid(BidRequest bidRequest) {
        Bid bid = BidBuilder(bidRequest);
        userRepository.findById(bidRequest.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + bid.getUserId()));

        Auction auction = auctionRepository.findByIdAndStatus(bidRequest.getAuctionId(), AuctionStatus.RUNNING)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found with ID: " + bid.getAuctionId()));


        long currHighestBid = auction.getHighestBid();
        if (bidRequest.getBidAmount() < max(currHighestBid, auction.getBasePrice()) + auction.getStepRate())
        {
            throw new AuctionExceptions("Bid amount not enough");
        }

            int executed = auctionRepository.updateHighestbid(auction.getAuctionId(), bid.getBidAmount(),auction.getRevisionVersion());
            if (executed == 1) {
                bid.setBidStatus(BidStatus.ACCEPTED);
            } else {
                bid.setBidStatus(BidStatus.REJECTED);//Todo: throw exception
            }
            bid.setBidTime(LocalDateTime.now());
            bidRepository.insert(bid);
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
        return bidRepository.findById(bidId).orElseThrow(() -> new AuctionExceptions("Bid not found"));
    }


}
