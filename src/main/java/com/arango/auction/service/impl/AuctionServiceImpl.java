package com.arango.auction.service.impl;

import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Auction;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.service.AuctionService;
import com.arango.auction.service.EmailService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.protobuf.Timestamp;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.LocalDateTime.*;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    DSLContext dslContext;

    @Override
    public Auction createAuction(Auction auction) {
        if (auction.getStartTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Start time cannot be less than current time.");
        }
        auction.setAuctionStatus(AuctionStatus.CREATED);
        LocalDateTime startTime = auction.getStartTime() != null ? auction.getStartTime() : now();
        auction.setStartTime(startTime);
        LocalDateTime endTime = startTime.plusHours(auction.getDuration());
        auction.setEndTime(endTime);

//        Long batchId = dslContext.transactionResult(() -> batchRepository.insert(batch));
//        batch.setId(batchId);

        Long auctionId = dslContext.transactionResult(()-> auctionRepository.insert(auction));
        auction.setAuctionId(auctionId);
        try {
            CreateTask.createAuctionTask("start", auctionId, getTimestamp(startTime));
            CreateTask.createAuctionTask("stop", auctionId, getTimestamp(endTime));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return auction;
    }

    @Override
    public String startAuction(Long auctionId) {
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionId);
        if (optionalAuction.isPresent()) {
            Auction auction = optionalAuction.get();
            if (auction.getAuctionStatus().equals(AuctionStatus.RUNNING)) {
                throw new AuctionExceptions("Auction is already Running");
            }
            dslContext.transaction(() -> auctionRepository.updateStatus(auctionId, AuctionStatus.RUNNING));
            return auction.getAuctionName() + " has been Started!!";
        } else {
            throw new AuctionExceptions("Auction not found");
        }
    }

    @Override
    public String stopAuction(Long auctionId) {
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionId);
        if (optionalAuction.isPresent()) {
            Auction auction = optionalAuction.get();

            if (auction.getAuctionStatus() != AuctionStatus.RUNNING) {
                throw new AuctionExceptions("Auction is already stopped");
            }
            dslContext.transaction(() -> auctionRepository.updateStatus(auctionId, AuctionStatus.ENDED));
            if(Objects.nonNull(auction.getHighestBid()))
            emailService.scheduleEmail(auction);
            return auction.getAuctionName() + " has been ENDED!!";
        } else {
            throw new AuctionExceptions("Auction not found");
        }
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(Long id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isPresent()) {
            return optionalAuction.get();
        } else {
            throw new AuctionExceptions("Auction not found");
        }
    }

    @Override
    public List<Auction> getAuctionByStatus(String auctionStatus) {
        return auctionRepository.findByStatus(auctionStatus);
    }

    public Timestamp getTimestamp(LocalDateTime localDateTime) {// Set scheduled time 3 hours from now
        ZoneId zone = ZoneId.of("Asia/Kolkata");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);

        return Timestamp.newBuilder()
                .setSeconds(localDateTime.toEpochSecond(zoneOffSet))
                .setNanos(localDateTime.getNano())
                .build();
    }
}
