package com.arango.auction.repository;


import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Auction;
import com.arangodb.springframework.repository.ArangoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends ArangoRepository<Auction, String> {

    Optional<Auction> findByAuctionNameAndAuctionStatus(String AuctionName, AuctionStatus auctionStatus);

    List<Auction> findByAuctionStatus(String auctionStatus);
}