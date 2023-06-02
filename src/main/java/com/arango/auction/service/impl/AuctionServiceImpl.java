package com.arango.auction.service.impl;

import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Auction;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.service.AuctionService;
import com.arango.auction.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import com.google.protobuf.Timestamp;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.*;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    EmailService emailService;

    @Override
    public Auction createAuction(Auction auction) {
        if(auction.getStartTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Start time cannot be less than current time.");
        }
        auction.setAuctionStatus(AuctionStatus.CREATED);
        LocalDateTime startTime = auction.getStartTime() != null ? auction.getStartTime() : now();
        auction.setStartTime(startTime);
        LocalDateTime endTime = startTime.plusHours(auction.getDuration());
        auction.setEndTime(endTime);
        Auction savedAuction = auctionRepository.save(auction);
        String auctionId = savedAuction.getAuctionId().replaceAll("[^0-9]", "");
        try {
            CreateTask.startAuctionTask(auctionId, getTimestamp(startTime));
            CreateTask.stopAuctionTask(auctionId, getTimestamp(endTime));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savedAuction;
    }
    @Override
    public String startAuction(String auctionId) {
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionId);
        if (optionalAuction.isPresent()) {
            Auction auction = optionalAuction.get();
            if(auction.getAuctionStatus().equals(AuctionStatus.RUNNING)) {
                throw new AuctionExceptions("Auction is already Running");
            }
            auction.setAuctionStatus(AuctionStatus.RUNNING);
            auctionRepository.save(auction);
            return auction.getAuctionName() + " has been Started!!";
        } else {
            throw new AuctionExceptions("Auction not found");
        }
    }

    @Override
    public String stopAuction(String auctionId) {
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionId);
        if (optionalAuction.isPresent()) {
            Auction auction = optionalAuction.get();

            if(auction.getAuctionStatus() != AuctionStatus.RUNNING) {
                throw new AuctionExceptions("Auction is already stopped");
            }
            auction.setAuctionStatus(AuctionStatus.ENDED);
            auctionRepository.save(auction);

            emailService.scheduleEmail(auction);

            return auction.getAuctionName() + " has been ENDED!!";
        } else {
            throw new AuctionExceptions("Auction not found");
        }
    }

    @Override
    public List<Auction> getAllAuctions() {
        return (List<Auction>) auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(String id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isPresent()) {
            return optionalAuction.get();
        } else {
            throw new AuctionExceptions("Auction not found");
        }
    }

    @Override
    public List<Auction> getAuctionByStatus(String auctionStatus) {
        return auctionRepository.findByAuctionStatus(auctionStatus);
    }

    public Timestamp getTimestamp(LocalDateTime localDateTime){// Set scheduled time 3 hours from now
        ZoneId zone = ZoneId.of("Asia/Kolkata");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);

        return Timestamp.newBuilder()
                .setSeconds(localDateTime.toEpochSecond(zoneOffSet))
                .setNanos(localDateTime.getNano())
                .build();
    }
}
