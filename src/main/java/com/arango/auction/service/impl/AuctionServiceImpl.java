package com.arango.auction.service.impl;

import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Auction;
import com.arango.auction.pojo.GenericResponse;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.repository.ItemRepository;
import com.arango.auction.service.AuctionService;
import com.arango.auction.service.EmailService;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.protobuf.Timestamp;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    AuctionRepository auctionRepository;
    ItemRepository itemRepository;
    EmailService emailService;
    DSLContext dslContext;

    @Override
    public Auction createAuction(Auction auction) {
        if (auction.getStartTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Start time cannot be less than current time.");
        }

        itemRepository.findById(auction.getItemId()).orElseThrow(() -> new RuntimeException("Item not present"));

        Optional<Auction> optionalAuction = auctionRepository.findByItemId(auction.getItemId());
        if (optionalAuction.isPresent()) {
            throw new RuntimeException("Item part of another auction");
        }

        auction.setAuctionStatus(AuctionStatus.CREATED);
        LocalDateTime endTime = auction.getStartTime().plusHours(auction.getDuration());

        Auction savedAuction = dslContext.transactionResult(() -> auctionRepository.insert(auction));
//        CreateTask(auction,endTime);
        try {
            CreateTask.createAuctionTask("start", auction.getAuctionId(), getTimestamp(auction.getStartTime())); //todo: ASYNC
            CreateTask.createAuctionTask("stop", auction.getAuctionId(), getTimestamp(endTime));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savedAuction;
    }

    @Override
    public String startAuction(Long auctionId) {
        Auction auction = auctionRepository.findByIdAndStatus(auctionId,AuctionStatus.CREATED).orElseThrow(() -> new AuctionExceptions("Auction not found"));
        dslContext.transaction(() -> auctionRepository.updateStatus(auctionId, AuctionStatus.RUNNING));
        return auction.getAuctionName() + " has been Started!!";
    }

    @Override
    public String stopAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionExceptions("Auction not found"));
        if (auction.getAuctionStatus().equals(AuctionStatus.ENDED)) {
            throw new AuctionExceptions("Auction is already stopped");
        }
        dslContext.transaction(() -> auctionRepository.updateStatus(auctionId, AuctionStatus.ENDED));
        emailService.scheduleEmail(auction);
        return   auction.getAuctionName() + " has been ENDED!!";
    }

    @Override
    public List<Auction> getAllAuctions() {
        return  auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(Long id) {
        return auctionRepository.findById(id).orElseThrow(()->new AuctionExceptions("Auction not found"));
    }

    @Override
    public List<Auction> getAuctionByStatus(String auctionStatus) {
        return auctionRepository.findByStatus(auctionStatus);
    }

//    @Async
//    public void CreateTask(Auction auction,LocalDateTime endTime){
//        try {
//            CreateTask.createAuctionTask("start", auction.getAuctionId(), getTimestamp(auction.getStartTime())); //todo: ASYNC
//            CreateTask.createAuctionTask("stop", auction.getAuctionId(), getTimestamp(endTime));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Timestamp getTimestamp(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.of("Asia/Kolkata");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);

        return Timestamp.newBuilder()
                .setSeconds(localDateTime.toEpochSecond(zoneOffSet))
                .setNanos(localDateTime.getNano())
                .build();
    }
}
