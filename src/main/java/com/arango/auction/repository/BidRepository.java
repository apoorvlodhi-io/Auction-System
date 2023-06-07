package com.arango.auction.repository;

import com.arango.auction.constants.BidStatus;
import com.arango.auction.jooq.Tables;
import com.arango.auction.jooq.tables.records.BidsRecord;
import com.arango.auction.model.Bid;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BidRepository{
//    List<Bid> findByUserId(Long userId);
//
//    Bid findByBidAmountAndBidStatus(long bidAmount, BidStatus bidStatus);
//
//    List<Bid> findByAuctionIdAndBidStatus(Long auctionId, BidStatus bidStatus);

    private static final com.arango.auction.jooq.tables.Bids BIDS = Tables.BIDS.as("bd");

    @Autowired
    private DSLContext dslContext;

    public Bid insert(Bid bid) {
        BidsRecord record = dslContext.newRecord(BIDS); //todo
        record.setUserId(bid.getUserId());
        record.setAuctionId(bid.getAuctionId());
        record.setBidAmount(bid.getBidAmount());
        record.setBidTime(bid.getBidTime());
        record.setBidStatus(String.valueOf(bid.getBidStatus()));
        record.insert();
        return toBids(record);
    }

    public Optional<Bid> findById(Long id) {
        BidsRecord record = dslContext.selectFrom(BIDS)
                .where(BIDS.BID_ID.eq(id))
                .fetchOne();
        if (record != null) {
            return Optional.of(toBids(record)); //optional.nullabale
        }
        return Optional.empty();
    }

    public List<Bid> findByStatus(String bidStatus) {
        return dslContext.selectFrom(BIDS)
                .where(BIDS.BID_STATUS.eq(bidStatus))
                .fetch()
                .map(this::toBids);
    }

    public List<Bid> findByUserId(Long userId) {
        return dslContext.selectFrom(BIDS)
                .where(BIDS.USER_ID.eq(userId))
                .fetch()
                .map(this::toBids);
    }

    public Bid findByBidAmountAndBidStatus(long highestBidAmount, BidStatus bidStatus) {
        BidsRecord record = dslContext.selectFrom(BIDS)
                .where(BIDS.BID_STATUS.eq(bidStatus.name()))
                .and(BIDS.BID_AMOUNT.eq(highestBidAmount))
                .fetchOne();
        if (record != null) {
            return toBids(record);
        }
        return null;
    }

    public List<Bid> findBidsByOthers(Long auctionId, BidStatus bidStatus, Long userId) {
        return dslContext.selectFrom(BIDS)
                .where(BIDS.BID_STATUS.eq(bidStatus.name()))
                .and(BIDS.AUCTION_ID.eq(auctionId))
                .and(BIDS.USER_ID.notEqual(userId))
                .fetch()
                .map(record -> toBids(record));
    }

    public List<Bid> findAll() {
        return dslContext.selectFrom(BIDS)
                .fetch()
                .map(record -> toBids(record));
    }

    private Bid toBids(BidsRecord re) {
        return Bid.builder()
                .bidId(re.getBidId())
                .userId(re.getUserId())
                .auctionId(re.getAuctionId())
                .bidAmount(re.getBidAmount())
                .bidTime(re.getBidTime())
                .bidStatus(BidStatus.valueOf(re.getBidStatus()))
                .build();
    }




//    private Auction toBatch(Record record) {
//        return Auction.builder()
//                .auctionId(record.get(AUCTION.AUCTION_ID))
//                .auctionName(record.get(AUCTION.AUCTION_NAME))
//                .auctionStatus(AuctionStatus.valueOf(record.get(AUCTION.AUCTION_STATUS)))
//                .startTime(record.get(AUCTION.START_TIME))
//                .endTime(record.get(AUCTION.END_TIME))
//                .duration(record.get(AUCTION.DURATION))
//                .itemId(record.get(AUCTION.ITEM_ID))
//                .basePrice(record.get(AUCTION.BASE_PRICE))
//                .stepRate(record.get(AUCTION.STEP_RATE))
//                .highestBid(record.get(AUCTION.HIGHEST_BID))
//                .build();
//    }
}