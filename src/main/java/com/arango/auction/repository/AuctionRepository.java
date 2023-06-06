package com.arango.auction.repository;


import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.jooq.tables.records.AuctionRecord;
import com.arango.auction.model.Auction;
import com.arango.auction.model.Bid;
import org.jooq.DSLContext;
import org.jooq.Name;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arango.auction.jooq.*;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class AuctionRepository {
    private static final com.arango.auction.jooq.tables.Auction AUCTION = Tables.AUCTION.as("ac");

    @Autowired
    private DSLContext dslContext;

    public Long insert(Auction auction) {
        AuctionRecord record = dslContext.newRecord(AUCTION);
        record.setAuctionName(auction.getAuctionName());
        record.setAuctionStatus(auction.getAuctionStatus().name());
        record.setStartTime(auction.getStartTime());
        record.setEndTime(auction.getEndTime());
        record.setDuration(auction.getDuration());
        record.setItemId(auction.getItemId());
        record.setBasePrice(auction.getBasePrice());
        record.setStepRate(auction.getStepRate());
        record.setHighestBid(auction.getHighestBid());
        record.insert();
        return record.getAuctionId();
    }

    public void updateStatus(Long auctionId, AuctionStatus auctionStatus) {
        dslContext.update(AUCTION)
                .set(AUCTION.AUCTION_STATUS, auctionStatus.name())
                .where(AUCTION.AUCTION_ID.eq(auctionId))
                .execute();
    }

    public void updateHighestbid(Long auctionId, Long highestBid) {
        dslContext.update(AUCTION)
                .set(AUCTION.HIGHEST_BID,highestBid)
                .where(AUCTION.AUCTION_ID.eq(auctionId))
                .execute();
    }

    public Optional<Auction> findById(Long id) {
        AuctionRecord record = dslContext.selectFrom(AUCTION)
                .where(AUCTION.AUCTION_ID.eq(id))
                .fetchOne();
        if (record != null) {
            return Optional.of(toAuction(record));
        }
        return Optional.empty();
    }

    public List<Auction> findByStatus(String auctionStatus) {
        return dslContext.selectFrom(AUCTION)
                .where(AUCTION.AUCTION_STATUS.eq(auctionStatus))
                .fetch()
                .map(record -> toAuction(record));
    }

    public List<Auction> findAll() {
        return dslContext.selectFrom(AUCTION)
                .fetch()
                .map(record -> toAuction(record));
    }

    private Auction toAuction(AuctionRecord re) {
        return Auction.builder()
                .auctionId(re.getAuctionId())
                .auctionName(re.getAuctionName())
                .auctionStatus(AuctionStatus.valueOf(re.getAuctionStatus()))
                .startTime(re.getStartTime())
                .endTime(re.getEndTime())
                .duration(re.getDuration())
                .itemId(re.getItemId())
                .basePrice(re.getBasePrice())
                .stepRate(re.getStepRate())
                .highestBid(re.getHighestBid())
                .build();

    }

    private Auction toAuction(Record record) {
        return Auction.builder()
                .auctionId(record.get(AUCTION.AUCTION_ID))
                .auctionName(record.get(AUCTION.AUCTION_NAME))
                .auctionStatus(AuctionStatus.valueOf(record.get(AUCTION.AUCTION_STATUS)))
                .startTime(record.get(AUCTION.START_TIME))
                .endTime(record.get(AUCTION.END_TIME))
                .duration(record.get(AUCTION.DURATION))
                .itemId(record.get(AUCTION.ITEM_ID))
                .basePrice(record.get(AUCTION.BASE_PRICE))
                .stepRate(record.get(AUCTION.STEP_RATE))
                .highestBid(record.get(AUCTION.HIGHEST_BID))
                .build();
    }

}


