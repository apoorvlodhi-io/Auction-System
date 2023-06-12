package com.arango.auction.utils;

import com.arango.auction.model.Auction;
import com.arango.auction.model.Bid;
import com.arango.auction.pojo.AuctionRequest;
import com.arango.auction.pojo.BidRequest;
import com.google.protobuf.Timestamp;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class AuctionUtils {
    public static Bid BidBuilder(BidRequest bidRequest) {
        return Bid.builder()
                .userId(bidRequest.getUserId())
                .bidAmount(bidRequest.getBidAmount())
                .auctionId(bidRequest.getAuctionId())
                .build();
    }
    public static Auction AuctionBuiler(AuctionRequest auctionRequest){
        Auction auction = new Auction();
        BeanUtils.copyProperties(auctionRequest,auction);
        return auction;
    }
    public static Timestamp getTimestamp(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.of("Asia/Kolkata");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);

        return Timestamp.newBuilder()
                .setSeconds(localDateTime.toEpochSecond(zoneOffSet))
                .setNanos(localDateTime.getNano())
                .build();
    }
}
