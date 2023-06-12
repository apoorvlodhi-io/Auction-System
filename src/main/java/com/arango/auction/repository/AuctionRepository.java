package com.arango.auction.repository;


import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.jooq.tables.records.AuctionRecord;
import com.arango.auction.model.Auction;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.arango.auction.jooq.tables.Auction.AUCTION;

@Repository
public class AuctionRepository {
    @Autowired
    private DSLContext dslContext;

    public Auction insert(Auction auction) {
        AuctionRecord record = dslContext.newRecord(AUCTION);
        record.setAuctionName(auction.getAuctionName());
        record.setAuctionStatus(auction.getAuctionStatus().name());
        record.setStartTime(auction.getStartTime());
        record.setDuration(auction.getDuration());
        record.setItemId(auction.getItemId());
        record.setBasePrice(auction.getBasePrice());
        record.setStepRate(auction.getStepRate());
        record.setHighestBid(0L);
        record.setRevisionVersion(0L);
        AuctionRecord savedRecord = dslContext.insertInto(AUCTION)
                .set(record)
                .returning(AUCTION.asterisk()).fetchOne();
        return toAuction(Objects.requireNonNull(savedRecord));
    }

    public void updateStatus(Long auctionId, AuctionStatus auctionStatus, Long revisionVersion) {
        dslContext.update(AUCTION)
                .set(AUCTION.AUCTION_STATUS, auctionStatus.name())
                .where(AUCTION.AUCTION_ID.eq(auctionId))
                .and(AUCTION.REVISION_VERSION.eq(revisionVersion))
                .execute(); //todo
    }

    public int updateHighestbid(Long auctionId, Long highestBid, Long oldRevisionVersion) {
        return dslContext.update(AUCTION)
                .set(AUCTION.HIGHEST_BID,highestBid)
                .set(AUCTION.REVISION_VERSION,oldRevisionVersion + 1)
                .where(AUCTION.AUCTION_ID.eq(auctionId),AUCTION.REVISION_VERSION.eq(oldRevisionVersion))
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

    public Optional<Auction> findByIdAndStatus(Long id, AuctionStatus auctionStatus) {
        AuctionRecord record = dslContext.selectFrom(AUCTION)
                .where(AUCTION.AUCTION_ID.eq(id))
                .and(AUCTION.AUCTION_STATUS.eq(auctionStatus.name()))
//                .forUpdate()
                .fetchOne();
        if (record != null) {
            return Optional.of(toAuction(record));
        }
        return Optional.empty();
    }

    public Optional<Auction> findByItemId(Long itemId) {
        AuctionRecord record = dslContext.selectFrom(AUCTION)
                .where(AUCTION.ITEM_ID.eq(itemId))
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
                .map(this::toAuction);
    }

    public List<Auction> findAll() {
        return dslContext.selectFrom(AUCTION)
                .fetch()
                .map(this::toAuction);
    }

    private Auction toAuction(AuctionRecord re) {
        return Auction.builder()
                .auctionId(re.getAuctionId())
                .auctionName(re.getAuctionName())
                .auctionStatus(AuctionStatus.valueOf(re.getAuctionStatus()))
                .startTime(re.getStartTime())
                .duration(re.getDuration())
                .itemId(re.getItemId())
                .basePrice(re.getBasePrice())
                .stepRate(re.getStepRate())
                .highestBid(re.getHighestBid())
                .revisionVersion(re.getRevisionVersion())
                .build();

    }

    private Auction toAuction(Record record) {
        return Auction.builder()
                .auctionId(record.get(AUCTION.AUCTION_ID))
                .auctionName(record.get(AUCTION.AUCTION_NAME))
                .auctionStatus(AuctionStatus.valueOf(record.get(AUCTION.AUCTION_STATUS)))
                .startTime(record.get(AUCTION.START_TIME))
                .duration(record.get(AUCTION.DURATION))
                .itemId(record.get(AUCTION.ITEM_ID))
                .basePrice(record.get(AUCTION.BASE_PRICE))
                .stepRate(record.get(AUCTION.STEP_RATE))
                .highestBid(record.get(AUCTION.HIGHEST_BID))
                .revisionVersion(record.get(AUCTION.REVISION_VERSION))
                .build();
    }


}


