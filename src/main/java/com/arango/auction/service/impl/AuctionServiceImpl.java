package com.arango.auction.service.impl;

import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Auction;
import com.arango.auction.pojo.AuctionRequest;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.repository.ItemRepository;
import com.arango.auction.service.AuctionService;
import com.arango.auction.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.arango.auction.utils.AuctionUtils.AuctionBuiler;
import static com.arango.auction.utils.AuctionUtils.getTimestamp;

@Service
@AllArgsConstructor
@Slf4j
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final EmailService emailService;
    private final DSLContext dslContext;

    @Override
    @Transactional
    public void createAuction(AuctionRequest auctionRequest) {
        Auction auction = AuctionBuiler(auctionRequest);
        if (auction.getStartTime().isBefore(LocalDateTime.now())) {
            throw new AuctionExceptions("Start time cannot be less than current time.");
        }
        itemRepository.findById(auction.getItemId()).orElseThrow(() -> new AuctionExceptions("Item not present"));

        Optional<Auction> optionalAuction = auctionRepository.findByItemId(auction.getItemId());
        if (optionalAuction.isPresent()) {
            throw new AuctionExceptions("Item part of another auction");
        }
        auction.setAuctionStatus(AuctionStatus.CREATED);
        LocalDateTime endTime = auction.getStartTime().plusHours(auction.getDuration());
        Auction savedAuction = auctionRepository.insert(auction);
        log.info("Auction created with id:{}", savedAuction.getAuctionId());
        try {
            CreateTask.createAuctionTask("start", auction.getAuctionId(), getTimestamp(auction.getStartTime())); //todo: ASYNC
            CreateTask.createAuctionTask("stop", auction.getAuctionId(), getTimestamp(endTime));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void startAuction(Long auctionId) {
        Auction auction = auctionRepository.findByIdAndStatus(auctionId, AuctionStatus.CREATED).orElseThrow(() -> new AuctionExceptions("Auction not found"));
        auctionRepository.updateStatus(auctionId, AuctionStatus.RUNNING,auction.getRevisionVersion());
        log.info("Auction id:{}, name:{} has been sarted",auction.getAuctionId(),auction.getAuctionName());
    }

    @Override
    @Transactional
    public void stopAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionExceptions("Auction not found"));
        if (auction.getAuctionStatus().equals(AuctionStatus.ENDED)) {
            throw new AuctionExceptions("Auction is already stopped");
        }
        auctionRepository.updateStatus(auctionId, AuctionStatus.ENDED, auction.getRevisionVersion());
        emailService.scheduleEmail(auction);
        log.info("Auction id:{}, name:{} has been ended",auction.getAuctionId(),auction.getAuctionName());
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(Long id) {
        return auctionRepository.findById(id).orElseThrow(() -> new AuctionExceptions("Auction not found"));
    }

    @Override
    public List<Auction> getAuctionByStatus(String auctionStatus) {
        return auctionRepository.findByStatus(auctionStatus);
    }
}
